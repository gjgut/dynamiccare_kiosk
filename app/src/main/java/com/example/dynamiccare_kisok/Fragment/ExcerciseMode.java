package com.example.dynamiccare_kisok.Fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCSpinnerAdapter;
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
import com.example.dynamiccare_kisok.Common.Util.Commands;
import com.example.dynamiccare_kisok.Common.Component.DCActionButton;
import com.example.dynamiccare_kisok.Common.Component.DCButton;
import com.example.dynamiccare_kisok.Common.Component.DCButtonManager;
import com.example.dynamiccare_kisok.Common.Component.DCEditText;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.Util.DCHttp;
import com.example.dynamiccare_kisok.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExcerciseMode extends DCfragment implements View.OnTouchListener {

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
    DCSpinnerAdapter spinnerAdapter;
    boolean isProgram = false, onSchedule = false;
    int count;
    Handler handler = new Handler();
    boolean isResume = false;

    public ExcerciseMode() {
    }

    public ExcerciseMode(Main main) {
        super(main);
        main.getusbService().write(Commands.ExcerciseMode(main.getisIsoKinetic()));
        main.PlaySound(new int[]{R.raw.excercise_mode, R.raw.excercise_mode_english});
    }

    public ExcerciseMode(Main main, Bundle bundle) {
        super(main);
        savedState = bundle;
        isResume = true;
    }

    public ExcerciseMode(Main main, Workout workout, boolean isProgram) {
        super(main);
        try {
            main.getusbService().write(Commands.ExcerciseMode(main.getisIsoKinetic()));
            main.PlaySound(new int[]{R.raw.excercise_mode, R.raw.excercise_mode_english});
            this.workout = workout;
            if (isProgram)
                isProgram = onSchedule = true;
            else
                onSchedule = true;
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    @Override
    public void onClick(View v) {
        try {
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
                case R.id.exc_btn_ready:
                    ready.setPressed();
                    if (ready.IsPressed()) {
                        setPropertiesFocusable(false);
                        txt_count.setText(edt_count.getSource().getText().toString());
                        dcButtonManager.setDCState(DCButtonManager.State.Ready);
                        main.getusbService().write(Commands.ExcerciseReady(main.getCurrentExcercise().getMode(),
                                main.getisIsoKinetic() ? String.valueOf(spinnerAdapter.getCurrentNumber()) : edt_weight.getSource().getText().toString(),
                                edt_count.getSource().getText().toString(),
                                edt_set.getSource().getText().toString()));
                    } else {
                        setPropertiesFocusable(true);

                        dcButtonManager.setDCState(DCButtonManager.State.Setted);
                        main.getusbService().write(Commands.ExcerciseStop(main.getCurrentExcercise().getMode(),
                                main.getisIsoKinetic() ? String.valueOf(spinnerAdapter.getCurrentNumber()) : edt_weight.getSource().getText().toString(),
                                edt_count.getSource().getText().toString(),
                                edt_set.getSource().getText().toString()));
                    }
                    break;
            }
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    public void setPropertiesFocusable(boolean value) {
        try {
            edt_count.getSource().setEnabled(value);
            edt_set.getSource().setEnabled(value);
            edt_weight.getSource().setEnabled(value);
            edt_rest.getSource().setEnabled(value);
            spin_level.setEnabled(value);
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }


    public void setExcercise(DCButton button, Excercise excercise) {
        try {
            if (isResume)
                button.setPressedWithNoSound();
            else
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
                                "0"));
                if (!isResume)
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
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_excercise_mode, container, false);
        try {
            prevstate = DCButtonManager.getDCState();
            setViews(view);

            if (savedState != null) {
                resumeView(savedState);
            }
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

        return view;
    }

    @Override
    public void HandleACK(ACK ack) {
        try {
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
                    setPropertiesFocusable(true);
                    SendWorkoutRecord();
                    break;
            }
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    public void TakeBreak(boolean isResume) {
        try {
            setPropertiesFocusable(false);
            dcButtonManager.setDCState(DCButtonManager.State.onRest);
            exc_table.setVisibility(View.INVISIBLE);
            exc_rest.setVisibility(View.VISIBLE);

            if (!isResume) {
                if (timer != null)
                    timer.cancel();
                count = Integer.parseInt(edt_rest.getSource().getText().toString());
                timer = new CountDownTimer(Integer.parseInt(edt_rest.getSource().getText().toString()) * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        rest_time.setText(String.valueOf(count));
                        if (count == 15)
                            main.PlaySound(new int[]{R.raw.next_set_will_start_soon, R.raw.next_set_will_start_soon_english});
                        else if (count < 10)
                            main.HandleACK(ACKListener.ParseACK("$ACS0" + count + "#"));
                        count--;
                    }

                    @Override
                    public void onFinish() {
                        rest_time.setText("0");
                        ResumeWorkout();
                    }
                };
            } else {
                if (timer != null)
                    timer.cancel();
                timer = new CountDownTimer(count * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        rest_time.setText(String.valueOf(count));
                        if (count == 15)
                            main.PlaySound(new int[]{R.raw.next_set_will_start_soon, R.raw.next_set_will_start_soon_english});
                        else if (count < 10)
                            main.HandleACK(ACKListener.ParseACK("$ACS0" + count + "#"));
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
        try {
            exc_rest.setVisibility(View.INVISIBLE);
            exc_table.setVisibility(View.VISIBLE);
            DCButtonManager.setDCState(DCButtonManager.State.Excercise);
            main.getusbService().write(Commands.ExcerciseStart(main.getCurrentExcercise().getMode(),
                    edt_weight.getSource().getText().toString(),
                    edt_count.getSource().getText().toString(),
                    edt_set.getSource().getText().toString()));
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    public void resumeView(Bundle inState) {
        try {
            isResume = true;
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
                        setExcercise(bench, new BenchPress(main));
                        break;
                    case "Squat":
                        setExcercise(squat, new Squat(main));
                        break;
                    case "DeadLift":
                        setExcercise(deadlift, new DeadLift(main));
                        break;
                    case "ShoulderPress":
                        setExcercise(press, new ShoulderPress(main));
                        break;
                    case "LatPullDown":
                        setExcercise(latpull, new LatPullDown(main));
                        break;
                    case "CarfRaise":
                        setExcercise(carf, new CarfRaise(main));
                        break;
                    case "ArmCurl":
                        setExcercise(curl, new ArmCurl(main));
                        break;
                    case "ArmExtension":
                        setExcercise(extension, new ArmExtension(main));
                        break;
                }

            dcButtonManager.setDCState(prevstate);
            if (count > 0) {
                TakeBreak(true);
            }
            isResume = false;
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        try {
            super.onSaveInstanceState(outState);
            outState.putString("edt_count", edt_count.getSource().getText().toString());
            outState.putString("edt_weight", edt_weight.getSource().getText().toString());
            outState.putString("edt_set", edt_set.getSource().getText().toString());
            outState.putString("edt_rest", edt_rest.getSource().getText().toString());
            outState.putString("txt_count", txt_count.getText().toString());
            outState.putString("txt_set", txt_set.getText().toString());
            outState.putInt("count", count);
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    public Bundle getSaveState() {
        try {
            Bundle outState = new Bundle();
            outState.putString("edt_count", edt_count.getSource().getText().toString());
            outState.putString("edt_weight", edt_weight.getSource().getText().toString());
            outState.putString("edt_set", edt_set.getSource().getText().toString());
            outState.putString("edt_rest", edt_rest.getSource().getText().toString());
            outState.putString("txt_count", txt_count.getText().toString());
            outState.putString("txt_set", txt_set.getText().toString());
            outState.putInt("count", count);
            return outState;
        } catch (Exception e) {
            Log.i("Error", e.toString());
            return null;
        }

    }

    public void setViews(View view) {
        try {
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

            spinnerAdapter = new DCSpinnerAdapter(main, data);
            spin_level.setAdapter(spinnerAdapter);

            dcButtonManager = new DCButtonManager(bench, squat, deadlift, press, curl, extension, latpull, carf, start, ready, stop);

            setListener();
            Loadworkout();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Loadworkout() {
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
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.exc_btn_ready) {
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
                                edt_set.getSource().getText().toString()));
                        main.PlaySound(new int[]{R.raw.start_excercise, R.raw.start_excercise_english});
                        start.getButton().setImageDrawable(getResources().getDrawable(R.drawable.btn_pause));
                        start.setButton(start.getButton(), getResources().getDrawable(R.drawable.btn_pause_pressed));
                    } else {
                        dcButtonManager.setDCState(DCButtonManager.State.Paused);
                        main.getusbService().write(Commands.ExcercisePause(main.getCurrentExcercise().getMode(),
                                main.getisIsoKinetic() ? String.valueOf(spinnerAdapter.getCurrentNumber()) : edt_weight.getSource().getText().toString(),
                                edt_count.getSource().getText().toString(),
                                edt_set.getSource().getText().toString()));
                        start.getButton().setImageDrawable(getResources().getDrawable(R.drawable.btn_start));
                        start.setButton(start.getButton(), getResources().getDrawable(R.drawable.pressed_btn_start));
                    }
                    break;
            }
        } else if (v.getId() == R.id.exc_btn_stop) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                count = 0;
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
                        edt_set.getSource().getText().toString()));
                txt_count.setText("0");
                txt_set.setText("0");
//                        SendWorkoutRecord();
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                stop.setPressedwithNoSound();
            }
        }
        return false;
    }


    private void setListener() {
        bench.getButton().setOnClickListener(this);
        squat.getButton().setOnClickListener(this);
        deadlift.getButton().setOnClickListener(this);
        press.getButton().setOnClickListener(this);
        latpull.getButton().setOnClickListener(this);
        carf.getButton().setOnClickListener(this);
        curl.getButton().setOnClickListener(this);
        extension.getButton().setOnClickListener(this);

        start.getButton().setOnTouchListener(this);
        stop.getButton().setOnTouchListener(this);
        ready.getButton().setOnClickListener(this);
        txt_count.setOnClickListener(this);
        txt_set.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        try {
            super.onDestroy();
            savedState = getSaveState();
            if (timer != null)
                timer.cancel();

            main.getusbService().write(Commands.Home(true));
            Log.i("Command", "CHM08");
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    public void SendWorkoutRecord() {
        try {
            Workout sendworkdout = new Workout(true,
                    main.getisIsoKinetic(),
                    main.getCurrentExcercise(),
                    Integer.valueOf(edt_weight.getSource().getText().toString()),
                    Integer.valueOf(edt_count.getSource().getText().toString()),
                    Integer.valueOf(edt_set.getSource().getText().toString()));
            if (!workout.equals(sendworkdout))
                isProgram = onSchedule = false;

            JSONObject jsonObject = new JSONObject();
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
            Log.i("Error", e.toString());
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
        if (isProgram || onSchedule)
            return new SelectWorkOut(main, null);
        else
            return new SelectMode(main);
    }

}

