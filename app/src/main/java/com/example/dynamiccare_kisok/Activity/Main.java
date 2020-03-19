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
    FinishAlert FinishAlert;
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
    HashMap<String, Integer> CountSound = new HashMap<String, Integer>();
    HashMap<String, int[]> SetSound = new HashMap<String, int[]>();
    int MeasureTime = 10, MeasureWeight = 300, count = 0;

    @Override
    protected void ViewMapping() {
        ackListener = new ACKListener(this);
        handler = new Handler();
        dcSoundPlayer = care.getDcSoundPlayer();
        count = care.getLimit();
        countDownTimer = new CountDownTimer(count * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                try {
                    BottomRestTime.setText(((count < 600) ? "0" + String.valueOf(count / 60) : String.valueOf(count / 60)) + ":" + ((count % 60) < 10 ? "0" + String.valueOf(count % 60) : String.valueOf(count % 60)));
                    count--;
                    if (count == 30) {
                        new FinishAlert(main).show();
                        BottomRestTime.setTextColor(Color.RED);
                    }
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                getusbService().write("$CHM08".getBytes());
                new NormalAlert(main, "30분이 경과되었습니다.").show();
            }
        };
        dcSoundThread = new DCSoundThread(this);
        customActionBar = new DCActionBar(this, getSupportActionBar(), "메인");

        btn_back = findViewById(R.id.btn_back);
        btn_next = findViewById(R.id.btn_next);
        bottombar = findViewById(R.id.Bottom);
        BottomRestTime = findViewById(R.id.usertimer);

        makeSoundMap();
    }

    private void makeSoundMap() {
        CountSound.put("01", R.raw.one);
        CountSound.put("02", R.raw.two);
        CountSound.put("03", R.raw.three);
        CountSound.put("04", R.raw.four);
        CountSound.put("05", R.raw.five);
        CountSound.put("06", R.raw.six);
        CountSound.put("07", R.raw.seven);
        CountSound.put("08", R.raw.eight);
        CountSound.put("09", R.raw.nine);
        CountSound.put("10", R.raw.ten);
        CountSound.put("11", R.raw.eleven);
        CountSound.put("12", R.raw.twelve);
        CountSound.put("13", R.raw.threeteen);
        CountSound.put("14", R.raw.fourteen);
        CountSound.put("15", R.raw.fifteen);
        CountSound.put("16", R.raw.sixteen);
        CountSound.put("17", R.raw.seventeen);
        CountSound.put("18", R.raw.eighteen);
        CountSound.put("19", R.raw.nineteen);
        CountSound.put("20", R.raw.twenty);
        CountSound.put("21", R.raw.twenty_one);
        CountSound.put("22", R.raw.twenty_two);
        CountSound.put("23", R.raw.twenty_three);
        CountSound.put("24", R.raw.twenty_four);
        CountSound.put("25", R.raw.twenty_five);
        CountSound.put("26", R.raw.twenty_six);
        CountSound.put("27", R.raw.twenty_seven);
        CountSound.put("28", R.raw.twenty_eight);
        CountSound.put("29", R.raw.twenty_nine);
        CountSound.put("30", R.raw.thirty);
        CountSound.put("31", R.raw.thirty_one);
        CountSound.put("32", R.raw.thirty_two);
        CountSound.put("33", R.raw.thirty_three);
        CountSound.put("34", R.raw.thirty_three);
        CountSound.put("35", R.raw.thirty_five);
        CountSound.put("36", R.raw.thirty_six);
        CountSound.put("37", R.raw.thirty_seven);
        CountSound.put("38", R.raw.thirty_eight);
        CountSound.put("39", R.raw.thirty_nine);
        CountSound.put("40", R.raw.fourty);
        CountSound.put("41", R.raw.fourty_one);
        CountSound.put("42", R.raw.fourty_two);
        CountSound.put("43", R.raw.fourty_three);
        CountSound.put("44", R.raw.fourty_four);
        CountSound.put("45", R.raw.fourty_five);
        CountSound.put("46", R.raw.fourty_six);
        CountSound.put("47", R.raw.fourty_seven);
        CountSound.put("48", R.raw.fourty_eight);
        CountSound.put("49", R.raw.fourty_nine);
        CountSound.put("50", R.raw.fifty);

        SetSound.put("00", new int[]{R.raw.excercise_is_going_to_stop, R.raw.thank_you_for_your_efforts, R.raw.excercise_is_going_to_stop_english, R.raw.thank_you_for_your_efforts_english});
        SetSound.put("01", new int[]{R.raw.one_set_complete, R.raw.take_a_break, R.raw.one_set_complete_english, R.raw.take_a_break_english});
        SetSound.put("02", new int[]{R.raw.two_set_complete, R.raw.take_a_break, R.raw.two_set_complete_english, R.raw.take_a_break_english});
        SetSound.put("03", new int[]{R.raw.three_set_complete, R.raw.take_a_break, R.raw.three_set_complete_english, R.raw.take_a_break_english});
        SetSound.put("04", new int[]{R.raw.take_a_break, R.raw.four_sets_completed_english, R.raw.take_a_break_english});
        SetSound.put("05", new int[]{R.raw.take_a_break, R.raw.five_sets_completed_english, R.raw.take_a_break_english});
        SetSound.put("06", new int[]{R.raw.take_a_break, R.raw.six_sets_completed_english, R.raw.take_a_break_english});
        SetSound.put("07", new int[]{R.raw.take_a_break, R.raw.seven_sets_completed_english, R.raw.take_a_break_english});
        SetSound.put("08", new int[]{R.raw.take_a_break, R.raw.eight_sets_completed_english, R.raw.take_a_break_english});
        SetSound.put("09", new int[]{R.raw.take_a_break, R.raw.nine_sets_completed_english, R.raw.take_a_break_english});
        SetSound.put("10", new int[]{R.raw.take_a_break, R.raw.ten_sets_completed_english, R.raw.take_a_break_english});
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
        countDownTimer = new CountDownTimer(count * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i("Timer", String.valueOf(count));
                if (count == 30) {
                    alertflag = true;
                    BottomRestTime.setTextColor(Color.RED);
                } else if (count < 30 &&
                        alertflag && DCButtonManager.getDCState() != DCButtonManager.State.Excercise
                        && DCButtonManager.getDCState() != DCButtonManager.State.Ready
                ) {
                    new FinishAlert(main).show();
                    alertflag = false;
                }
                BottomRestTime.setText(((count < 600) ? "0" + String.valueOf(count / 60) : String.valueOf(count / 60))
                        + ":" +
                        ((count % 60) < 10 ? "0" + String.valueOf(count % 60) : String.valueOf(count % 60)));
                count--;
            }

            @Override
            public void onFinish() {
                getusbService().write(Commands.Home(true));
                new NormalAlert(main, "30분이 경과되었습니다.").show();
            }
        };
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
                    PlaySound(CountSound.get(ack.getData()));
                    break;
                case "ASS":
                    PlaySound(SetSound.get(ack.getData()));
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

            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            currentFragment = fragment;

            if (fragment.getClass() != TimeSetting.class) {
                bottombar.setVisibility(View.VISIBLE);
                if (care.isLimit())
                    customActionBar.setTimeButton(View.VISIBLE);
                else
                    customActionBar.setTimeButton(View.INVISIBLE);
            } else {
                bottombar.setVisibility(View.INVISIBLE);
                customActionBar.setTimeButton(View.INVISIBLE);
            }

            if (isRight) {
                bottombar.setVisibility(View.VISIBLE);
                fragmentTransaction.setCustomAnimations(R.anim.right_in, R.anim.left_out);
            } else if (!isRight) {
                bottombar.setVisibility(View.VISIBLE);
                fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.right_out);
            }


            if (currentFragment.getClass() == ExcerciseMode.class ||
                    currentFragment.getClass() == DetailResult.class ||
                    currentFragment.getClass() == Explain.class ||
                    currentFragment.getClass() == SelectWorkOut.class)
                btn_next.setVisibility(View.INVISIBLE);
            else
                btn_next.setVisibility(View.VISIBLE);

            switch (fragment.getClass().getSimpleName()) {
                case "SelectMode":
                    btn_next.setVisibility(View.INVISIBLE);
                    break;
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

            if (currentFragment.getClass() == ExcerciseMode.class
                    || currentFragment.getClass() == Explain.class
                    || currentFragment.getClass() == SelectMode.class
                    || currentFragment.getClass() == SelectWorkOut.class
                    || currentFragment.getClass() == TimeSetting.class)
                btn_next.setVisibility(View.INVISIBLE);
            else
                btn_next.setVisibility(View.VISIBLE);

            customActionBar.setHome(fragment.isHomeVisible());
            customActionBar.setTitle(fragment.getTitle());
            fragmentTransaction.replace(R.id.main_container, fragment);
            fragmentTransaction.commit();

        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    public void ReplaceFragment(DCfragment fragment) {

        try {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            currentFragment = fragment;

            if (fragment.getClass() != TimeSetting.class) {
                bottombar.setVisibility(View.VISIBLE);
                if (care.isLimit())
                    customActionBar.setTimeButton(View.VISIBLE);
                else
                    customActionBar.setTimeButton(View.INVISIBLE);
            } else {
                bottombar.setVisibility(View.INVISIBLE);
                customActionBar.setTimeButton(View.INVISIBLE);
            }

            customActionBar.setHome(fragment.isHomeVisible());
            customActionBar.setTitle(fragment.getTitle());

            if (currentFragment.getClass() == ExcerciseMode.class
                    || currentFragment.getClass() == DetailResult.class
                    || currentFragment.getClass() == SelectMode.class
                    || currentFragment.getClass() == SelectWorkOut.class
                    || currentFragment.getClass() == TimeSetting.class)
                btn_next.setVisibility(View.INVISIBLE);
            else
                btn_next.setVisibility(View.VISIBLE);


            fragmentTransaction.replace(R.id.main_container, fragment);
            fragmentTransaction.commit();

        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
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
            countDownTimer.start();
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
                    default:
                        ReplaceFragment(currentFragment.getBackFragment(), false);
                        break;
                }
            }
            else if (v.getId() == R.id.btn_back)
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

