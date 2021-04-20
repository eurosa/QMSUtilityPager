package com.qms.utility;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


public class DataControl extends Activity {

    TextView myLabel;
    EditText myTextbox;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;



   // Button btnOn, btnOff, btnDis;
    Button On, Off, Discnt, Abt;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private EditText sendEditText;
    private Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device

        //view of the DataControl
        setContentView(R.layout.activity_send_control);

        //call the widgets
        On = (Button)findViewById(R.id.on_btn);
        Off = (Button)findViewById(R.id.off_btn);
        Discnt = (Button)findViewById(R.id.dis_btn);
        Abt = (Button)findViewById(R.id.abt_btn);
        myLabel=findViewById(R.id.my_text_view);
        sendBtn=findViewById(R.id.send_btn);
        sendEditText=(EditText)findViewById(R.id.sendEditText);

        new ConnectBT().execute(); //Call the class to connect



        //commands to be sent to bluetooth
        sendBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendData();      //method to turn on
            }
        });

       // Toast.makeText(getApplicationContext(),""+isBtConnected,Toast.LENGTH_LONG);


        //commands to be sent to bluetooth
        On.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                turnOnLed();      //method to turn on
            }
        });

        Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               // beginListenForData();
                turnOffLed();   //method to turn off


            }
        });

        Discnt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Disconnect(); //close connection
            }
        });

      //  beginListenForData();




          //  receiveData();
        //} catch (IOException e) {
          //  e.printStackTrace();
        //}
    }


    public void receiveData() throws IOException{

//       final Handler handler = new Handler();

        // Get a handler that can be used to post to the main thread
        Handler mainHandler = new Handler(Looper.getMainLooper());

        if (btSocket!=null)
        {
            try
            {
                InputStream socketInputStream =  btSocket.getInputStream();

                byte[] buffer = new byte[1024];
                int bytes;

                // Keep looping to listen for received messages
                while (true) {
                    try {
                        bytes = mmInputStream.read(buffer);            //read bytes from input buffer
                        final String readMessage = new String(buffer, 0, bytes);
                        // Send the obtained bytes to the UI Activity via handler
                        Log.i("logging", readMessage + "");


Runnable myRunnable = new Runnable() {
    @Override
    public void run() {

        myLabel.setText(readMessage);
        //myLabel.append("");
    } // This is your code
};
mainHandler.post(myRunnable);
/*
                        handler.post(new Runnable()
                        {
                            public void run()
                            {
                                myLabel.setText(readMessage);
                            }
                        });
                        */


                    } catch (IOException e) {
                        break;
                    }
                }
            }
            catch (IOException e)
            {
                msg("Error");
            }


        }



    }


    void beginListenForData()
    {
      //  final Handler handler = new Handler();
        // Get a handler that can be used to post to the main thread
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        final byte delimiter = 10; //This is the ASCII code for a newline character
        Log.d("btSocket",""+btSocket);
        if(btSocket!=null) {
            try {
                mmInputStream = btSocket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        if(mmInputStream!=null) {
                            int bytesAvailable = mmInputStream.available();
                            if (bytesAvailable > 0) {
                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);
                                for (int i = 0; i < bytesAvailable; i++) {
                                    byte b = packetBytes[i];
                                    if (b == delimiter) {
                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;
                                        Log.d("btSocket data", "" + data);
                                        Runnable myRunnable = new Runnable() {
                                            @Override
                                            public void run() {

                                                myLabel.setText(data);
                                                //myLabel.append("");
                                            } // This is your code
                                        };
                                        mainHandler.post(myRunnable);
                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }


    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }


    private void sendData()
    {
        if (btSocket!=null)
        {
            try
            {




                final Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                       // myLabel.setText(sendEditText.getText().toString());
                        handler.postDelayed(this,100);
                    }
                },100);

                btSocket.getOutputStream().write(sendEditText.getText().toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void turnOffLed()
    {
        if (btSocket!=null)
        {
            try
            {
                mmOutputStream.write("Hello".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }



    private void turnOnLed()
    {
        if (btSocket!=null)
        {
            try
            {
                mmOutputStream.write("Ranojan Kumar".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    public  void about(View v)
    {
      //  if(v.getId() == R.id.abt)
        //{
            Intent i = new Intent(this, AboutActivity.class);
            startActivity(i);
        //}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_led_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(DataControl.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                 myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                 BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                 btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                 BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                 btSocket.connect();//start connection
                    mmOutputStream = btSocket.getOutputStream();
                    mmInputStream = btSocket.getInputStream();

                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;



/*
                try {
                    receiveData();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
            progress.dismiss();

              //beginListenForData();

            new Thread(new Runnable() {
                public void run(){
                    try {
                        receiveData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //beginListenForData();
                }
            }).start();
            /*try {
                receiveData();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*if(isBtConnected){

                try {
                    receiveData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
        }
    }
}
