package com.qms.utility;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.instantapps.InstantApps;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import static android.content.ContentValues.TAG;


public class DeviceList extends AppCompatActivity implements  View.OnClickListener,FragmentToActivity {
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

    //-------------------------------Start ofClock in TextView---------------------------------------------
    BroadcastReceiver _broadcastReceiver;
    private final SimpleDateFormat _sdfWatchTime = new SimpleDateFormat("HH:mm:ss");
    private TextView _tvTime;
    private TextClock textClock;
    //-------------------------------End of Clock in TextView----------------------------------------------

    //==============================To connect bluetooth devices===========================================
    //-----------------------------Camera------------------------------------------------------------------
    private static final String LOG_TAG = "JBCamera";
    private static final int REQUEST_CAMERA_PERMISSION = 21;
    private static final int REQUEST_STORAGE_PERMISSION = 22;
    private int cameraId = 1;
    private Camera camera = null;
    private boolean waitForPermission = false;
    private boolean waitForStoragePermission = false;
    DatabaseHandler dbHandler = new DatabaseHandler(this);
    DataModel dataModel = new DataModel();
     //-----------------------------------Camera-----------------------------------------------

    //**************** General Fragment ******************************************************
    /*private EditText instEditText, timeDateEditText, bankIdEditText, counterNameEditText,
            tokenSlipBEditText, tokenSlipAEditText,
            tokenSlip9EditText, cTimeEditText, copyNoEditText, totalCounterEditText,
            cntLabelOneEditText,cntLabelTwoEditText,cntLabelThreeEditText,cntLabelFourEditText,
            cntLabelFiveEditText,cntLabelSixEditText,cntLabelSevenEditText,cntLabelEightEditText,
            cntLabelNineEditText,cntLabelTenEditText,cntLabelElevenEditText,cntLabelTweleveEditText,
            cntLabelThirteenEditText,cntLabelFourteenEditText,cntLabelFifteenEditText,cntLabelSixteenEditText;

    private Button sendcntLabelOne,sendCntLabelTwo,sendCntLabelThree,sendCntLabelFour,sendCntLabelFive,
            sendCntLabelSix,sendCntLabelSeven,sendCntLabelEight,sendCntLabelNine,sendCntLabelTen,
            sendCntLabelEleven,sendCntLabelTweleve,sendCntLabelThirteen,sendCntLabelFourteen,sendCntLabelFifteen,sendCntLabelSixteen,
            btnInstitute,btnBankId,sendTimeDate,counterTimeDate,sendTotalCounter,sendCopyNo,sendCTime,
            sendTokenSlip9,sendTokenSlipA,sendTokenSlipB;*/

    //**************** General Fragment End **************************************************


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

    private ImageView iv_your_image;
    private String str_cel,str_fah;

