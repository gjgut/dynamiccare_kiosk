package com.example.dynamiccare_kisok.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dynamiccare_kisok.Common.Component.DCActionBar;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.Excercise.Excercise;
import com.example.dynamiccare_kisok.Common.Util.UsbService;
import com.example.dynamiccare_kisok.Fragment.DetailResult;
import com.example.dynamiccare_kisok.Fragment.ExcerciseMode;
import com.example.dynamiccare_kisok.Fragment.SelectMode;
import com.example.dynamiccare_kisok.R;

import java.lang.ref.WeakReference;
import java.util.Set;

public class Main extends AppCompatActivity implements View.OnClickListener {
    DCfragment currentFragment;
    ImageButton btn_back, btn_next;
    DCActionBar customActionBar;
    static ConstraintLayout bottombar;
    FragmentManager fragmentManager;
    static boolean isIsoKinetic,isIsoTonic;
    static Excercise currentExcercise;
    private UsbService usbService;
    public static ConstraintLayout getBottombar()
    {
        return bottombar;
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case UsbService.ACTION_USB_PERMISSION_GRANTED: // USB PERMISSION GRANTED
                    Toast.makeText(context, "USB Ready", Toast.LENGTH_SHORT).show();
                    break;
                case UsbService.ACTION_USB_PERMISSION_NOT_GRANTED: // USB PERMISSION NOT GRANTED
                    Toast.makeText(context, "USB Permission not granted", Toast.LENGTH_SHORT).show();
                    break;
                case UsbService.ACTION_NO_USB: // NO USB CONNECTED
                    Toast.makeText(context, "No USB connected", Toast.LENGTH_SHORT).show();
                    break;
                case UsbService.ACTION_USB_DISCONNECTED: // USB DISCONNECTED
                    Toast.makeText(context, "USB disconnected", Toast.LENGTH_SHORT).show();
                    break;
                case UsbService.ACTION_USB_NOT_SUPPORTED: // USB NOT SUPPORTED
                    Toast.makeText(context, "USB device not supported", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private final ServiceConnection usbConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            usbService = ((UsbService.UsbBinder) arg1).getService();
//            usbService.setHandler(mHandler);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            usbService = null;
        }
    };

    public static void setisIsoKinetic(boolean value){
        isIsoKinetic = value;
    }
    public static void setIsIsoTonic(boolean value)
    {
        isIsoTonic = value;
    }
    public static boolean getisIsoKinetic()
    {
        return isIsoKinetic;
    }
    public static boolean getisIsoTonic()
    {
        return  isIsoTonic;
    }
    public static Excercise getCurrentExcercise()
    {
        return currentExcercise;
    }
    public static void setCurrentExcercise(Excercise excercise){currentExcercise = excercise;}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customActionBar = new DCActionBar(this, getSupportActionBar(), "메인");
        btn_back = findViewById(R.id.btn_back);
        btn_next = findViewById(R.id.btn_next);
        bottombar = findViewById(R.id.Bottom);

        btn_back.setOnClickListener(this);
        btn_next.setOnClickListener(this);

