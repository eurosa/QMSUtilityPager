package com.bluetooth.scan;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.camerakit.CameraKitView;
import com.google.android.gms.instantapps.InstantApps;
import com.muddzdev.quickshot.QuickShot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.BreakIterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;


public class DeviceList extends AppCompatActivity
{
    private static final String MY_PREFS_NAME = "MyTxtFile";;
    //  private CameraKitView cameraKitView;
    //==============================To Connect Bluetooth Device=============================
    private ProgressDialog progress;
    private boolean isBtConnected = false;
    BluetoothSocket btSocket = null;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    String address = null;
    TextView textView;
    boolean listViewFlag=true;

    //==============================To connect bluetooth devices============================
    //-----------------------------Camera----------------------------------------------------
    private static final String LOG_TAG = "JBCamera";
    private static final int REQUEST_CAMERA_PERMISSION = 21;
    private static final int REQUEST_STORAGE_PERMISSION = 22;
    private int cameraId = 1;
    private Camera camera = null;
    private boolean waitForPermission = false;
    private boolean waitForStoragePermission = false;
    //-----------------------------------Camera-----------------------------------------------


    //widgets
    Button btnPaired,scanDevices;
    ListView devicelist;
    //Bluetooth
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    public static String EXTRA_ADDRESS = "device_address";

    //screenshott
    private final static String[] requestWritePermission =
            { Manifest.permission.WRITE_EXTERNAL_STORAGE };
    private ImageView imageView;
    private Bitmap bitmap;
    private TextView txt_fahrenheit;
    private TextView txt_celcius;
    private String str_celcius;
    private String str_fahrenheit;
    private ImageView bmpView;
    //=================================For temperature limit count==================================
    private double normalLow=32;
    private double normalHigh=37.3;
    private double moderateLow=37.4;
    private double moderateHigh=38;
    private double maxLow=38.1;
    private double maxHigh=40;
    private String double_str_celcius;
    private String double_str_fahrenheit;
    private int normalCount=0;
    private int moderateCount=0;
    private int highCount=0;
    private TextView normalView;
    private TextView moderateView;
    private TextView highView;
    private TextView totalView;
    private ImageView iv_your_image;

