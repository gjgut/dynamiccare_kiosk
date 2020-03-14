package com.example.dynamiccare_kisok.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Application;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dynamiccare_kisok.Common.Component.DCActionBar;
import com.example.dynamiccare_kisok.Common.Component.DCActionButton;
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
import com.example.dynamiccare_kisok.Dialog.ExcerciseFinished;
import com.example.dynamiccare_kisok.Dialog.FinishAlert;
import com.example.dynamiccare_kisok.Dialog.LoadPlan;
import com.example.dynamiccare_kisok.Fragment.Explain;
import com.example.dynamiccare_kisok.Fragment.TimeSetting;
import com.example.dynamiccare_kisok.Fragment.DetailResult;
import com.example.dynamiccare_kisok.Fragment.ExcerciseMode;
import com.example.dynamiccare_kisok.Fragment.SelectMode;
import com.example.dynamiccare_kisok.Fragment.SelectWorkOut;
import com.example.dynamiccare_kisok.R;

import java.lang.ref.WeakReference;
import java.util.Set;

import static com.example.dynamiccare_kisok.Common.DynamicCare.getDeviceID;

public class Main extends AppCompatActivity implements View.OnClickListener {
    FinishAlert FinishAlert;
    Main main;
    DCfragment currentFragment;
    ImageButton btn_back, btn_next;
    TextView BottomRestTime;
    DCActionBar customActionBar;
    Handler handler;
    CountDownTimer countDownTimer;
    DynamicCare care;
    static ConstraintLayout bottombar;
    FragmentManager fragmentManager;
    static boolean isIsoKinetic, isIsoTonic, alertflag = false;
    static Excercise currentExcercise;
    static UsbService usbService;
    static DCSoundPlayer dcSoundPlayer;
    DCSoundThread dcSoundThread;
    ACKListener ackListener;
    int MeasureTime = 10, MeasureWeight = 300, count = 0;

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

                }
                if (count < 30) {
                    if (alertflag && DCButtonManager.getDCState() != DCButtonManager.State.Excercise && DCButtonManager.getDCState() != DCButtonManager.State.Ready) {
                        FinishAlert.show();
                        alertflag = false;
                    }
                }
                if (count < 10) {
                    BottomRestTime.setText("00:0" + String.valueOf(count));
                } else if (count > 10 && count % 60 < 10)
                    BottomRestTime.setText("0" + String.valueOf(count / 60) + ":0" + String.valueOf(count % 60));
                else
                    BottomRestTime.setText("0" + String.valueOf(count / 60) + ":" + String.valueOf(count % 60));
                count--;
            }

            @Override
            public void onFinish() {
                getusbService().write(Commands.Home(true).getBytes());
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                ExcerciseFinished finished = new ExcerciseFinished(main,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        },
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                finished.show();
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
                    usbService.write(Commands.Home(true).getBytes());
                    Intent intent = new Intent(this, Login.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    finish();
                    break;
                case "AME":
                    break;
                case "ASP":
                    break;
                case "ACD":
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
                    switch (ack.getData()) {
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
                            PlaySound(new int[]{R.raw.excercise_is_going_to_stop, R.raw.thank_you_for_your_efforts, R.raw.excercise_is_going_to_stop_english, R.raw.thank_you_for_your_efforts_english});
                            break;
                        case "01":
                            PlaySound(new int[]{R.raw.one_set_complete, R.raw.take_a_break, R.raw.one_set_complete_english, R.raw.take_a_break_english});
                            break;
                        case "02":
                            PlaySound(new int[]{R.raw.two_set_complete, R.raw.take_a_break, R.raw.two_set_complete_english, R.raw.take_a_break_english});
                            break;
                        case "03":
                            PlaySound(new int[]{R.raw.three_set_complete, R.raw.take_a_break, R.raw.three_set_complete_english, R.raw.take_a_break_english});
                            break;
                        case "04":
                            PlaySound(new int[]{R.raw.take_a_break, R.raw.four_sets_completed_english, R.raw.take_a_break_english});
                            break;
                        case "05":
                            PlaySound(new int[]{R.raw.take_a_break, R.raw.five_sets_completed_english, R.raw.take_a_break_english});
                            break;
                        case "06":
                            PlaySound(new int[]{R.raw.take_a_break, R.raw.six_sets_completed_english, R.raw.take_a_break_english});
                            break;
                        case "07":
                            PlaySound(new int[]{R.raw.take_a_break, R.raw.seven_sets_completed_english, R.raw.take_a_break_english});
                            break;
                        case "08":
                            PlaySound(new int[]{R.raw.take_a_break, R.raw.eight_sets_completed_english, R.raw.take_a_break_english});
                            break;
                        case "09":
                            PlaySound(new int[]{R.raw.take_a_break, R.raw.nine_sets_completed_english, R.raw.take_a_break_english});
                            break;
                        case "10":
                            PlaySound(new int[]{R.raw.take_a_break, R.raw.ten_sets_completed_english, R.raw.take_a_break_english});
                            break;
                    }
                    break;
                case "PCA":
                    DCButtonManager.setDCState(DCButtonManager.State.Setted);
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


        ackListener = new ACKListener(this);
        handler = new Handler();

        care = (DynamicCare) getApplicationContext();
        dcSoundPlayer = care.getDcSoundPlayer();
        count = care.getLimit();

        countDownTimer = new CountDownTimer(count * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                try {
                    BottomRestTime.setText("00:" + String.valueOf(count));
                    count--;

                    if (count == 30) {
                        FinishAlert.show();
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
                Intent intent = new Intent(main, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                ExcerciseFinished finished = new ExcerciseFinished(main,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        },
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                finished.show();
            }
        };

        countDownTimer.start();


        dcSoundThread = new DCSoundThread(this);
        customActionBar = new DCActionBar(this, getSupportActionBar(), "메인");

        btn_back = findViewById(R.id.btn_back);
        btn_next = findViewById(R.id.btn_next);
        bottombar = findViewById(R.id.Bottom);
        BottomRestTime = (TextView) findViewById(R.id.usertimer);

        btn_back.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        Toast.makeText(this, getCare().getDeviceID().toString(), Toast.LENGTH_LONG);

        Toast.makeText(this, getCare().getDeviceID().toString(), Toast.LENGTH_LONG).show();

        if (care.isLimit())
            ReplaceFragment(new TimeSetting(this));
        else
            ReplaceFragment(new SelectMode(this));


        limitoff();
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btn_back: {
                    PlaySound(R.raw.back_button);
                    StopSound();
                    if (currentFragment.getClass().getSimpleName() == "ExcerciseMode")
                        main.setCurrentExcercise(null);
                    else if (currentFragment.getClass().getSimpleName() == "Explain") {
                        main.getusbService().write(Commands.Home(true).getBytes());
                    }
                    if (currentFragment.getClass().getSimpleName().equals("SelectMode")) {
                        Intent intent = new Intent(main, Login.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.left_in, R.anim.right_out);
                        finish();
                    }
                    ReplaceFragment(currentFragment.getBackFragment(), false);
                    break;
                }
                case R.id.btn_next: {
                    PlaySound(R.raw.back_button);
                    ReplaceFragment(currentFragment.getNextFragment(), true);
                    break;
                }
            }
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
        super.onPause();
        unregisterReceiver(mUsbReceiver);
        unbindService(usbConnection);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        dcSoundThread.stopstream();
        if (countDownTimer != null)
            countDownTimer.cancel();
    }


}

