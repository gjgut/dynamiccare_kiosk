package com.example.dynamiccare_kisok.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import com.example.dynamiccare_kisok.Common.Util.Commands;
import com.example.dynamiccare_kisok.Common.Component.DCActionButton;
import com.example.dynamiccare_kisok.Common.Component.DCButton;
import com.example.dynamiccare_kisok.Common.Component.DCButtonManager;
import com.example.dynamiccare_kisok.Common.Component.DCEditText;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.Util.DCSoundPlayer;
import com.example.dynamiccare_kisok.Common.Util.DCSoundThread;
import com.example.dynamiccare_kisok.R;
import com.example.dynamiccare_kisok.Test.Runnable.ExcerciseReady1;
import com.example.dynamiccare_kisok.Test.Runnable.ExcerciseStart;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ExcerciseMode extends DCfragment {

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
    CountDownTimer countDownTimer;
    int count;
    Handler handler = new Handler();

    public ExcerciseMode(Main main) {
        super(main);
        main.getusbService().write(Commands.ExcerciseMode(main.getisIsoKinetic()).getBytes());
        main.PlaySound(new int[]{R.raw.excercise_mode, R.raw.excercise_mode_english});
    }

    public ExcerciseMode(Main main, Workout workout)
    {
        super(main);
        main.getusbService().write(Commands.ExcerciseMode(main.getisIsoKinetic()).getBytes());
        main.PlaySound(new int[]{R.raw.excercise_mode, R.raw.excercise_mode_english});
        this.workout = workout;

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
                setExcercise(deadlift, new BenchPress(main));
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
//                start.setPressed();
//                dcButtonManager.setDCState(DCButtonManager.State.StartSetting);
//                main.getusbService().write(Commands.ExcerciseStart(main.getCurrentExcercise().getMode(),
//                        edt_weight.getSource().getText().toString(),
//                        txt_count.getText().toString(),
//                        txt_set.getText().toString()).getBytes());
                break;
            case R.id.exc_btn_stop: {
                break;
            }
            case R.id.exc_btn_ready: {
                txt_count.setText(edt_count.getSource().getText().toString());
                ready.setPressed();
                txt_set.setText(edt_set.getSource().getText().toString());
                if (ready.getButton().isPressed()) {
                    setPropertiesFocusable(false);

                    dcButtonManager.setDCState(DCButtonManager.State.Ready);
                    main.getusbService().write(Commands.ExcerciseReady(main.getCurrentExcercise().getMode(),
                            edt_weight.getSource().getText().toString(),
                            txt_count.getText().toString(),
                            txt_set.getText().toString()).getBytes());
                } else {
                    setPropertiesFocusable(true);

                    dcButtonManager.setDCState(DCButtonManager.State.Clear);
                    main.getusbService().write(Commands.ExcerciseStop(main.getCurrentExcercise().getMode(),
                            edt_weight.getSource().getText().toString(),
                            txt_count.getText().toString(),
                            txt_set.getText().toString()).getBytes());
                }
                break;
            }


        }
    }

    public void setPropertiesFocusable(boolean value)
    {
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
                            String.valueOf(main.getMeasureWeight()),
                            "000", String.valueOf(main.getMeasureTime()),
                            "1",
                            "0").getBytes());
            handler.postDelayed(new Runnable() {
                public void run() {
                    main.HandleACK(ACKListener.ACKParser.ParseACK("$PCA#"));
                }
            }, 3000);
        } else {
            dcButtonManager.setDCState(DCButtonManager.State.Clear);
            main.setCurrentExcercise(null);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_excercise_mode, container, false);
        setViews(view);


        return view;
    }

    @Override
    public void HandleACK(ACK ack) {
        switch (ack.getCommandCode()) {
            case "ACD":
                String count = String.valueOf(Integer.parseInt(ack.getData().substring(0, 2)));
                String set = String.valueOf(Integer.parseInt(ack.getData().substring(2, 4)));
                String restOn = ack.getData().substring(4, 5);
                txt_count.setText(count);
                txt_set.setText(set);
                switch (restOn) {
                    case "1":
                        DCButtonManager.setDCState(DCButtonManager.State.onRest);
                        TakeBreak(set);
                        break;
                }
                break;
            case "ACB":
                switch (ack.getData())
                {
                    case "1":
                        DCButtonManager.setDCState(DCButtonManager.State.Excercise);

                }
        }
    }

    public void TakeBreak(String set) {
        try {
            exc_table.setVisibility(View.INVISIBLE);
            exc_rest.setVisibility(View.VISIBLE);
            switch (set) {
                case "1":
                    main.PlaySound(new int[]{R.raw.one_set_complete, R.raw.take_a_break, R.raw.one_set_complete_english, R.raw.take_a_break_english});
                    break;
                case "2":
                    main.PlaySound(new int[]{R.raw.two_set_complete, R.raw.take_a_break, R.raw.two_set_complete_english, R.raw.take_a_break_english});
                    break;
                case "3":
                    main.PlaySound(new int[]{R.raw.three_set_complete, R.raw.take_a_break, R.raw.three_set_complete_english, R.raw.take_a_break_english});
                    break;
                case "4":
                    main.PlaySound(new int[]{R.raw.take_a_break, R.raw.four_sets_completed_english, R.raw.take_a_break_english});
                    break;
                case "5":
                    main.PlaySound(new int[]{R.raw.take_a_break, R.raw.five_sets_completed_english, R.raw.take_a_break_english});
                    break;
                case "6":
                    main.PlaySound(new int[]{R.raw.take_a_break, R.raw.six_sets_completed_english, R.raw.take_a_break_english});
                    break;
                case "7":
                    main.PlaySound(new int[]{R.raw.take_a_break, R.raw.seven_sets_completed_english, R.raw.take_a_break_english});
                    break;
                case "8":
                    main.PlaySound(new int[]{R.raw.take_a_break, R.raw.eight_sets_completed_english, R.raw.take_a_break_english});
                    break;
                case "9":
                    main.PlaySound(new int[]{R.raw.take_a_break, R.raw.nine_sets_completed_english, R.raw.take_a_break_english});
                    break;
                case "10":
                    main.PlaySound(new int[]{R.raw.take_a_break, R.raw.ten_sets_completed_english, R.raw.take_a_break_english});
                    break;
            }
            count = Integer.parseInt(edt_rest.getSource().getText().toString());

            countDownTimer = new CountDownTimer(Integer.parseInt(edt_rest.getSource().getText().toString()) * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    rest_time.setText(String.valueOf(count));
                    if (count == 15)
                        main.PlaySound(new int[]{R.raw.next_set_will_start_soon, R.raw.next_set_will_start_soon_english});
                    count--;
                }

                @Override
                public void onFinish() {
                    rest_time.setText("0");
                    ResumeWorkout();
                }
            };
            countDownTimer.start();
        } catch (Exception e) {
            Log.i("Dynamic", e.toString());
        }
    }

    public void ResumeWorkout() {
        exc_rest.setVisibility(View.INVISIBLE);
        exc_table.setVisibility(View.VISIBLE);
        main.PlaySound(new int[]{R.raw.start_excercise, R.raw.start_excercise_english});
        DCButtonManager.setDCState(DCButtonManager.State.Excercise);
        main.getusbService().write(Commands.ExcerciseStart(main.getCurrentExcercise().getMode(),
                edt_weight.getSource().getText().toString(),
                txt_count.getText().toString(),
                txt_set.getText().toString()).getBytes());
    }


    public void setViews(View view) {
        try {
            if (Main.getisIsoKinetic()) {
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
            data.add("1");
            data.add("2");
            data.add("3");
            data.add("4");
            data.add("5");

            spinnerAdapter spinnerAdapter = new spinnerAdapter(main, data);
            spin_level.setAdapter(spinnerAdapter);

            bench.getButton().setOnClickListener(this);
            squat.getButton().setOnClickListener(this);
            deadlift.getButton().setOnClickListener(this);
            press.getButton().setOnClickListener(this);
            latpull.getButton().setOnClickListener(this);
            carf.getButton().setOnClickListener(this);
            curl.getButton().setOnClickListener(this);
            extension.getButton().setOnClickListener(this);
//            start.getButton().setOnClickListener(this);
            start.getButton().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction())
                    {
                        case MotionEvent.ACTION_DOWN:
                            start.setPressedwithNoSound();
                            break;
                        case MotionEvent.ACTION_UP:
                            start.setPressed();
                            start.setPause();
                            if(start.isPause())
                            {
                                dcButtonManager.setDCState(DCButtonManager.State.Excercise);
                                main.getusbService().write(Commands.ExcerciseStart(main.getCurrentExcercise().getMode(),
                                        edt_weight.getSource().getText().toString(),
                                        txt_count.getText().toString(),
                                        txt_set.getText().toString()).getBytes());
                                main.PlaySound(new int[] {R.raw.start_excercise,R.raw.start_excercise_english});
                                start.getButton().setImageDrawable(getResources().getDrawable(R.drawable.btn_pause));
                                start.setButton(start.getButton(), getResources().getDrawable(R.drawable.btn_pause_pressed));
                            }
                            else {
                                dcButtonManager.setDCState(DCButtonManager.State.Paused);
                                main.getusbService().write(Commands.ExcercisePause(main.getCurrentExcercise().getMode(),
                                        edt_weight.getSource().getText().toString(),
                                        txt_count.getText().toString(),
                                        txt_set.getText().toString()).getBytes());
                                start.getButton().setImageDrawable(getResources().getDrawable(R.drawable.btn_start));
                                start.setButton(start.getButton(), getResources().getDrawable(R.drawable.pressed_btn_start));
                            }
                            break;
                    }
                    return false;
                }
            });