        ReplaceFragment(new SelectMode(this));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: {
                ReplaceFragment(currentFragment.getBackFragment(),false);
                break;
            }
            case R.id.btn_next: {
                ReplaceFragment(currentFragment.getNextFragment(),true);
                break;
            }
        }
    }

    public void ReplaceFragment(DCfragment fragment,boolean isRight)
    {

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        currentFragment = fragment;

        if(isRight && fragment.getClass() != SelectMode.class) {
            bottombar.setVisibility(View.VISIBLE);
            fragmentTransaction.setCustomAnimations(R.anim.right_in, R.anim.left_out);
        }
        else if(!isRight && fragment.getClass() != SelectMode.class)
        {
            bottombar.setVisibility(View.VISIBLE);
            fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.right_out);
        }
        else
        {
            bottombar.setVisibility(View.INVISIBLE);
            fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.right_out);
        }


        if(currentFragment.getClass() == ExcerciseMode.class  || currentFragment.getClass() == DetailResult.class)
            btn_next.setVisibility(View.INVISIBLE);
        else
            btn_next.setVisibility(View.VISIBLE);

        switch(fragment.getClass().getSimpleName())
        {
            case "Explain":
            {
                btn_next.setVisibility(View.INVISIBLE);
                btn_next.setImageDrawable(getResources().getDrawable(R.drawable.btn_instruct));
                break;
            }
            case "Instruction":
            {
                btn_next.setImageDrawable(getResources().getDrawable(R.drawable.btn_lograedy));
                break;
            }
            case "GraphResult":
            {
                btn_next.setImageDrawable(getResources().getDrawable(R.drawable.btn_logdetail));
                break;
            }
        }

        customActionBar.setHome(fragment.isHomeVisible());
        customActionBar.setTitle(fragment.getTitle());
        fragmentTransaction.replace(R.id.main_container,fragment);
        fragmentTransaction.commit();
    }

    public void ReplaceFragment(DCfragment fragment)
    {

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        currentFragment = fragment;

        if(fragment.getClass() != SelectMode.class) {
            bottombar.setVisibility(View.VISIBLE);
        }
        else
        {
            bottombar.setVisibility(View.INVISIBLE);
        }

        customActionBar.setHome(fragment.isHomeVisible());
        customActionBar.setTitle(fragment.getTitle());
        if(currentFragment.getClass() == ExcerciseMode.class  || currentFragment.getClass() == DetailResult.class)
            btn_next.setVisibility(View.INVISIBLE);
        else
            btn_next.setVisibility(View.VISIBLE);
        fragmentTransaction.replace(R.id.main_container,fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onResume() {
        super.onResume();
        setFilters();  // Start listening notifications from UsbService
        startService(UsbService.class, usbConnection, null); // Start UsbService(if it was not started before) and Bind it
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mUsbReceiver);
        unbindService(usbConnection);
    }

    private void startService(Class<?> service, ServiceConnection serviceConnection, Bundle extras) {
        if (!UsbService.SERVICE_CONNECTED) {
            Intent startService = new Intent(this, service);
            if (extras != null && !extras.isEmpty()) {
                Set<String> keys = extras.keySet();
                for (String key : keys) {
                    String extra = extras.getString(key);
                    startService.putExtra(key, extra);
                }
            }
            startService(startService);
        }
        Intent bindingIntent = new Intent(this, service);
        bindService(bindingIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void setFilters() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbService.ACTION_USB_PERMISSION_GRANTED);
        filter.addAction(UsbService.ACTION_NO_USB);
        filter.addAction(UsbService.ACTION_USB_DISCONNECTED);
        filter.addAction(UsbService.ACTION_USB_NOT_SUPPORTED);
        filter.addAction(UsbService.ACTION_USB_PERMISSION_NOT_GRANTED);
        registerReceiver(mUsbReceiver, filter);
    }

    /*
     * This handler will be passed to UsbService. Data received from serial port is displayed through this handler
     */
//    private static class MyHandler extends Handler {
//        private final WeakReference<Main> mActivity;
//
//        public MyHandler(Main activity) {
//            mActivity = new WeakReference<>(activity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case UsbService.MESSAGE_FROM_SERIAL_PORT:
//                    String data = (String) msg.obj;
//                    mActivity.get().display.append(data);
//                    break;
//                case UsbService.CTS_CHANGE:
//                    Toast.makeText(mActivity.get(), "CTS_CHANGE",Toast.LENGTH_LONG).show();
//                    break;
//                case UsbService.DSR_CHANGE:
//                    Toast.makeText(mActivity.get(), "DSR_CHANGE",Toast.LENGTH_LONG).show();
//                    break;
//            }
//        }
//    }
}
