package com.example.dynamiccare_kisok.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dynamiccare_kisok.Common.Component.DCActionBar;
import com.example.dynamiccare_kisok.Common.Component.DCActivity;
import com.example.dynamiccare_kisok.Common.Component.DCButtonManager;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.DynamicCare;
import com.example.dynamiccare_kisok.Common.Excercise.Excercise;
import com.example.dynamiccare_kisok.Common.Object.ACK;
import com.example.dynamiccare_kisok.Common.Util.ACKListener;
import com.example.dynamiccare_kisok.Common.Util.Commands;
import com.example.dynamiccare_kisok.Common.Util.DCSoundPlayer;
import com.example.dynamiccare_kisok.Common.Util.DCSoundThread;
import com.example.dynamiccare_kisok.Common.Util.UsbService;
import com.example.dynamiccare_kisok.Dialog.FinishAlert;
import com.example.dynamiccare_kisok.Dialog.NormalAlert;
import com.example.dynamiccare_kisok.Fragment.Explain;
import com.example.dynamiccare_kisok.Fragment.GraphResult;
import com.example.dynamiccare_kisok.Fragment.Instruction;
import com.example.dynamiccare_kisok.Fragment.TimeSetting;
import com.example.dynamiccare_kisok.Fragment.DetailResult;
import com.example.dynamiccare_kisok.Fragment.ExcerciseMode;
import com.example.dynamiccare_kisok.Fragment.SelectMode;
import com.example.dynamiccare_kisok.Fragment.SelectWorkOut;
import com.example.dynamiccare_kisok.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main extends DCActivity implements View.OnClickListener {
    Main main;
    DCfragment currentFragment;
    ImageButton btn_back, btn_next;
    TextView BottomRestTime;
    DCActionBar customActionBar;
    Handler handler;
    CountDownTimer countDownTimer;
    static ConstraintLayout bottombar;
    FragmentManager fragmentManager;
    static boolean isIsoKinetic, isIsoTonic, alertflag = false;
    static Excercise currentExcercise;
    static UsbService usbService;
    static DCSoundPlayer dcSoundPlayer;
    DCSoundThread dcSoundThread;
    ACKListener ackListener;
    int MeasureTime = 10, MeasureWeight = 300, count = 0;

    @Override
    protected void ViewMapping() {
        ackListener = new ACKListener(this);
        handler = new Handler();
        dcSoundPlayer = care.getDcSoundPlayer();
        count = care.getLimit();
        dcSoundThread = new DCSoundThread(this);
        customActionBar = new DCActionBar(this, getSupportActionBar(), "메인");

        btn_back = findViewById(R.id.btn_back);
        btn_next = findViewById(R.id.btn_next);
        bottombar = findViewById(R.id.Bottom);
        BottomRestTime = findViewById(R.id.usertimer);

    }



    @Override
    protected void setListener() {
        btn_back.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }


    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };
    private final ServiceConnection usbConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            try {
                usbService = ((UsbService.UsbBinder) arg1).getService();
                usbService.setMain(main);
                usbService.setHandler(ackListener);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            usbService = null;
        }
    };

    public DCSoundPlayer getDcSoundPlayer() {
        return dcSoundPlayer;
    }

    public DynamicCare getCare() {
        return care;
    }

    public String getMeasureTime() {
        return care.getMeasureTime();
    }

    public void setMeasureTime(int measureTime) {
        care.setMeasureTime(String.valueOf(measureTime));
    }

    public String getMeasureWeight() {
        return care.getMeasureWeight();
    }

    public void setMeasureWeight(int measureWeight) {
        care.setMeasureWeight(String.valueOf(measureWeight));
    }

    public void setisIsoKinetic(boolean value) {
        isIsoKinetic = value;
    }

    public void setIsIsoTonic(boolean value) {
        isIsoTonic = value;
    }

    public boolean getisIsoKinetic() {
        return isIsoKinetic;
    }

    public boolean getisIsoTonic() {
        return isIsoTonic;
    }

    public static Excercise getCurrentExcercise() {
        return currentExcercise;
    }

    public static void setCurrentExcercise(Excercise excercise) {
        currentExcercise = excercise;
    }

    public DCfragment getCurrentFragment() {
        return currentFragment;
    }

    public static UsbService getusbService() {
        return usbService;
    }

    public static ConstraintLayout getBottombar() {
        return bottombar;
    }


    public void PlaySound(int soundId) {
        dcSoundPlayer.playwithNoInterrept(soundId);
    }

    public void PlaySound(int[] stream) {
        dcSoundThread.playstream(stream);
    }

    public void StopSound() {
        dcSoundThread.stopstream();
    }

    public void setTimer(int time) {
        if (countDownTimer != null)
            countDownTimer.cancel();
        count += time;
        BottomRestTime.setTextColor(Color.WHITE);
        countDownTimer = SecondTimer();
//        countDownTimer = MinuteTimer();
        countDownTimer.start();
    }

    public void limitoff() {
        if (!care.isLimit()) {
            BottomRestTime.setVisibility(View.INVISIBLE);
            customActionBar.setTimeButton(View.INVISIBLE);
            if (countDownTimer != null)
                countDownTimer.cancel();
        }

    }

    private CountDownTimer SecondTimer()
    {
       return new CountDownTimer(count * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i("Timer", String.valueOf(count));
                if (count == 30) {
                    alertflag = true;
                    BottomRestTime.setTextColor(Color.RED);
                } else if (count < 30 &&
                        alertflag
                        && DCButtonManager.getDCState() != DCButtonManager.State.Excercise
                        && DCButtonManager.getDCState() != DCButtonManager.State.Ready
                        && currentFragment.getClass() != TimeSetting.class
                ) {
                    new FinishAlert(main).show();
                    alertflag = false;
                }
                BottomRestTime.setText(((count < 600) ? "0" + (count / 60) : (count / 60))
                        + ":" +
                        ((count % 60) < 10 ? "0" + (count % 60) : (count % 60)));
                count--;
            }

            @Override
            public void onFinish() {
                getusbService().write(Commands.Home(true));
                new NormalAlert(main, "30분이 경과되었습니다.").show();
            }
        };
    }
    private CountDownTimer MinuteTimer()
    {
        return new CountDownTimer(count * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i("Timer", String.valueOf(count));
                if (count == 600) {
                    alertflag = true;
                    BottomRestTime.setTextColor(Color.RED);
                } else if (count < 600 &&
                        alertflag
                        && DCButtonManager.getDCState() != DCButtonManager.State.Excercise
                        && DCButtonManager.getDCState() != DCButtonManager.State.Ready
                        && currentFragment.getClass() != TimeSetting.class
                ) {
                    new FinishAlert(main).show();
                    alertflag = false;
                }
                BottomRestTime.setText(((count < 36000) ? "0" + (count / 3600) : (count / 3600))
                        + ":" +
                        ((count % 3600)/60 < 10 ? "0" + (count % 3600)/60 : (count % 3600)/60));
                count--;
            }

            @Override
            public void onFinish() {
                getusbService().write(Commands.Home(true));
                new NormalAlert(main, "30분이 경과되었습니다.").show();
            }
        };
    }

    public void HandleACK(ACK ack) {
        try {
            Toast.makeText(this, "Command:" + ack.getCommandCode(), Toast.LENGTH_SHORT).show();
            switch (ack.getCommandCode()) {
                case "CHM":
                    PlaySound(new int[]{R.raw.excercise_is_going_to_stop, R.raw.thank_you_for_your_efforts, R.raw.excercise_is_going_to_stop_english, R.raw.thank_you_for_your_efforts_english});
                    usbService.write(Commands.Home(true));
                    ChangeActivity(Login.class);
                    break;
                case "ACB":
                    switch (ack.getData()) {
                        case "1":
                            PlaySound(new int[]{R.raw.start_excercise, R.raw.start_excercise_english});
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
                    switch (ack.getData()) {
                        case "1":
                            PlaySound(new int[]{R.raw.take_down_the_bar, R.raw.take_down_the_bar_english});
                            break;
                        case "2":
                            PlaySound(new int[]{R.raw.raise_the_bar, R.raw.raise_the_bar_english});
                            break;
                    }
                    break;
                case "AEE":
                case "ACS":
                    if(currentFragment.getClass() == ExcerciseMode.class)
                        break;
                    PlaySound(dcSoundPlayer.getCoundSound(ack.getData()));
                    break;
                case "ASS":
                    PlaySound(dcSoundPlayer.getSetSound(ack.getData()));
                    break;
                case "PCA":
                    DCButtonManager.setDCState(DCButtonManager.State.Setted);
                    break;
                case "AME":
                case "ASP":
                case "ACD":
                    break;
            }
            currentFragment.HandleACK(ack);
            Log.i("Main-Broadcasted", ack.getCommandCode());
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Main-Broadcasted", ack.getCommandCode() + e.toString());
            Toast.makeText(this, "ACK:" + ack.getCommandCode() + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void ReplaceFragment(DCfragment fragment, boolean isRight) {
        try {
            frgobSetting(fragment);
            frgcommit(isRight, fragment);
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    public void ReplaceFragment(DCfragment fragment) {
        try {
            frgobSetting(fragment);
            frgcommit(fragment);
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    public void frgobSetting(DCfragment fg) {
        Class frgClass = fg.getClass();

        if (frgClass == TimeSetting.class) {
            bottombar.setVisibility(View.INVISIBLE);
            btn_next.setVisibility(View.INVISIBLE);
            customActionBar.setTimeButton(View.INVISIBLE);
        } else {
            bottombar.setVisibility(View.VISIBLE);
            if (care.isLimit())
                customActionBar.setTimeButton(View.VISIBLE);
            else
                customActionBar.setTimeButton(View.INVISIBLE);

            if (frgClass == ExcerciseMode.class
                    || frgClass == SelectMode.class
                    || frgClass == SelectWorkOut.class
                    || frgClass == Explain.class) {
                btn_next.setVisibility(View.INVISIBLE);
                if (frgClass == Explain.class)
                    btn_next.setImageDrawable(getResources().getDrawable(R.drawable.btn_instruct));
            } else {
                btn_next.setVisibility(View.VISIBLE);
                if (frgClass == Instruction.class)
                    btn_next.setImageDrawable(getResources().getDrawable(R.drawable.btn_lograedy));
                else if (frgClass == GraphResult.class)
                    btn_next.setImageDrawable(getResources().getDrawable(R.drawable.btn_logdetail));
            }
        }
    }

    public void frgcommit(boolean isRight, DCfragment fg) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        currentFragment = fg;
        bottombar.setVisibility(View.VISIBLE);
        if (isRight) {
            fragmentTransaction.setCustomAnimations(R.anim.right_in, R.anim.left_out);
        } else if (!isRight) {
            fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.right_out);
        }
        customActionBar.setHome(fg.isHomeVisible());
        customActionBar.setTitle(fg.getTitle());
        fragmentTransaction.replace(R.id.main_container, fg);
        fragmentTransaction.commit();
    }

    public void frgcommit(DCfragment fg) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        currentFragment = fg;
        customActionBar.setHome(fg.isHomeVisible());
        customActionBar.setTitle(fg.getTitle());
        fragmentTransaction.replace(R.id.main_container, fg);
        fragmentTransaction.commit();
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main = this;
        try {
            ViewMapping();
            setListener();
//            countDownTimer.start();
            Toast.makeText(this, care.getDeviceID().toString(), Toast.LENGTH_LONG).show();
            if (care.isLimit())
                ReplaceFragment(new TimeSetting(this));
            else
                ReplaceFragment(new SelectMode(this));
            limitoff();
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    @Override
    public void onClick(View v) {
        try {
            String freagmentname = currentFragment.getClass().getSimpleName();
            StopSound();
            PlaySound(R.raw.back_button);
            if (v.getId() == R.id.btn_back) {
                switch (freagmentname) {
                    case "ExcerciseMode":
                        main.setCurrentExcercise(null);
                        break;
                    case "Explain":
                        main.getusbService().write(Commands.Home(true));
                        break;
                    case "SelectMode":
                        ChangeActivity(Login.class);
                        break;
                }
                ReplaceFragment(currentFragment.getBackFragment(), false);
            } else if (v.getId() == R.id.btn_next)
                ReplaceFragment(currentFragment.getNextFragment(), true);
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            setFilters();  // Start listening notifications from UsbService
            startService(UsbService.class, usbConnection, null); // Start UsbService(if it was not started before) and Bind it
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    @Override
    public void onPause() {
        try {
            super.onPause();
            unregisterReceiver(mUsbReceiver);
            unbindService(usbConnection);
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }


    @Override
    public void onDestroy() {
        try {
            super.onDestroy();
            dcSoundThread.stopstream();
            if (countDownTimer != null)
                countDownTimer.cancel();
            care.setLimit(30);
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }


}