    //=========================================End temperature limit count==========================
    String[] permissions = new String[]{

            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private    FrameLayout   layout;
    private Button btnTag;


    //+++++++++++++++++++++++++++ Pager and TabLayout +++++++++++++++++++++++++++++++++++++++++++++
    TabLayout tabLayout;
    ViewPager viewPager;
    private MyAdapter adapter;
    private SpinnerDialog mSpinnerDialog;


    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //=========================Toolbar End============================================================
        //+++++++++++++++++++ TabLayout and ViewPager ++++++++++++++++++++++++++++++++++++++++++++++++++++

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("General"));
        tabLayout.addTab(tabLayout.newTab().setText("Counter Label"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
          adapter = new MyAdapter(this,getSupportFragmentManager(),
                tabLayout.getTabCount(), dataModel);
        viewPager.setAdapter(adapter);
        viewPager.getCurrentItem(); // --> 2
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
               // Log.d("TAG", "onTabSelected: " + tab.getPosition());

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // Attach the page change listener inside the activity
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
               /* Toast.makeText(getApplicationContext(),
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();*/

                Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.getCurrentItem());
                // based on the current position you can then cast the page to the correct
                // class and call the method:
                if (viewPager.getCurrentItem() == 0 && page != null) {
                    ((General)page).changeTextOfGeneralFragment(dataModel);
                }

                if (viewPager.getCurrentItem() == 1 && page != null) {
                    ((CounterLabel)page).changeTextOfCounterFragment(dataModel);
                }
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });

       /* int pos = viewPager.getCurrentItem();
        Fragment activeFragment = adapter.getItem(pos);
        if(pos == 0)
            ((General)activeFragment).onRefresh();*/

        View view = viewPager;
        if (view !=null) {
            view.findViewById(R.id.sendcntLabelOne);
            Log.d("TAG","Helllfd"+view.getId());
        }


       /* viewPager.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             Log.d("TAG", "onTabSelected: " + view.getId());
                                         }
                                     });*/
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //cameraKitView = findViewById(R.id.camera);
        //-------------------------------------To Receive device address from background==================
        //textView=findViewById(R.id.txt_normal);
        //txt_celcius=findViewById(R.id.txt_celcius);
        //txt_fahrenheit=findViewById(R.id.txt_fahrenheit);
       // txt_date=findViewById(R.id.txt_date);
        //textClock=findViewById(R.id.hk_time);

       //**************** General Fragment & Counter Label EditText ***************************************************************************************************************************
       /* instEditText=viewPager.findViewById(R.id.instEditText);
        bankIdEditText=viewPager.findViewById(R.id.bankIdEditText);
        timeDateEditText=viewPager.findViewById(R.id.timeDateEditText);
        counterNameEditText=viewPager.findViewById(R.id.counterNameEditText);

        totalCounterEditText=viewPager.findViewById(R.id.totalCounterEditText);
        copyNoEditText=viewPager.findViewById(R.id.copyNoEditText);
        cTimeEditText=viewPager.findViewById(R.id.cTimeEditText);
        tokenSlip9EditText=viewPager.findViewById(R.id.tokenSlip9EditText);

        tokenSlipAEditText=viewPager.findViewById(R.id.tokenSlipAEditText);
        tokenSlipBEditText=viewPager.findViewById(R.id.tokenSlipBEditText);

        // ++++++++++++++++++++++++++++++Counter Label EditText++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        cntLabelOneEditText=viewPager.findViewById(R.id.cntLabelOneEditText);
        cntLabelTwoEditText=viewPager.findViewById(R.id.cntLabelTwoEditText);
        cntLabelThreeEditText=viewPager.findViewById(R.id.cntLabelThreeEditText);
        cntLabelFourEditText=viewPager.findViewById(R.id.cntLabelFourEditText);

        cntLabelFiveEditText=viewPager.findViewById(R.id.cntLabelFiveEditText);
        cntLabelSixEditText=viewPager.findViewById(R.id.cntLabelSixEditText);
        cntLabelSevenEditText=viewPager.findViewById(R.id.cntLabelSevenEditText);
        cntLabelEightEditText=viewPager.findViewById(R.id.cntLabelEightEditText);

        cntLabelNineEditText=viewPager.findViewById(R.id.cntLabelNineEditText);
        cntLabelTenEditText=viewPager.findViewById(R.id.cntLabelTenEditText);

        cntLabelElevenEditText=viewPager.findViewById(R.id.cntLabelElevenEditText);
        cntLabelTweleveEditText=viewPager.findViewById(R.id.cntLabelTweleveEditText);
        cntLabelThirteenEditText=viewPager.findViewById(R.id.cntLabelThirteenEditText);
        cntLabelFourteenEditText=viewPager.findViewById(R.id.cntLabelFourteenEditText);

        cntLabelFifteenEditText=viewPager.findViewById(R.id.cntLabelFifteenEditText);
        cntLabelSixteenEditText=viewPager.findViewById(R.id.cntLabelSixteenEditText);*/


        /*************************************************************************************************
        *************************************General Button***********************************************
        **************************************************************************************************/

        /*btnInstitute=viewPager.findViewById(R.id.btnInstitute);
        btnBankId=viewPager.findViewById(R.id.btnBankId);
        sendTimeDate=viewPager.findViewById(R.id.sendTimeDate);
        counterTimeDate=viewPager.findViewById(R.id.counterTimeDate);

        sendTotalCounter=viewPager.findViewById(R.id.sendTotalCounter);
        sendCopyNo=viewPager.findViewById(R.id.sendCopyNo);
        sendCTime=viewPager.findViewById(R.id.sendCTime);
        sendTokenSlip9=viewPager.findViewById(R.id.sendTokenSlip9);

        sendTokenSlipA=viewPager.findViewById(R.id.sendTokenSlipA);
        sendTokenSlipB=viewPager.findViewById(R.id.sendTokenSlipB);



        sendcntLabelOne=viewPager.findViewById(R.id.sendcntLabelOne);
        // sendcntLabelOne.setOnClickListener(this);
        sendCntLabelTwo=viewPager.findViewById(R.id.sendCntLabelTwo);
        sendCntLabelThree=viewPager.findViewById(R.id.sendCntLabelThree);
        sendCntLabelFour=viewPager.findViewById(R.id.sendCntLabelFour);

        sendCntLabelFive=viewPager.findViewById(R.id.sendCntLabelFive);
        sendCntLabelSix=viewPager.findViewById(R.id.sendCntLabelSix);
        sendCntLabelSeven=viewPager.findViewById(R.id.sendCntLabelSeven);
        sendCntLabelEight=viewPager.findViewById(R.id.sendCntLabelEight);

        sendCntLabelNine=viewPager.findViewById(R.id.sendCntLabelNine);
        sendCntLabelTen=viewPager.findViewById(R.id.sendCntLabelTen);

        sendCntLabelEleven=viewPager.findViewById(R.id.sendCntLabelEleven);
        sendCntLabelTweleve=viewPager.findViewById(R.id.sendCntLabelTweleve);
        sendCntLabelThirteen=viewPager.findViewById(R.id.sendCntLabelThirteen);
        sendCntLabelFourteen=viewPager.findViewById(R.id.sendCntLabelFourteen);

        sendCntLabelFifteen=viewPager.findViewById(R.id.sendCntLabelFifteen);
        sendCntLabelSixteen=viewPager.findViewById(R.id.sendCntLabelSixteen);*/


        //**************** General Fragment End **********************************************************************************************************************


        //iv_your_image=findViewById(R.id.iv_your_image);
        //============================Temperature Counting View=====================================================//
        //normalView=findViewById(R.id.normal_view);
        //moderateView=findViewById(R.id.moderate_view);
        //highView=findViewById(R.id.high_view);
        //totalView=findViewById(R.id.total_view);
        //============================Temperature Countning View====================================================//


        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device
        //new ConnectBT().execute(); //Call the class to connect
        //-------------------------------------To Receive device address from background==================
        //====================================Camera======================================================

        boolean isInstantApp = InstantApps.getPackageManagerCompat(this).isInstantApp();
        Log.d(LOG_TAG, "are we instant?" + isInstantApp);





        //Calling widgets
        btnPaired = findViewById(R.id.button);
     //   devicelist = findViewById(R.id.listView);
        scanDevices=findViewById(R.id.scanDevice);
     //   devicelist.setVisibility(View.GONE);

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
                /*
                if(listViewFlag){
                    devicelist.setBackgroundColor(Color.WHITE);
                    devicelist.setVisibility(View.VISIBLE);
                    listViewFlag=false;
                }else{
                    devicelist.setVisibility(View.GONE);
                    devicelist.setBackgroundColor(Color.WHITE);
                    listViewFlag=true;
                }
*/
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



        //--------------------------------------------------------------------------------------------------------------
       Dialog dialog = new Dialog(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Device for Pairing");

        LinearLayout parent = new LinearLayout(DeviceList.this);

        parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        parent.setOrientation(LinearLayout.VERTICAL);

        ListView modeList = new ListView(this);



        //------------------To fixed height of listView------------------------------------
        setListViewHeightBasedOnItems(modeList);
        //RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(200, 0);
        //modeList.setLayoutParams(params);
        //modeList.requestLayout();
        /******************************************************************************************************************
        *
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.height = 20;
        modeList.setLayoutParams(params);
        modeList.requestLayout();

        *
        * */
       // ViewGroup.LayoutParams listViewParams = (ViewGroup.LayoutParams)modeList.getLayoutParams();
        //listViewParams.height = 20;
       // modeList.smoothScrollToPosition(3);
/*
        ListAdapter listadp = modeList.getAdapter();
        if (listadp != null) {
            int totalHeight = 0;
            for (int i = 0; i < listadp.getCount(); i++) {
                View listItem = listadp.getView(i, null, modeList);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = modeList.getLayoutParams();
            params.height = totalHeight + (modeList.getDividerHeight() * (listadp.getCount() - 1));
            modeList.setLayoutParams(params);
            modeList.requestLayout();
        }
*/
        //------------------End of fixed height of listView---------------------------------

        final ArrayAdapter modeAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        modeList.setAdapter(modeAdapter);
        modeList.setOnItemClickListener(myListClickListener);
        builder.setView(modeList);
      //  builder.show();
        dialog = builder.create();
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, 600); //Controlling width and height.


        //-------------------------------------------------------------------------------------------------------------



    }


    private void pairedDevicesListOriginal()
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
            new ConnectBT(address,info).execute(); //Call the class to connect
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
            // shareFileWithApps();
            final DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            ArrayList<String> labels = db.Get_QmsUtility();
            mSpinnerDialog = new SpinnerDialog(this, labels,dataModel, new SpinnerDialog.DialogListener() {
                public void cancelled() {
                    // do your code here
                }
                public void ready(String n) {
                    // do your code here
                    db.getQmsUtility(n,dataModel);


                    Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.getCurrentItem());
                    // based on the current position you can then cast the page to the correct
                    // class and call the method:
                    if (viewPager.getCurrentItem() == 0 && page != null) {
                        ((General)page).changeTextOfGeneralFragment(dataModel);
                    }

                    if (viewPager.getCurrentItem() == 1 && page != null) {
                        ((CounterLabel)page).changeTextOfCounterFragment(dataModel);
                    }

                    int selectionPosition= mSpinnerDialog.adapter.getPosition(n);
                    mSpinnerDialog.mSpinner.setSelection(selectionPosition);
                    Log.d("SelectedPosition: ",""+selectionPosition);
                    dataModel.setSelectionPosition(selectionPosition);
                    dataModel.setLastSelectedItem((n));

                    // ((General)activeFragment).changeTextOfFragment(dataModel.getInstName());
                    // ((General)activeFragment).getInstEditText().setText("98");
                    // instEditText.setText(dataModel.getInstName());


                }
            });

            mSpinnerDialog.show();
            /*************************************************************************************************************
            * When Click on EditText then android keyboard not opened but adding the below line solved the problem
            *
            **************************************************************************************************************/
            mSpinnerDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            //recordSetUp();
            return true;
        }

        if (id == R.id.action_reset) {

            sendData("$RESET" +""+ ";");
            Toast.makeText(getApplicationContext(), "Data has been successfully reset", Toast.LENGTH_SHORT).show();
//==============================End of Folder Delete using Alert Dialog=================================================================================
          /*  AlertDialog.Builder adb = new AlertDialog.Builder(this);
           // adb.setView(Integer.parseInt("Delete Folder"));
            adb.setTitle("Delete Folder");
            adb.setMessage("Are you sure you want to delete this Folder?");
            adb.setIcon(android.R.drawable.ic_dialog_alert);
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                   // File dir =new File( getApplicationContext().getFilesDir()+"/Temperature_Scan_Folder");
                    File dir = new File(Environment.getExternalStorageDirectory()+"/Temperature_Scan_Folder");
                    boolean success = deleteDir(dir);
                    if(success) {
                        Toast.makeText(DeviceList.this, "Successfully Deleted the Folder ",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(DeviceList.this, "Folder Not Found",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
            adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(DeviceList.this, "Cancel",
                            Toast.LENGTH_SHORT).show();
                    //finish();
                }
            });
            adb.show();
*/     return true;
        }

            if (id == R.id.action_save) {

                /*int pos = viewPager.getCurrentItem();
                Fragment activeFragment = adapter.getItem(pos);
               if(pos == 0 || pos==1)
                    ((General)activeFragment).onRefresh();*/

                 // countries.getGeneralData();
                if(dataModel.getLastSelectedItem() =="New Record") {
                    dbHandler.Add_QmsUtility(dataModel);
                }else {
                    int updated_id = dbHandler.Update_QmsUtility(dataModel);
                    Toast.makeText(getApplicationContext(), "Data has been updated successfully", Toast.LENGTH_SHORT).show();
                }
                // Toast.makeText(getApplicationContext(), "Data has been saved successfully", Toast.LENGTH_SHORT).show();

                return true;

        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onAttachFragment(Fragment fragment) {
        // TODO Auto-generated method stub
        super.onAttachFragment(fragment);

       // Toast.makeText(getApplicationContext(), String.valueOf(fragment.getId()), Toast.LENGTH_SHORT).show();

    }

    public void getFromUser(View view) {
        General fragment1 = (General) getSupportFragmentManager().findFragmentById(R.id.general_fg);
        View frag=fragment1.getView();
        EditText editText1 =(EditText) frag.findViewById(R.id.instEditText);
        String message=editText1.getText().toString();
        Toast.makeText(getApplicationContext(), "Data has been saved successfully"+message, Toast.LENGTH_SHORT).show();
        //  sendMessage(message);
    }

    /********************************************************************************************************
    *
    *     Just cool man and join in the day to day marketing here just cool man and go to horn area
    *
    * ***************This program is designed for QMS utility and serving the issue*************************
    * ******************************************************************************************************
    *
    *
    * ******************************************************************************************************/

    @Override
    public void onResume() {
        super.onResume();

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

        _broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                //if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0)
                  //  txt_date.setText(_sdfWatchTime.format(new Date()));
            }
        };

        registerReceiver(_broadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));

     //   cameraKitView.onStart();
    }
    @Override
    protected void onStop() {
       // cameraKitView.onStop();
        super.onStop();
        if (_broadcastReceiver != null)
            unregisterReceiver(_broadcastReceiver);
    }

    private void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
    }


    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,   String[] permissions,
                                             final int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length != 3 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                AlertDialog errorDialog = new AlertDialog.Builder(this)
                        .setMessage(R.string.request_permission).create();
                errorDialog.show();
               finish();
            } else {
                waitForPermission = false;
                // startCamera(cameraId);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
//------------------------------------
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

//---------------------------------------
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
//---------------------------------------------------------------------------------------
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
                final String jpgPath = getCacheDir() + "/JBCameraCapture.jpg";
                FileOutputStream jpg = new FileOutputStream(jpgPath);
                jpg.write(data);
                jpg.close();

                Log.i(LOG_TAG, "written " + data.length + " bytes to " + jpgPath);

              //  final Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);


               // handleSamplingAndRotationBitmap(DeviceList.this, Uri.fromFile(new File("/sdcard/sample.jpg")));
                final Bitmap bmp =  handleSamplingAndRotationBitmap(DeviceList.this, Uri.fromFile(new File(jpgPath)));
                Log.i(LOG_TAG, "bmp dimensions " + bmp.getWidth() + "x" + bmp.getHeight());
             //  final ImageView bmpView = (ImageView)findViewById(R.id.bitmap_view);

                bmpView.post(new Runnable() {
                    @Override
                    public void run() {
                       bmpView.setImageBitmap(bmp);
                     //   Picasso.get().load(Uri.fromFile(new File(jpgPath))).into(bmpView);
                      //  iv_your_image.setImageBitmap(bmp);
                      /*  Glide.with(DeviceList.this)
                                .load(bmp)
                                .dontTransform()
                                .into(iv_your_image);*/
                     //   bmpView.setRotation(-90);
                        bmpView.setVisibility(View.VISIBLE);



                        takeScreenshot();
                        btnTag.setVisibility(View.VISIBLE);
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
            // startCamera(cameraId);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            Log.i(LOG_TAG, "surfaceChanged callback " + width + "x" + height);
            restartPreview();
        }

    };



    Handler cameraHandler;



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
        btnTag.setVisibility(View.INVISIBLE);
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        String timeFormat =DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";
            String fileName= android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)+"_"+str_cel+"_"+str_fah + ".jpg";

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
        Log.d("fileNameNow",""+fileName);
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
          //  sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
            MediaScannerConnection.scanFile(getApplicationContext(), new String[] { root.getAbsolutePath() }, null, new MediaScannerConnection.OnScanCompletedListener() {

                @Override
                public void onScanCompleted(String path, Uri uri) {
                    // TODO Auto-generated method stub

                }
            });

            //=====================================================================================================================
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(this, ""+v.getId(),
        //      Toast.LENGTH_LONG).show();
        /*      Button sendcntLabelOne,sendCntLabelTwo,sendCntLabelThree,sendCntLabelFour,sendCntLabelFive,
                sendCntLabelSix,sendCntLabelSeven,sendCntLabelEight,sendCntLabelNine,sendCntLabelTen,
                sendCntLabelEleven,sendCntLabelTweleve,sendCntLabelThirteen,sendCntLabelFourteen,sendCntLabelFifteen,sendCntLabelSixteen,
                btnInstitute,btnBankId,sendTimeDate,counterTimeDate,sendTotalCounter,sendCopyNo,sendCTime,
                sendTokenSlip9,sendTokenSlipA,sendTokenSlipB

          EditText instEditText, timeDateEditText, bankIdEditText, counterNameEditText,
            tokenSlipBEditText, tokenSlipAEditText,
            tokenSlip9EditText, cTimeEditText, copyNoEditText, totalCounterEditText,
            cntLabelOneEditText,cntLabelTwoEditText,cntLabelThreeEditText,cntLabelFourEditText,
            cntLabelFiveEditText,cntLabelSixEditText,cntLabelSevenEditText,cntLabelEightEditText,
            cntLabelNineEditText,cntLabelTenEditText,cntLabelElevenEditText,cntLabelTweleveEditText,
            cntLabelThirteenEditText,cntLabelFourteenEditText,cntLabelFifteenEditText,cntLabelSixteenEditText;
                */
        switch (v.getId()) {
            case R.id.sendcntLabelOne:
                 // String cntLabelOneEditTextData = "$BnkL"+cntLabelOneEditText.getText().toString()+";";
                 // sendData(cntLabelOneEditTextData);
                 Log.d("Fucking:","cntLabelOneEditTextData");
                 Log.d("Just Cool Man","");
                // Toast.makeText(this, cntLabelOneEditTextData,
                // Toast.LENGTH_LONG).show();
                // break;

        }
    }

    @Override
    public void communicate(String comm) {
        //Toast.makeText(getApplicationContext(),"Received: "+comm,Toast.LENGTH_LONG).show();
    }

public void dispatchInformations(String ff){

    Toast.makeText(getApplicationContext(),"Received: "+ff,Toast.LENGTH_LONG).show();
}
    //=================================To Connect Bluetooth Device====================================
    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        String addressBLE=null;
        String infoBLE=null;
        private boolean ConnectSuccess = true; //if it's here, it's almost connected
        public ConnectBT(String address,String info) {
            super();
            addressBLE=address;
            infoBLE=info;
            //Do stuff
        }


        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(DeviceList.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {

            if(btSocket!=null){
                try {
                    btSocket.close();
                    btSocket=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

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
                // finish();
              //  startActivity(intent);
               // Disconnect();
                getSupportActionBar().setTitle(R.string.app_name);
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
                String address = infoBLE.substring(infoBLE.length() - 17);
                String name = infoBLE.replace(address, "");
                getSupportActionBar().setTitle(name);

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

    public void sendData(String text)
    {
        OutputStream tmpOut = null;
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
                byte[] msgBuffer = text.getBytes();
                Log.d("SendText1",text);
                tmpOut = btSocket.getOutputStream();
                Log.d("SendText2",msgBuffer.toString());
                tmpOut.write(msgBuffer);
                tmpOut.flush();


                Toast.makeText(this, "Data has been successfully sent", Toast.LENGTH_SHORT).show();
            }
            catch (IOException e)
            {
                msg("Error");
            }
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


                                }else  if(readString.equals("HIGH".trim())){


                                }

                                try {
                                    ArrayList<String> elephantList = new ArrayList<>(Arrays.asList(str.split(",")));
                                    str_celcius = elephantList.get(0).replace("*", "").replace(" ", "\u00B0").trim();
                                    str_fahrenheit = elephantList.get(1).replace("#", "").replace(" ", "\u00B0").trim();
                                    double_str_celcius = elephantList.get(0).replace("*", "").replace("C", "").replace(" ", "").trim();
                                    double_str_fahrenheit = elephantList.get(1).replace("#", "").replace("F", "").replace(" ", "").trim();

                                    if (normalLow <= Double.parseDouble(double_str_celcius) && normalHigh >= Double.parseDouble(double_str_celcius)) {



                                    } else if (moderateLow <= Double.parseDouble(double_str_celcius) && moderateHigh >= Double.parseDouble(double_str_celcius)) {



                                    } else if (maxLow <= Double.parseDouble(double_str_celcius) && maxHigh >= Double.parseDouble(double_str_celcius)) {



                                    }



                                    saveDataToFile(str_celcius+" "+"/"+" "+str_fahrenheit);

                                }catch (Exception e){

                                   e.printStackTrace();

                                }

                                if(str_celcius != null && !str_celcius.isEmpty() && str_fahrenheit != null && !str_fahrenheit.isEmpty()){
                                try {

                                    Log.d("screenshot_image",""+bmpView);
                                    camera.takePicture(null, null, pictureCallback);

                                   str_cel=str_celcius;
                                   str_fah=str_fahrenheit;

                                  }catch (Exception e){

                                }
                                        // Take screen shot
                                        // bitmap = ScreenShott.getInstance().takeScreenShotOfView(hidden_txtview);
                                        // bitmap = ScreenShott.getInstance().takeScreenShotOfJustView(hidden_txtview);
                                        // bitmap = ScreenShott.getInstance().takeScreenShotOfTextureView(hidden_textureview);
                                        // bitmap = ScreenShott.getInstance().takeScreenShotOfView(view);
                                        // Display in imageview

                                    try{
                                        //saveScreenshot();
                                        //bmpView.setImageResource(android.R.color.transparent);
                                        //bmpView.setImageResource(0);

                                        str_celcius=null;
                                        str_fahrenheit=null;

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

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
    //------------------------To check storage Permission--------------------------------------------------------------------------------------------

    //======================To resize and rotate image===========================================================
    /**
     * This method is responsible for solving the rotation issue if exist. Also scale the images to
     * 1024x1024 resolution
     *
     * @param context       The current context
     * @param selectedImage The Image URI
     * @return Bitmap image results
     * @throws IOException
     */
    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage)
            throws IOException {
        int MAX_HEIGHT = 1024;
        int MAX_WIDTH = 1024;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        imageStream = context.getContentResolver().openInputStream(selectedImage);
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

        img = rotateImageIfRequired(context, img, selectedImage);
        return img;
    }

    /**
     * Calculate an inSampleSize for use in a {@link BitmapFactory.Options} object when decoding
     * bitmaps using the decode* methods from {@link BitmapFactory}. This implementation calculates
     * the closest inSampleSize that will result in the final decoded bitmap having a width and
     * height equal to or larger than the requested width and height. This implementation does not
     * ensure a power of 2 is returned for inSampleSize which can be faster when decoding but
     * results in a larger bitmap which isn't as useful for caching purposes.
     *
     * @param options   An options object with out* params already populated (run through a decode*
     *                  method with inJustDecodeBounds==true
     * @param reqWidth  The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return The value to be used for inSampleSize
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }


    /**
     * Rotate an image if required.
     *
     * @param img           The image bitmap
     * @param selectedImage Image URI
     * @return The resulted Bitmap after manipulation
     */
    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }
    //======================End of resize and rotate image=======================================================

    //=====================================Start Delete Folder===================================================
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    //=====================================End Delete Folder=====================================================




    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

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
  //    finish(); //return to the first layout

    }



    @Override
    public void onBackPressed() {
// TODO Auto-generated method stub
        AlertDialog.Builder builder=new AlertDialog.Builder(DeviceList.this);
        // builder.setCancelable(false);
        builder.setTitle("Rate Us if u like this");
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("yes",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Toast.makeText(DeviceList.this, "Yes i wanna exit", Toast.LENGTH_LONG).show();
                if (btSocket!=null) //If the btSocket is busy
                {
                    try
                    {
                        btSocket.close(); //close connection
                        btSocket=null;
                    }
                    catch (IOException e)
                    { msg("Error");}
                }

                finish();
            }
        });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Toast.makeText(DeviceList.this, "i wanna stay on this", Toast.LENGTH_LONG).show();
                dialog.cancel();

            }
        });
        builder.setNeutralButton("Rate",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }

            }
        });
        AlertDialog alert=builder.create();
        alert.show();
        //super.onBackPressed();
    }

public void recordSetUp(){

    AlertDialog.Builder adb = new AlertDialog.Builder(this);
    // adb.setView(Integer.parseInt("Delete Folder"));
    adb.setTitle("Record Settings");
    // adb.setMessage("Are you sure you want to delete this Folder?");
    adb.setIcon(android.R.drawable.ic_dialog_alert);
    String[] anyData = {"New Record"};
   // loadSpinnerData();
    /*
    adb.setItems(anyData, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0: // horse

            }
        }
    });
     */

    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {


        }
    });
    adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(DeviceList.this, "Cancel",
                    Toast.LENGTH_SHORT).show();
            //finish();
        }
    });
    adb.show();
}



}