    //=========================================End temperature limit count==========================
    String[] permissions = new String[]{

            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    //screenshot
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
//----------------------------Grant storage permission--------------------------------------------------

        //----------------------------------------------------------------------------------------------
        //=========================Adding Toolbar in android layout======================================
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
        //=========================Toolbar End============================================================

        //cameraKitView = findViewById(R.id.camera);
        //-------------------------------------To Receive device address from background==================
        textView=findViewById(R.id.txt_normal);
        txt_celcius=findViewById(R.id.txt_celcius);
        txt_fahrenheit=findViewById(R.id.txt_fahrenheit);
        bmpView = findViewById(R.id.bitmap_view);
     //   iv_your_image=findViewById(R.id.iv_your_image);
        //============================Temperature Counting View=====================================================//
        normalView=findViewById(R.id.normal_view);
        moderateView=findViewById(R.id.moderate_view);
        highView=findViewById(R.id.high_view);
        totalView=findViewById(R.id.total_view);
        //============================Temperature Countning View====================================================//


        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device
        //new ConnectBT().execute(); //Call the class to connect
        //-------------------------------------To Receive device address from background==================
        //====================================Camera======================================================

        boolean isInstantApp = InstantApps.getPackageManagerCompat(this).isInstantApp();
        Log.d(LOG_TAG, "are we instant?" + isInstantApp);
        findViewById(R.id.preview_surface).setOnClickListener(clickListener);
        //findViewById(R.id.capture_button).setOnClickListener(clickListener);
        //findViewById(R.id.switch_button).setOnClickListener(clickListener);

        //=======================================Camera=============================================


        //Calling widgets
        btnPaired = findViewById(R.id.button);
        devicelist = findViewById(R.id.listView);
        scanDevices=findViewById(R.id.scanDevice);
        devicelist.setVisibility(View.GONE);

        //if the device has bluetooth
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if(myBluetooth == null)
        {
            //Show a Mensag. That the device has no bluetooth adapter
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();

            //finish apk
            finish();
        }
        else if(!myBluetooth.isEnabled())
        {
                //Ask to the user turn the bluetooth on
                Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnBTon,1);
        }

        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                pairedDevicesList();
                if(listViewFlag){
                    devicelist.setBackgroundColor(Color.WHITE);
                    devicelist.setVisibility(View.VISIBLE);
                    listViewFlag=false;
                }else{
                    devicelist.setVisibility(View.GONE);
                    devicelist.setBackgroundColor(Color.WHITE);
                    listViewFlag=true;
                }

            }

        }

        );



        scanDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ScanDevicesList();
            }
        });





        //Camera screenshot
        final boolean hasWritePermission = RuntimePermissionUtil.checkPermissonGranted(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        imageView = findViewById(R.id.imageView);
        final TextView hidden_txtview =  findViewById(R.id.hidden_txtview);
        final TextureView hidden_textureview =  findViewById(R.id.hidden_textureview);

        ImageButton capture_screenshot = findViewById(R.id.capture_screenshot);

        //Screen_shott.xml view------------------------------------------------------------
        /*
        capture_screenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Take screen shot
                //bitmap = ScreenShott.getInstance().takeScreenShotOfView(hidden_txtview);
                //bitmap = ScreenShott.getInstance().takeScreenShotOfJustView(hidden_txtview);
                //bitmap = ScreenShott.getInstance().takeScreenShotOfTextureView(hidden_textureview);
                bitmap = ScreenShott.getInstance().takeScreenShotOfRootView(view);

               // bitmap = ScreenShott.getInstance().takeScreenShotOfView(view);
                // Display in imageview
               // takeScreenshot();
                imageView.setImageBitmap(bitmap);
            }
        });

        ImageButton capture_refresh = findViewById(R.id.capture_refresh);
        capture_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap placeholder =
                        BitmapFactory.decodeResource(DeviceList.this.getResources(), R.drawable.placeholder);
                // load the placeholder image into imageview
                imageView.setImageBitmap(placeholder);
            }
        });

        ImageButton capture_save =  findViewById(R.id.capture_save);
        capture_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bitmap != null) {
                    if (hasWritePermission) {
                        Log.d("screenshotclick","screenclick:"+bitmap);

                        saveScreenshot();
                    }
                    else {
                        RuntimePermissionUtil.requestPermission(DeviceList.this, requestWritePermission, 100);
                    }
                }
            }
        });*/

//----------------------------------screen_shot xml view-----------------------------------------
        //Camera screenshot

        //=================================FileExposed============================
        /*
        *
        *
        * android.os.FileUriExposedException: file:///storage/emulated/0/test.txt exposed beyond app through Intent.getData()
        * solved using this
        * */
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        //=======================================================================



    }





    private void ScanDevicesList(){

        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
    }
    private void pairedDevicesList()
    {
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size()>0)
        {
            for(BluetoothDevice bt : pairedDevices)
            {
                list.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        devicelist.setAdapter(adapter);
        devicelist.setOnItemClickListener(myListClickListener); //Method called when the device from the list is clicked



    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick (AdapterView<?> av, View v, int arg2, long arg3)
        {
            //Get the device MAC address, the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            //Make an intent to start next activity.
            //Intent i = new Intent(DeviceList.this, DeviceList.class);

            //Change the activity.
            //i.putExtra(EXTRA_ADDRESS, address); //this will be received at DataControl (class) Activity
            //startActivity(i);
            new ConnectBT(address).execute(); //Call the class to connect
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_device_list, menu);
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
            shareFileWithApps();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


//----------------------------------------------Cmamera-----------------------------------------------------------

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (camera == null) {
                return;
            } else if (v.getId() == R.id.preview_surface) {
                try {
                    switchCamera();
                //    camera.takePicture(null, null, pictureCallback);
                } catch (RuntimeException e) {
                    Log.e(LOG_TAG, "preview_surface", e);
                }
            }
            /*else if (v.getId() == R.id.capture_button) {
                try {
                  //  takeScreenshot();
                    camera.takePicture(null, null, pictureCallback);
                } catch (RuntimeException e) {
                    Log.e(LOG_TAG, "capture_button", e);
                }
            } */
            /*
            else if (v.getId() == R.id.switch_button) {
                switchCamera();
            }*/
        }
    };

    public void switchCamera() {
        startCamera(1 - cameraId);
    }

    @Override
    public void onResume() {
        super.onResume();
        setSurface();
      //  cameraKitView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
      //  checkPermissions();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED&&checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                waitForPermission = true;
                requestCameraPermission();

            }