//            stop.getButton().setOnClickListener(this);
            stop.getButton().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        setPropertiesFocusable(true);
                        exc_rest.setVisibility(View.INVISIBLE);
                        exc_table.setVisibility(View.VISIBLE);
                        if(countDownTimer!=null)
                            countDownTimer.cancel();

                        stop.setPressed();
                        dcButtonManager.setDCState(DCButtonManager.State.Stop);
                        dcButtonManager.setDCState(DCButtonManager.State.Setted);

                        main.PlaySound(new int[]{R.raw.excercise_is_going_to_stop, R.raw.thank_you_for_your_efforts, R.raw.excercise_is_going_to_stop_english, R.raw.thank_you_for_your_efforts_english});
                        main.getusbService().write(Commands.ExcerciseStop(main.getCurrentExcercise().getMode(),
                                edt_weight.getSource().getText().toString(),
                                txt_count.getText().toString(),
                                txt_set.getText().toString()).getBytes());
                        txt_count.setText("0");
                        txt_set.setText("0");
                    }
                    else if(event.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        stop.setPressed();
                    }
                    return false;
                }
            });
            ready.getButton().setOnClickListener(this);
            txt_count.setOnClickListener(this);
            txt_set.setOnClickListener(this);

            dcButtonManager = new DCButtonManager(bench, squat, deadlift, press, curl, extension, latpull, carf, start, ready, stop);


            if(workout != null)
            {
                edt_count.getSource().setText(String.valueOf(workout.getReps()));
                edt_weight.getSource().setText(String.valueOf(workout.getWeight()));
                edt_set.getSource().setText(String.valueOf(workout.getSet()));
                switch (workout.getExcercise().getSimpleName())
                {
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
        if (countDownTimer != null)
            countDownTimer.cancel();
    }

    @Override
    public String getTitle() {
        if (Main.getisIsoKinetic())
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
    LayoutInflater inflater;


    public spinnerAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        //데이터세팅
        String text = data.get(position);
        ((TextView) convertView.findViewById(R.id.spinnerText)).setText(text);


        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}