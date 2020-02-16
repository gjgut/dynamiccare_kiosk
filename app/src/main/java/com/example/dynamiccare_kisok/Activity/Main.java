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
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dynamiccare_kisok.Common.Component.DCActionBar;
import com.example.dynamiccare_kisok.Common.Component.DCActionButton;
import com.example.dynamiccare_kisok.Common.Component.DCButtonManager;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.Excercise.Excercise;
import com.example.dynamiccare_kisok.Common.Util.ACK;
import com.example.dynamiccare_kisok.Common.Util.ACKListener;
import com.example.dynamiccare_kisok.Common.Util.DCSoundPlayer;
import com.example.dynamiccare_kisok.Common.Util.DCSoundThread;
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
    static boolean isIsoKinetic, isIsoTonic;
    static Excercise currentExcercise;
    static UsbService usbService;
    DCSoundPlayer dcSoundPlayer;
    DCSoundThread dcSoundThread;
    ACKListener ackListener;

    public static UsbService getusbService() {
        return usbService;
    }

    public static ConstraintLayout getBottombar() {
        return bottombar;
    }

    public void PlaySound(int soundId) {
        dcSoundPlayer.play(soundId);
    }
    public void PlaySound(int []stream)
    {
        dcSoundThread.playstream(stream);
    }

    public void HandleACK(ACK ack) {
        switch (ack.getCommandCode())
        {
            case "AME":
                currentFragment.HandleACK(ack);
                break;
            case "ASP":
                currentFragment.HandleACK(ack);
                break;
            case "ACD":
                currentFragment.HandleACK(ack);
                break;
            case "ACB":
                switch(ack.getData())
                {
                    case "1":
                        PlaySound(new int[]{R.raw.start_excercise,R.raw.start_excercise_english});
                        break;
                    case "2":
                        PlaySound(new int[]{R.raw.bee_measurement_begin});
                        break;
                    case "3":
                        PlaySound(R.raw.ring);
                        break;
                }
                break;
            case "AET":
                switch (ack.getData())
                {
                    case "1":
                        PlaySound(new int[]{R.raw.take_down_the_bar,R.raw.take_down_the_bar_english});
                        break;
                    case"2":
                        PlaySound(new int[]{R.raw.raise_the_bar,R.raw.raise_the_bar_english});
                        break;
                }
                break;
            case "AEE":
            case "ACS":
                switch (ack.getData())
                {
                    case "01":
                        PlaySound(new int[]{R.raw.one});
                        break;
                    case "02":
                        PlaySound(new int[]{R.raw.two});
                        break;
                    case "03":
                        PlaySound(new int[]{R.raw.three});
                        break;
                    case "04":
                        PlaySound(new int[]{R.raw.four});
                        break;
                    case "05":
                        PlaySound(new int[]{R.raw.five});
                        break;
                    case "06":
                        PlaySound(new int[]{R.raw.six});
                        break;
                    case "07":
                        PlaySound(new int[]{R.raw.seven});
                        break;
                    case "08":
                        PlaySound(new int[]{R.raw.eight});
                        break;
                    case "09":
                        PlaySound(new int[]{R.raw.nine});
                        break;
                    case "10":
                        PlaySound(new int[]{R.raw.ten});
                        break;
                    case "11":
                        PlaySound(new int[]{R.raw.eleven});
                        break;
                    case "12":
                        PlaySound(new int[]{R.raw.twelve});
                        break;
                    case "13":
                        PlaySound(new int[]{R.raw.threeteen});
                        break;
                    case "14":
                        PlaySound(new int[]{R.raw.fourteen});
                        break;
                    case "15":
                        PlaySound(new int[]{R.raw.fifteen});
                        break;
                    case "16":
                        PlaySound(new int[]{R.raw.sixteen});
                        break;
                    case "17":
                        PlaySound(new int[]{R.raw.seventeen});
                        break;
                    case "18":
                        PlaySound(new int[]{R.raw.eighteen});
                        break;
                    case "19":
                        PlaySound(new int[]{R.raw.nineteen});
                        break;
                    case "20":
                        PlaySound(new int[]{R.raw.twenty});
                        break;
                    case "21":
                        PlaySound(new int[]{R.raw.twenty_one});
                        break;
                    case "22":
                        PlaySound(new int[]{R.raw.twenty_two});
                        break;
                    case "23":
                        PlaySound(new int[]{R.raw.twenty_three});
                        break;
                    case "24":
                        PlaySound(new int[]{R.raw.twenty_four});
                        break;
                    case "25":
                        PlaySound(new int[]{R.raw.twenty_five});
                        break;
                    case "26":
                        PlaySound(new int[]{R.raw.twenty_six});
                        break;
                    case "27":
                        PlaySound(new int[]{R.raw.twenty_seven});
                        break;
                    case "28":
                        PlaySound(new int[]{R.raw.twenty_eight});
                        break;
                    case "29":
                        PlaySound(new int[]{R.raw.twenty_nine});
                        break;
                    case "30":
                        PlaySound(new int[]{R.raw.thirty});
                        break;
                    case "31":
                        PlaySound(new int[]{R.raw.thirty_one});
                        break;
                    case "32":
                        PlaySound(new int[]{R.raw.thirty_two});
                        break;
                    case "33":
                        PlaySound(new int[]{R.raw.thirty_three});
                        break;
                    case "34":
                        PlaySound(new int[]{R.raw.thirty_three});
                        break;
                    case "35":
                        PlaySound(new int[]{R.raw.thirty_five});
                        break;
                    case "36":
                        PlaySound(new int[]{R.raw.thirty_six});
                        break;
                    case "37":
                        PlaySound(new int[]{R.raw.thirty_seven});
                        break;
                    case "38":
                        PlaySound(new int[]{R.raw.thirty_eight});
                        break;
                    case "39":
                        PlaySound(new int[]{R.raw.thirty_nine});
                        break;
                    case "40":
                        PlaySound(new int[]{R.raw.fourty});
                        break;
                    case "41":
                        PlaySound(new int[]{R.raw.fourty_one});
                        break;
                    case "42":
                        PlaySound(new int[]{R.raw.fourty_two});
                        break;
                    case "43":
                        PlaySound(new int[]{R.raw.fourty_three});
                        break;
                    case "44":
                        PlaySound(new int[]{R.raw.fourty_four});
                        break;
                    case "45":
                        PlaySound(new int[]{R.raw.fourty_five});
                        break;
                    case "46":
                        PlaySound(new int[]{R.raw.fourty_six});
                        break;
                    case "47":
                        PlaySound(new int[]{R.raw.fourty_seven});
                        break;
                    case "48":
                        PlaySound(new int[]{R.raw.fourty_eight});
                        break;
                    case "49":
                        PlaySound(new int[]{R.raw.fourty_nine});
                        break;
                    case "50":
                        PlaySound(new int[]{R.raw.fifty});
                        break;
                        
                }
                break;
            case "ASS":
                switch (ack.getData()) {
                    case "00":
                        PlaySound(new int[]{R.raw.thank_you_for_your_efforts,R.raw.thank_you_for_your_efforts_english});
                        break;
                    case "01":
                        PlaySound(new int[]{R.raw.one_set_complete,R.raw.one_set_complete_english});
                        break;
                    case "02":
                        PlaySound(new int[]{R.raw.two_set_complete,R.raw.two_set_complete_english});
                        break;
                    case "03":
                        PlaySound(new int[]{R.raw.three_set_complete,R.raw.two_set_complete_english});
                        break;
                    case "04":
                        PlaySound(new int[]{R.raw.four_sets_completed_english});
                        break;
                    case "05":
                        PlaySound(new int[]{R.raw.five_sets_completed_english});
                        break;
                    case "06":
                        PlaySound(new int[]{R.raw.six_sets_completed_english});
                        break;
                    case "07":
                        PlaySound(new int[]{R.raw.seven_sets_completed_english});
                        break;
                    case "08":
                        PlaySound(new int[]{R.raw.eight_sets_completed_english});
                        break;
                    case "09":
                        PlaySound(new int[]{R.raw.nine_sets_completed_english});
                        break;
                    case "10":
                        PlaySound(new int[]{R.raw.ten_sets_completed_english});
                        break;
                }
                break;
            case "PCA":
                DCButtonManager.setDCState(DCButtonManager.State.Setted);
        }
        currentFragment.HandleACK(ack);
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
            usbService.setHandler(ackListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            usbService = null;
        }
    };

    public static void setisIsoKinetic(boolean value) {
        isIsoKinetic = value;
    }

    public static void setIsIsoTonic(boolean value) {
        isIsoTonic = value;
    }

    public static boolean getisIsoKinetic() {
        return isIsoKinetic;
    }

    public static boolean getisIsoTonic() {
        return isIsoTonic;
    }

    public static Excercise getCurrentExcercise() {
        return currentExcercise;
    }

    public static void setCurrentExcercise(Excercise excercise) {
        currentExcercise = excercise;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ackListener = new ACKListener(this);

        dcSoundPlayer = new DCSoundPlayer();
        dcSoundPlayer.initSounds(this);
        dcSoundThread = new DCSoundThread(this);
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
                PlaySound(R.raw.back_button);
                ReplaceFragment(currentFragment.getBackFragment(), false);
                break;
            }
            case R.id.btn_next: {
                PlaySound(R.raw.back_button);
                ReplaceFragment(currentFragment.getNextFragment(), true);
                break;
            }
        }
    }

    public void ReplaceFragment(DCfragment fragment, boolean isRight) {

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        currentFragment = fragment;

        if (isRight && fragment.getClass() != SelectMode.class) {
            bottombar.setVisibility(View.VISIBLE);
            fragmentTransaction.setCustomAnimations(R.anim.right_in, R.anim.left_out);
        } else if (!isRight && fragment.getClass() != SelectMode.class) {
            bottombar.setVisibility(View.VISIBLE);
            fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.right_out);
        } else {
            bottombar.setVisibility(View.INVISIBLE);
            fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.right_out);
        }


        if (currentFragment.getClass() == ExcerciseMode.class || currentFragment.getClass() == DetailResult.class)
            btn_next.setVisibility(View.INVISIBLE);
        else
            btn_next.setVisibility(View.VISIBLE);

        switch (fragment.getClass().getSimpleName()) {
            case "Explain": {
                btn_next.setVisibility(View.INVISIBLE);
                btn_next.setImageDrawable(getResources().getDrawable(R.drawable.btn_instruct));
                break;
            }
            case "Instruction": {
                btn_next.setImageDrawable(getResources().getDrawable(R.drawable.btn_lograedy));
                break;
            }
            case "GraphResult": {
                btn_next.setImageDrawable(getResources().getDrawable(R.drawable.btn_logdetail));
                break;
            }
        }

        customActionBar.setHome(fragment.isHomeVisible());
        customActionBar.setTitle(fragment.getTitle());
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();
    }

    public void ReplaceFragment(DCfragment fragment) {

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        currentFragment = fragment;

        if (fragment.getClass() != SelectMode.class) {
            bottombar.setVisibility(View.VISIBLE);
        } else {
            bottombar.setVisibility(View.INVISIBLE);
        }

        customActionBar.setHome(fragment.isHomeVisible());
        customActionBar.setTitle(fragment.getTitle());
        if (currentFragment.getClass() == ExcerciseMode.class || currentFragment.getClass() == DetailResult.class)
            btn_next.setVisibility(View.INVISIBLE);
        else
            btn_next.setVisibility(View.VISIBLE);
        fragmentTransaction.replace(R.id.main_container, fragment);
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

}