/*
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED&&checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                waitForStoragePermission = true;
                requestStoragePermission();

            }

 */

        }

     //   cameraKitView.onStart();
    }
    @Override
    protected void onStop() {
       // cameraKitView.onStop();
        super.onStop();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)&&shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)&&shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            AlertDialog confirmationDialog = new AlertDialog.Builder(this)
                    .setMessage(R.string.request_permission)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                                    REQUEST_CAMERA_PERMISSION);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                    .create();
            confirmationDialog.show();
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)&&shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            AlertDialog confirmationDialog = new AlertDialog.Builder(this)
                    .setMessage(R.string.request_permission)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                                    REQUEST_STORAGE_PERMISSION);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                    .create();
            confirmationDialog.show();
        } else {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull final int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length != 3 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                AlertDialog errorDialog = new AlertDialog.Builder(this)
                        .setMessage(R.string.request_permission).create();
                errorDialog.show();
               finish();
            } else {
                waitForPermission = false;
                startCamera(cameraId);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                AlertDialog errorDialog = new AlertDialog.Builder(this)
                        .setMessage(R.string.request_permission).create();
                errorDialog.show();
                finish();
            } else {
                waitForStoragePermission = false;
               // startCamera(cameraId);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


        switch (requestCode) {
            case 100: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do something
                }


                RuntimePermissionUtil.onRequestPermissionsResult(grantResults, new RPResultListener() {


                    @Override
                    public void onPermissionGranted() {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            saveScreenshot();
                        }
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(DeviceList.this, "Permission Denied! You cannot save image!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                break;



            }
        }
    //    cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        switch (requestCode) {
            case 100: {

                RuntimePermissionUtil.onRequestPermissionsResult(grantResults, new RPResultListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            saveScreenshot();
                        }
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(DeviceList.this, "Permission Denied! You cannot save image!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
    }
    */

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera cam) {
            try {
                String jpgPath = getCacheDir() + "/JBCameraCapture.jpg";
                FileOutputStream jpg = new FileOutputStream(jpgPath);
                jpg.write(data);
                jpg.close();

                Log.i(LOG_TAG, "written " + data.length + " bytes to " + jpgPath);

                final Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

                Log.i(LOG_TAG, "bmp dimensions " + bmp.getWidth() + "x" + bmp.getHeight());

             //  final ImageView bmpView = (ImageView)findViewById(R.id.bitmap_view);

                bmpView.post(new Runnable() {
                    @Override
                    public void run() {
                        bmpView.setImageBitmap(bmp);
                      //  iv_your_image.setImageBitmap(bmp);
                      /*  Glide.with(DeviceList.this)
                                .load(bmp)
                                .dontTransform()
                                .into(iv_your_image);*/
                     //   bmpView.setRotation(-90);
                        bmpView.setVisibility(View.VISIBLE);

                        takeScreenshot();

                        try {
                            camera.startPreview();
                        }catch (Exception e){


                        }


                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };



    private SurfaceHolder.Callback shCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.i(LOG_TAG, "surfaceDestroyed callback");
            if (camera != null) {
                camera.stopPreview();
                camera.release();
            }
            camera = null;
        }



        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.i(LOG_TAG, "surfaceCreated callback");
            startCamera(cameraId);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            Log.i(LOG_TAG, "surfaceChanged callback " + width + "x" + height);
            restartPreview();
        }

    };

    private void setSurface() {
        SurfaceView previewSurfaceView = (SurfaceView)findViewById(R.id.preview_surface);
        previewSurfaceView.getHolder().addCallback(shCallback);
    }

    Handler cameraHandler;

    protected void startCamera(final int id) {

        if (cameraHandler == null) {
            HandlerThread handlerThread = new HandlerThread("CameraHandlerThread");
            handlerThread.start();
            cameraHandler = new Handler(handlerThread.getLooper());
        }
        Log.d(LOG_TAG, "startCamera(" + id + "): " + (waitForPermission ? "waiting" : "proceeding"));
        if (waitForPermission) {
            return;
        }
        releaseCamera();
        cameraHandler.post(new Runnable() {
            @Override
            public void run() {
                final Camera camera = openCamera(id);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startPreview(id, camera);
                    }
                });
            }
        });
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    private void restartPreview() {
        if (camera == null) {
            return;
        }
        int degrees = 0;
        switch (getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        Camera.CameraInfo ci = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, ci);
        if (ci.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            degrees += ci.orientation;
            degrees %= 360;
            degrees = 360 - degrees;
        }
        else {
            degrees = 360 - degrees;
            degrees += ci.orientation;
        }
        camera.setDisplayOrientation(degrees%360);
        choosePictureResolution();

        camera.startPreview();
    }

    private void choosePictureResolution() {

        List<Camera.Size> supportedSizes;
        Camera.Parameters params = camera.getParameters();

        supportedSizes = params.getSupportedPreviewSizes();
        for (Camera.Size sz : supportedSizes) {
            Log.d(LOG_TAG, "supportedPreviewSizes " + sz.width + "x" + sz.height);
        }
        params.setPreviewSize(supportedSizes.get(0).width, supportedSizes.get(0).height);

        supportedSizes = params.getSupportedVideoSizes();
        for (Camera.Size sz : supportedSizes) {
            Log.d(LOG_TAG, "supportedVideoSizes " + sz.width + "x" + sz.height);
        }

        supportedSizes = params.getSupportedPictureSizes();
        for (Camera.Size sz : supportedSizes) {
            Log.d(LOG_TAG, "supportedPictureSizes " + sz.width + "x" + sz.height);
        }
        params.setPictureSize(supportedSizes.get(0).width, supportedSizes.get(0).height);

        Log.d(LOG_TAG, "current preview size " + params.getPreviewSize().width + "x" + params.getPreviewSize().height);
        Log.d(LOG_TAG, "current picture size " + params.getPictureSize().width + "x" + params.getPictureSize().height);
        // TODO: choose the best preview & picture size, and also fit the surface aspect ratio to preview aspect ratio
        camera.setParameters(params);
    }

    private static Camera openCamera(int id) {
        Log.d(LOG_TAG, "opening camera " + id);
        Camera camera = null;
        try {
            camera = Camera.open(id);
            Log.d(LOG_TAG, "opened camera " + id);
        } catch (Exception e) {
            e.printStackTrace();
            if (camera != null) {
                camera.release();
            }
            camera = null;
        }
        return camera;
    }

    private void setPictureSize(int width, int height) {
        Camera.Parameters params = camera.getParameters();
        params.setPictureSize(width, height);
        camera.setParameters(params);
    }

    private void startPreview(int id, Camera c) {
        if (c != null) {
            try {
                SurfaceView previewSurfaceView = (SurfaceView)findViewById(R.id.preview_surface);
                SurfaceHolder holder = previewSurfaceView.getHolder();
                c.setPreviewDisplay(holder);
                camera = c;
                cameraId = id;
                restartPreview();
            } catch (IOException e) {
                e.printStackTrace();
                c.release();
            }
        }
    }

    //Camera-----------------------------------------------------------------------

    //camera screenshot
    private void saveScreenshot() {
        // Save the screenshot

        try {
            File file = ScreenShott.getInstance()
                    .saveScreenshotToPicturesFolder(DeviceList.this, bitmap, "my_screenshot");
            // Display a toast
            Toast.makeText(DeviceList.this, "Bitmap Saved at " + file.getAbsolutePath(),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    //camera screenshot

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";
            String fileName=now + ".jpg";

            //-------------------------------------

            //-------------------------------------

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
         //   View v2 = getWindow().takeSurface();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

        //    File imageFile = new File(mPath);
        //    Log.d("takeshot",""+imageFile);

/*
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
*/
            boolean save=createDirectoryAndSaveFile(bitmap,fileName);



        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }
    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }



    private boolean createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {
      //  bmpView.setImageBitmap(imageToSave);

            File root = new File(Environment.getExternalStorageDirectory() + File.separator + "Temperature_Scan_Folder", "Image Folder");
            //File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
                //The rest of the series of this so called meterpre
            }
            File gpxfile = new File(root, fileName);
            //-----------------------------------
        if (gpxfile.exists()) {
            gpxfile.delete();
        }


        try {
            FileOutputStream out = new FileOutputStream(gpxfile);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            //==============================To Show Image in Gallery==============================================================
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
            //=====================================================================================================================
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    //=================================To Connect Bluetooth Device====================================
    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        String addressBLE=null;
        private boolean ConnectSuccess = true; //if it's here, it's almost connected
        public ConnectBT(String address) {
            super();
            addressBLE=address;
            // do stuff
        }


        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(DeviceList.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(addressBLE);//connects to the device's address and checks if it's available
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
                Intent intent = getIntent();
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
                startActivity(intent);

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
                                //To set degree html special entity character
                                //https://en.wikipedia.org/wiki/List_of_XML_and_HTML_character_entity_references
                                //For Degree \u00B0

                                String str = readMessage;

                                String readString=readMessage.replace("*","").replace("#","").trim();
                                Log.d("readMessage"+"outside",""+readString+""+readString.equals("LOW".trim()));

                                if(readString.equals("LOW".trim())){
                                    Log.d("readMessage",""+readMessage);

                                    txt_celcius.setBackgroundColor(getResources().getColor(R.color.redColor));
                                    txt_fahrenheit.setBackgroundColor(getResources().getColor(R.color.redColor));
                                    txt_celcius.setText(readString);
                                    txt_fahrenheit.setText(readString);
                                }else  if(readString.equals("HIGH".trim())){

                                    txt_celcius.setBackgroundColor(getResources().getColor(R.color.redColor));
                                    txt_fahrenheit.setBackgroundColor(getResources().getColor(R.color.redColor));
                                    txt_celcius.setText(readString);
                                    txt_fahrenheit.setText(readString);
                                }

                                try {
                                    ArrayList<String> elephantList = new ArrayList<>(Arrays.asList(str.split(",")));
                                    str_celcius = elephantList.get(0).replace("*", "").replace(" ", "\u00B0").trim();
                                    str_fahrenheit = elephantList.get(1).replace("#", "").replace(" ", "\u00B0").trim();
                                    double_str_celcius = elephantList.get(0).replace("*", "").replace("C", "").replace(" ", "").trim();
                                    double_str_fahrenheit = elephantList.get(1).replace("#", "").replace("F", "").replace(" ", "").trim();

                                    if (normalLow <= Double.parseDouble(double_str_celcius) && normalHigh >= Double.parseDouble(double_str_celcius)) {

                                        txt_celcius.setBackgroundColor(getResources().getColor(R.color.limeGreen));
                                        txt_fahrenheit.setBackgroundColor(getResources().getColor(R.color.limeGreen));
                                        txt_celcius.setText(str_celcius);
                                        txt_fahrenheit.setText(str_fahrenheit);
                                        normalCount=normalCount+1;
                                        normalView.setText(""+normalCount);
                                        Log.d("normalCount", "surfaceCreated callback:"+normalCount);

                                    } else if (moderateLow <= Double.parseDouble(double_str_celcius) && moderateHigh >= Double.parseDouble(double_str_celcius)) {

                                        txt_celcius.setBackgroundColor(getResources().getColor(R.color.blueColor));
                                        txt_fahrenheit.setBackgroundColor(getResources().getColor(R.color.blueColor));
                                        txt_celcius.setText(str_celcius);
                                        txt_fahrenheit.setText(str_fahrenheit);
                                        moderateCount = moderateCount+1;
                                        moderateView.setText(""+moderateCount);

                                    } else if (maxLow <= Double.parseDouble(double_str_celcius) && maxHigh >= Double.parseDouble(double_str_celcius)) {

                                        txt_celcius.setBackgroundColor(getResources().getColor(R.color.redColor));
                                        txt_fahrenheit.setBackgroundColor(getResources().getColor(R.color.redColor));
                                        txt_celcius.setText(str_celcius);
                                        txt_fahrenheit.setText(str_fahrenheit);
                                        highCount = highCount+1;
                                        highView.setText(""+highCount);

                                    }

                                    totalView.setText(""+(normalCount + moderateCount + highCount));

                                    saveDataToFile(str_celcius+" "+"/"+" "+str_fahrenheit);

                                }catch (Exception e){

                                   e.printStackTrace();

                                }

                                if(str_celcius != null && !str_celcius.isEmpty() && str_fahrenheit != null && !str_fahrenheit.isEmpty()){
                                try {


                                    camera.takePicture(null, null, pictureCallback);



                                  }catch (Exception e){

                                }
                                        // Take screen shot
                                        //bitmap = ScreenShott.getInstance().takeScreenShotOfView(hidden_txtview);
                                        //bitmap = ScreenShott.getInstance().takeScreenShotOfJustView(hidden_txtview);
                                        //bitmap = ScreenShott.getInstance().takeScreenShotOfTextureView(hidden_textureview);
                                        // bitmap = ScreenShott.getInstance().takeScreenShotOfView(view);
                                        // Display in imageview
                                    try{
                                        //saveScreenshot();
                                   // bmpView.setImageResource(android.R.color.transparent);
                                   // bmpView.setImageResource(0);



                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                txt_celcius.setText("\u00B0C");
                                                txt_fahrenheit.setText("\u00B0F");
                                                txt_celcius.setBackgroundColor(getResources().getColor(R.color.limeGreen));
                                                txt_fahrenheit.setBackgroundColor(getResources().getColor(R.color.limeGreen));
                                               // bmpView.setImageResource(0);
                                                //bmpView.setImageBitmap(null);
                                             //  bmpView.destroyDrawingCache();
                                                bmpView.post(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        //   bmpView.setRotation(-90);
                                                     //   iv_your_image.setImageBitmap(null);
                                                      /*  Glide.with(DeviceList.this)
                                                                .clear(iv_your_image);*/
                                                        bmpView.setImageResource(0);

                                                        bmpView.setVisibility(View.VISIBLE);

                                                    }
                                                });
                                            }
                                        }, 5000);


                                    Log.d("bskodh",""+bmpView);
                                        // bmpView.rem
                                       // imageView.setImageBitmap(bitmap);

                                    }catch (Exception e){


}
                                }



                                //myLabel.append("");
                            }   //This is your code
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


    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
    //=================================To Connect Bluetooth Device====================================
    private void tryDrawing(SurfaceHolder holder) {
        Log.i(TAG, "Trying to draw...");

        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            Log.e(TAG, "Cannot draw onto the canvas as it's null");
        } else {
            drawMyStuff(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawMyStuff(final Canvas canvas) {
        Random random = new Random();
        Log.i(TAG, "Drawing...");
        canvas.drawRGB(255, 128, 128);
    }

private void saveDataToFile(String sBody){
    Date now = new Date();
    //;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");

    String fileName = formatter.format(now) +"_Temperature Scan"+ ".txt";//like 2020_09_16.txt


    try
    {
        File root = new File(Environment.getExternalStorageDirectory()+File.separator+"Temperature_Scan_Folder", "Temperature Scan Files");
        //File root = new File(Environment.getExternalStorageDirectory(), "Notes");
      Log.d("folderPath",""+root);
        if (!root.exists())
        {
            root.mkdirs();
        }
        File gpxfile = new File(root, fileName);
        saveTxtLink( gpxfile.getPath());

        FileWriter writer = new FileWriter(gpxfile,true);
      //  FR Locale : 03/11/2017 16:04:58
        //DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(date)
        //03/11/2017 16:04:58 GMT+01:00
        //DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(now)

        writer.append(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(now)+"  "+sBody+"\n\n");
        writer.flush();
        writer.close();
    //    Toast.makeText(this, "Data has been written to Report File"+ gpxfile.getPath(), Toast.LENGTH_LONG).show();

    }
    catch(IOException e)
    {
        e.printStackTrace();

    }
}


    public void saveTxtLink(String txtLink){

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("txt_link", txtLink);
        editor.apply();
    }

    public  String getTxtLink(){

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String txtLink = prefs.getString("txt_link", "No name defined");//"No name defined" is the default value.
        return txtLink;
    }

    public void shareFileWithApps(){

        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        File fileWithinMyDir = new File(getTxtLink());

        if(fileWithinMyDir.exists()) {

            intentShareFile.setType("text/plain");

            //intentShareFile.setType("application/pdf");
            intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+getTxtLink()));

            intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
                    "Sharing File...");
            intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");

            startActivity(Intent.createChooser(intentShareFile, "Share File"));
        }

    }

    /*
    public void clearViewData(){
        txt_celcius.setText("\u00B0C");
        txt_celcius.setText("\u00B0F");
        bmpView.setImageResource(0);
        //bmpView.setImageBitmap(null);
        bmpView.destroyDrawingCache();
    }*/

    //------------------------To Check storage Permission----------------------------
    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }
    //------------------------To check storage Permission----------------------------

}
