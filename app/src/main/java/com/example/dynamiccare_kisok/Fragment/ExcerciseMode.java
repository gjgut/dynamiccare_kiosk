package com.example.dynamiccare_kisok.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Excercise.ArmCurl;
import com.example.dynamiccare_kisok.Common.Excercise.ArmExtension;
import com.example.dynamiccare_kisok.Common.Excercise.BenchPress;
import com.example.dynamiccare_kisok.Common.Excercise.CarfRaise;
import com.example.dynamiccare_kisok.Common.Excercise.DeadLift;
import com.example.dynamiccare_kisok.Common.Excercise.Excercise;
import com.example.dynamiccare_kisok.Common.Excercise.LatPullDown;
import com.example.dynamiccare_kisok.Common.Excercise.ShoulderPress;
import com.example.dynamiccare_kisok.Common.Excercise.Squat;
import com.example.dynamiccare_kisok.Common.Object.ACK;
import com.example.dynamiccare_kisok.Common.Object.Workout;
import com.example.dynamiccare_kisok.Common.Util.ACKListener;
import com.example.dynamiccare_kisok.Common.Util.AsyncTask.SendWorkoutTask;
import com.example.dynamiccare_kisok.Common.Util.Commands;
import com.example.dynamiccare_kisok.Common.Component.DCActionButton;
import com.example.dynamiccare_kisok.Common.Component.DCButton;
import com.example.dynamiccare_kisok.Common.Component.DCButtonManager;
import com.example.dynamiccare_kisok.Common.Component.DCEditText;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.Util.DCHttp;
import com.example.dynamiccare_kisok.Common.Util.DCSoundPlayer;
import com.example.dynamiccare_kisok.Common.Util.DCSoundThread;
import com.example.dynamiccare_kisok.R;
import com.example.dynamiccare_kisok.Test.Runnable.ExcerciseReady1;
import com.example.dynamiccare_kisok.Test.Runnable.ExcerciseStart;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ExcerciseMode extends DCfragment {

    DCButtonManager.State prevstate;
    Bundle savedState;
    Workout workout;
    DCButton bench, squat, deadlift, press, curl, extension, latpull, carf;
    TableLayout exc_table;
    LinearLayout exc_rest;
    DCActionButton start, stop, ready;
    DCButtonManager dcButtonManager;
    DCEditText edt_rest, edt_weight, edt_set, edt_count;
    TextView txt_count, txt_set, rest_time;
    ImageView Body;
    LinearLayout container;
    Spinner spin_level;
    boolean isProgram = false, onSchedule = false;
    int count;
    Handler handler = new Handler();
    boolean isResume = false;

    public ExcerciseMode(){}

    public ExcerciseMode(Main main) {
        super(main);
        main.getusbService().write(Commands.ExcerciseMode(main.getisIsoKinetic()).getBytes());
        main.PlaySound(new int[]{R.raw.excercise_mode, R.raw.excercise_mode_english});
    }

    public ExcerciseMode(Main main, Bundle bundle) {
        super(main);
        savedState = bundle;
    }

    public ExcerciseMode(Main main, Workout workout, boolean isProgram) {
        super(main);
        main.getusbService().write(Commands.ExcerciseMode(main.getisIsoKinetic()).getBytes());
        main.PlaySound(new int[]{R.raw.excercise_mode, R.raw.excercise_mode_english});
        this.workout = workout;
        if (isProgram)
            isProgram = true;
        else
            onSchedule = true;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exc_tab_btn_bench:
                setExcercise(bench, new BenchPress(main));
                break;
            case R.id.exc_tab_btn_squat:
                setExcercise(squat, new Squat(main));
                break;
            case R.id.exc_tab_btn_deadlift:
                setExcercise(deadlift, new DeadLift(main));
                break;
            case R.id.exc_tab_btn_shoulderpress:
                setExcercise(press, new ShoulderPress(main));
                break;
            case R.id.exc_tab_btn_latpulldown:
                setExcercise(latpull, new LatPullDown(main));
                break;
            case R.id.exc_tab_btn_carfraise:
                setExcercise(carf, new CarfRaise(main));
                break;
            case R.id.exc_tab_btn_armcurl:
                setExcercise(curl, new ArmCurl(main));
                break;
            case R.id.exc_tab_btn_armextension:
                setExcercise(extension, new ArmExtension(main));
                break;
            case R.id.exc_btn_start:
                break;
            case R.id.exc_btn_stop: {
                break;
            }
            case R.id.exc_btn_ready:
                ready.setPressed();
                if (ready.IsPressed()) {
                    setPropertiesFocusable(false);
                    txt_count.setText(edt_count.getSource().getText().toString());
                    dcButtonManager.setDCState(DCButtonManager.State.Ready);
                    main.getusbService().write(Commands.ExcerciseReady(main.getCurrentExcercise().getMode(),
                            main.getisIsoKinetic() ? String.valueOf(spinnerAdapter.getCurrentNumber()) : edt_weight.getSource().getText().toString(),
                            edt_count.getSource().getText().toString(),
                            edt_set.getSource().getText().toString()).getBytes());
                } else {
                    setPropertiesFocusable(true);

                    dcButtonManager.setDCState(DCButtonManager.State.Setted);
                    main.getusbService().write(Commands.ExcerciseStop(main.getCurrentExcercise().getMode(),
                            main.getisIsoKinetic() ? String.valueOf(spinnerAdapter.getCurrentNumber()) : edt_weight.getSource().getText().toString(),
                            edt_count.getSource().getText().toString(),
                            edt_set.getSource().getText().toString()).getBytes());
                }
                break;
        }
    }

    public void setPropertiesFocusable(boolean value) {
        edt_count.getSource().setEnabled(value);
        edt_set.getSource().setEnabled(value);
        edt_weight.getSource().setEnabled(value);
        edt_rest.getSource().setEnabled(value);
        spin_level.setEnabled(value);
    }


    public void setExcercise(DCButton button, Excercise excercise) {
        button.setPressed();
        if (button.IsPressed()) {
            main.setCurrentExcercise(excercise);
            dcButtonManager.setDCState(DCButtonManager.State.StartSetting);
            Main.getusbService().write(
                    Commands.MeasureSet(excercise.getMode(),
                            String.valueOf(300),
                            "000",
                            String.valueOf(30),
                            "2",
                            "0").getBytes());
            TakeBreak(false);
//            handler.postDelayed(new Runnable() {
//                public void run() {
//                    main.HandleACK(ACKListener.ACKParser.ParseACK("$PCA#"));
//                }
//            }, 3000);
        } else {
            dcButtonManager.setDCState(DCButtonManager.State.Clear);
            main.setCurrentExcercise(null);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_excercise_mode, container, false);
        prevstate = DCButtonManager.getDCState();
        setViews(view);

        if (savedState != null) {
            resumeView(savedState);
        }

        return view;
    }

    @Override
    public void HandleACK(ACK ack) {
        switch (ack.getCommandCode()) {
            case "ACD":
                Toast.makeText(main, "Command:" + ack.getCommandCode() + ack.getData() + ack.getmTension() + ack.getTime(), Toast.LENGTH_LONG).show();
                String count = String.valueOf(Integer.parseInt(ack.getData().substring(0, 2)));
                String set = String.valueOf(Integer.parseInt(ack.getData().substring(2, 4)));
                String restOn = ack.getData().substring(4, 5);
                txt_count.setText(count);
                txt_set.setText(set);
                switch (restOn) {
                    case "1":
                        DCButtonManager.setDCState(DCButtonManager.State.onRest);
                        TakeBreak(false);
                        break;
                }
                break;
            case "ACB":
                switch (ack.getData()) {
                    case "1":
                        exc_rest.setVisibility(View.INVISIBLE);
                        exc_table.setVisibility(View.VISIBLE);
                        DCButtonManager.setDCState(DCButtonManager.State.Excercise);

                }
                break;
            case "ASS":
                SendWorkoutRecord();
        }
    }

    public void TakeBreak(boolean isResume) {
        try {
            dcButtonManager.setDCState(DCButtonManager.State.onRest);
            exc_table.setVisibility(View.INVISIBLE);
            exc_rest.setVisibility(View.VISIBLE);

            if (!isResume) {
                count = Integer.parseInt(edt_rest.getSource().getText().toString());
                timer = new CountDownTimer(Integer.parseInt(edt_rest.getSource().getText().toString()) * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        rest_time.setText(String.valueOf(count));
                        if (count == 15)
                            main.PlaySound(new int[]{R.raw.next_set_will_start_soon, R.raw.next_set_will_start_soon_english});
                        else if (count < 10)
                            main.HandleACK(ACKListener.ACKParser.ParseACK("$ACS0" + count + "#"));
                        count--;
                    }

                    @Override
                    public void onFinish() {
                        rest_time.setText("0");
                        ResumeWorkout();
                    }
                };
            } else {
                timer = new CountDownTimer(count * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        rest_time.setText(String.valueOf(count));
                        if (count == 15)
                            main.PlaySound(new int[]{R.raw.next_set_will_start_soon, R.raw.next_set_will_start_soon_english});
                        else if (count < 10)
                            main.HandleACK(ACKListener.ACKParser.ParseACK("$ACS0" + count + "#"));
                        count--;
                        Log.i("Timer Used", String.valueOf(count));
                    }

                    @Override
                    public void onFinish() {
                        rest_time.setText("0");
                        ResumeWorkout();
                    }
                };
            }
            timer.start();
        } catch (Exception e) {
            Log.i("Dynamic", e.toString());
        }
    }

    public void ResumeWorkout() {
        exc_rest.setVisibility(View.INVISIBLE);
        exc_table.setVisibility(View.VISIBLE);
        DCButtonManager.setDCState(DCButtonManager.State.Excercise);
        main.getusbService().write(Commands.ExcerciseStart(main.getCurrentExcercise().getMode(),
                edt_weight.getSource().getText().toString(),
                edt_count.getSource().getText().toString(),
                edt_set.getSource().getText().toString()).getBytes());
    }

    public void resumeView(Bundle inState) {
        edt_count.getSource().setText(inState.getString("edt_count"));
        edt_weight.getSource().setText(inState.getString("edt_weight"));
        edt_set.getSource().setText(inState.getString("edt_set"));
        edt_rest.getSource().setText(inState.getString("edt_rest"));
        txt_count.setText(inState.getString("txt_count"));
        txt_set.setText(inState.getString("txt_set"));
        count = inState.getInt("count");
        if (main.getCurrentExcercise() != null)
            switch (main.getCurrentExcercise().getClass().getSimpleName()) {
                case "BenchPress":
                    if(!bench.IsPressed())
                    bench.setPressed();
                    break;
                case "Squat":
                    if(!squat.IsPressed())
                    squat.setPressed();
                    break;
                case "DeadLift":
                    if(!deadlift.IsPressed())
                    deadlift.setPressed();
                    break;
                case "ShoulderPress":
                    if(!press.IsPressed())
                    press.setPressed();
                    break;
                case "CarfRaise":
                    if(!carf.IsPressed())
                    carf.setPressed();
                    break;
                case "ArmCurl":
                    if(!curl.IsPressed())
                    curl.setPressed();
                    break;
                case "ArmExtension":
                    if(!extension.IsPressed())
                    extension.setPressed();
                    break;
                case "LatPullDown":
                    if(!latpull.IsPressed())
                    latpull.setPressed();
                    break;
            }

        dcButtonManager.setDCState(prevstate);
        if (count != 0) {
            TakeBreak(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("edt_count", edt_count.getSource().getText().toString());
        outState.putString("edt_weight", edt_weight.getSource().getText().toString());
        outState.putString("edt_set", edt_set.getSource().getText().toString());
        outState.putString("edt_rest", edt_rest.getSource().getText().toString());
        outState.putString("txt_count", txt_count.getText().toString());
        outState.putString("txt_set", txt_set.getText().toString());
        outState.putInt("count", count);
    }

    public Bundle getSaveState() {
        Bundle outState = new Bundle();
        outState.putString("edt_count", edt_count.getSource().getText().toString());
        outState.putString("edt_weight", edt_weight.getSource().getText().toString());
        outState.putString("edt_set", edt_set.getSource().getText().toString());
        outState.putString("edt_rest", edt_rest.getSource().getText().toString());
        outState.putString("txt_count", txt_count.getText().toString());
        outState.putString("txt_set", txt_set.getText().toString());
        outState.putInt("count", count);
        return outState;
    }

    public void setViews(View view) {
        try {
            isResume = true;
            if (main.getisIsoKinetic()) {
                view.findViewById(R.id.container_weight).setVisibility(View.GONE);
                view.findViewById(R.id.container_level).setVisibility(View.VISIBLE);
            } else {
                view.findViewById(R.id.container_level).setVisibility(View.GONE);
                view.findViewById(R.id.container_weight).setVisibility(View.VISIBLE);
            }


            bench = new DCButton(main);
            squat = new DCButton(main);
            deadlift = new DCButton(main);
            press = new DCButton(main);
            latpull = new DCButton(main);
            carf = new DCButton(main);
            curl = new DCButton(main);
            extension = new DCButton(main);
            start = new DCActionButton(main);
            stop = new DCActionButton(main);
            ready = new DCActionButton(main);
            edt_count = new DCEditText(view.findViewById(R.id.et_count));
            edt_weight = new DCEditText(view.findViewById(R.id.et_weight));
            edt_rest = new DCEditText(view.findViewById(R.id.et_rest));
            edt_set = new DCEditText(view.findViewById(R.id.et_set));

            bench.setButton(view.findViewById(R.id.exc_tab_btn_bench),
                    getResources().getDrawable(R.drawable.pressed_btn_benchpress),
                    getResources().getDrawable(R.drawable.body_pec));
            squat.setButton(view.findViewById(R.id.exc_tab_btn_squat),
                    getResources().getDrawable(R.drawable.pressed_btn_squat),
                    getResources().getDrawable(R.drawable.body_quad));
            deadlift.setButton(view.findViewById(R.id.exc_tab_btn_deadlift),
                    getResources().getDrawable(R.drawable.pressed_btn_deadlift),
                    getResources().getDrawable(R.drawable.body_spine));
            press.setButton(view.findViewById(R.id.exc_tab_btn_shoulderpress),
                    getResources().getDrawable(R.drawable.pressed_btn_shoulderpress),
                    getResources().getDrawable(R.drawable.body_deltoid));
            latpull.setButton(view.findViewById(R.id.exc_tab_btn_latpulldown),
                    getResources().getDrawable(R.drawable.pressed_btn_latpulldown),
                    getResources().getDrawable(R.drawable.body_lat));
            carf.setButton(view.findViewById(R.id.exc_tab_btn_carfraise),
                    getResources().getDrawable(R.drawable.pressed_btn_carfraise),
                    getResources().getDrawable(R.drawable.body_carf));
            curl.setButton(view.findViewById(R.id.exc_tab_btn_armcurl),
                    getResources().getDrawable(R.drawable.pressed_btn_amrcurl),
                    getResources().getDrawable(R.drawable.body_biceps));
            extension.setButton(view.findViewById(R.id.exc_tab_btn_armextension),
                    getResources().getDrawable(R.drawable.pressed_btn_armextension),
                    getResources().getDrawable(R.drawable.body_triceps));
            start.setButton(view.findViewById(R.id.exc_btn_start),
                    getResources().getDrawable(R.drawable.btn_pause_pressed));
            stop.setButton(view.findViewById(R.id.exc_btn_stop),
                    getResources().getDrawable(R.drawable.pressed_btn_stop));
            ready.setButton(view.findViewById(R.id.exc_btn_ready),
                    getResources().getDrawable(R.drawable.pressed_btn_ready));
            txt_count = view.findViewById(R.id.txt_realcount);
            txt_set = view.findViewById(R.id.txt_realset);
            rest_time = view.findViewById(R.id.rest_time);
            spin_level = view.findViewById(R.id.spin_level);
            Body = view.findViewById(R.id.exc_body);
            exc_table = view.findViewById(R.id.exc_table);
            exc_rest = view.findViewById(R.id.exc_rest);


            DCButton.setBody(Body);

            List<String> data = new ArrayList<String>();
            data.add("1(2cm/sec)");
            data.add("2(3cm/sec)");
            data.add("3(4.5cm/sec)");
            data.add("4(6cm/sec)");
            data.add("5(8.5cm/sec)");

//            List<Integer> number = new ArrayList<Integer>();
//            data.add(1);
//            data.add("2");
//            data.add("3");
//            data.add("4");
//            data.add("5");


            spinnerAdapter spinnerAdapter = new spinnerAdapter(main,data);
            spin_level.setAdapter(spinnerAdapter);

            bench.getButton().setOnClickListener(this);
            squat.getButton().setOnClickListener(this);
            deadlift.getButton().setOnClickListener(this);
            press.getButton().setOnClickListener(this);
            latpull.getButton().setOnClickListener(this);
            carf.getButton().setOnClickListener(this);
            curl.getButton().setOnClickListener(this);
            extension.getButton().setOnClickListener(this);
            start.getButton().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            start.setPressedwithNoSound();
                            break;
                        case MotionEvent.ACTION_UP:
                            start.setPressed();
                            start.setPause();
                            if (start.isPause()) {
                                dcButtonManager.setDCState(DCButtonManager.State.Excercise);
                                main.getusbService().write(Commands.ExcerciseStart(main.getCurrentExcercise().getMode(),
                                        main.getisIsoKinetic() ? String.valueOf(spinnerAdapter.getCurrentNumber()) : edt_weight.getSource().getText().toString(),
                                        edt_count.getSource().getText().toString(),
                                        edt_set.getSource().getText().toString()).getBytes());
                                main.PlaySound(new int[]{R.raw.start_excercise, R.raw.start_excercise_english});
                                start.getButton().setImageDrawable(getResources().getDrawable(R.drawable.btn_pause));
                                start.setButton(start.getButton(), getResources().getDrawable(R.drawable.btn_pause_pressed));
                            } else {
                                dcButtonManager.setDCState(DCButtonManager.State.Paused);
                                main.getusbService().write(Commands.ExcercisePause(main.getCurrentExcercise().getMode(),
                                        main.getisIsoKinetic() ? String.valueOf(spinnerAdapter.getCurrentNumber()) : edt_weight.getSource().getText().toString(),
                                        edt_count.getSource().getText().toString(),
                                        edt_set.getSource().getText().toString()).getBytes());
                                start.getButton().setImageDrawable(getResources().getDrawable(R.drawable.btn_start));
                                start.setButton(start.getButton(), getResources().getDrawable(R.drawable.pressed_btn_start));
                            }
                            break;
                    }
                    return false;
                }
            });
            stop.getButton().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        setPropertiesFocusable(true);
                        exc_rest.setVisibility(View.INVISIBLE);
                        exc_table.setVisibility(View.VISIBLE);
                        if (timer != null)
                            timer.cancel();

                        stop.setPressed();
                        dcButtonManager.setDCState(DCButtonManager.State.Stop);
                        dcButtonManager.setDCState(DCButtonManager.State.Setted);

//                        main.PlaySound(new int[]{R.raw.excercise_is_going_to_stop, R.raw.thank_you_for_your_efforts, R.raw.excercise_is_going_to_stop_english, R.raw.thank_you_for_your_efforts_english});
                        main.getusbService().write(Commands.ExcerciseStop(main.getCurrentExcercise().getMode(),
                                main.getisIsoKinetic() ? String.valueOf(String.valueOf(spinnerAdapter.getCurrentNumber())) : edt_weight.getSource().getText().toString(),
                                edt_count.getSource().getText().toString(),
                                edt_set.getSource().getText().toString()).getBytes());
                        txt_count.setText("0");
                        txt_set.setText("0");
                        SendWorkoutRecord();
                    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        stop.setPressedwithNoSound();
                    }
                    return false;
                }
            });
            ready.getButton().setOnClickListener(this);
            txt_count.setOnClickListener(this);
            txt_set.setOnClickListener(this);


            dcButtonManager = new DCButtonManager(bench, squat, deadlift, press, curl, extension, latpull, carf, start, ready, stop);


            if (workout != null) {
                edt_count.getSource().setText(String.valueOf(workout.getReps()));
                edt_weight.getSource().setText(String.valueOf(workout.getWeight()));
                edt_set.getSource().setText(String.valueOf(workout.getSet()));
                switch (workout.getExcercise().getSimpleName()) {
                    case "벤치 프레스":
                        setExcercise(bench, new BenchPress(main));
                        break;
                    case "스쿼트":
                        setExcercise(squat, new Squat(main));
                        break;
                    case "데드 리프트":
                        setExcercise(deadlift, new DeadLift(main));
                        break;
                    case "숄더 프레스":
                        setExcercise(press, new ShoulderPress(main));
                        break;
                    case "랫 풀 다운":
                        setExcercise(latpull, new LatPullDown(main));
                        break;
                    case "카프 레이즈":
                        setExcercise(carf, new CarfRaise(main));
                        break;
                    case "암 컬":
                        setExcercise(curl, new ArmCurl(main));
                        break;
                    case "암 익스텐션":
                        setExcercise(extension, new ArmExtension(main));
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        savedState = getSaveState();
        if (timer != null)
            timer.cancel();

        main.getusbService().write(Commands.Home(true).getBytes());
        Log.i("Command", "CHM08");

    }

    public void SendWorkoutRecord() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("commonCode", main.getCurrentExcercise().getDBCode() + ((main.getisIsoKinetic()) ? "02" : "01"));
            jsonObject.accumulate("count", Integer.valueOf(edt_count.getSource().getText().toString()));
            jsonObject.accumulate("device", main.getCare().getDeviceID().toString());
            jsonObject.accumulate("email", "");
            jsonObject.accumulate("height", 0);
            jsonObject.accumulate("isProgram", isProgram);
            jsonObject.accumulate("level", 0);
            jsonObject.accumulate("onSchedule", onSchedule);
            jsonObject.accumulate("rest", Integer.valueOf(edt_rest.getSource().getText().toString()));
            jsonObject.accumulate("set", Integer.valueOf(edt_set.getSource().getText().toString()));
            jsonObject.accumulate("weight", Integer.valueOf(edt_weight.getSource().getText().toString()));
            new DCHttp().SendWorkout(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTitle() {
        if (main.getisIsoKinetic())
            return "등속성 운동 모드";
        else
            return "등장성 운동 모드";
    }

    @Override
    public int isHomeVisible() {
        return View.VISIBLE;
    }

    @Override
    public DCfragment getBackFragment() {
        return new SelectMode(main);
    }

    @Override
    public DCfragment getNextFragment() {
        return null;
    }
}


class spinnerAdapter extends BaseAdapter {
    Context context;
    List<String> data;
    List<Integer> number;
    LayoutInflater inflater;
    static TextView spinnerText;


    public spinnerAdapter(Context context, List<Integer> number, List<String> data) {
        this.context = context;
        this.data = data;
        this.number = number;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public spinnerAdapter(Context context,List<String> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static char getCurrentNumber() {
        return spinnerText.getText().toString().charAt(0);
    }


    @Override
    public int getCount() {
        if (data != null) return data.size();
        else return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_normal, parent, false);
        }

        spinnerText = (TextView) convertView.findViewById(R.id.spinnerText);
        if (data != null) {
            //데이터세팅
            String text = data.get(position);
            ((TextView) convertView.findViewById(R.id.spinnerText)).setText(text);
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_drop_down, parent, false);
        }

        spinnerText = convertView.findViewById(R.id.spinnerText);
        //데이터세팅
        String text = data.get(position);
        spinnerText.setText(text);


        return convertView;
    }

    @Override
    public String getItem(int position) {
        Log.i("getItem",String.valueOf(position+1));
        return String.valueOf(position+1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}