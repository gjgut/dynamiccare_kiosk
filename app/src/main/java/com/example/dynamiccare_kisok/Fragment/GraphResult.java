package com.example.dynamiccare_kisok.Fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCActionButton;
import com.example.dynamiccare_kisok.Common.Component.DCButton;
import com.example.dynamiccare_kisok.Common.Component.DCButtonManager;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.Util.ACK;
import com.example.dynamiccare_kisok.Common.Util.Commands;
import com.example.dynamiccare_kisok.R;

import java.util.ArrayList;

public class GraphResult extends DCfragment implements View.OnTouchListener {

    DCButton Low, Mid, High;
    ImageButton Up, Down;
    DCActionButton ready, go;
    ProgressBar power;
    ResCalculator resCalculator;
    CountDownTimer timer;

    Handler handler = new Handler(); // Thread 에서 화면에 그리기 위해서 필요

    final int MSG_PROGRESS = 10;

    public GraphResult(Main main) {
        super(main);
        main.PlaySound(
                new int[]{R.raw.power_log,
                        R.raw.setting_is_completed,
                        R.raw.follow_instruction,
                        R.raw.take_pose_and_place_bar_or_wire_to_right_position,
                        R.raw.effort_maximally_during_measurement,
                        R.raw.dont_stop_measurement_by_stop_sound,
                        R.raw.measurement_begin_soon,
                        R.raw.please_follow_the_directions_english,
                        R.raw.adjust_the_bar_or_wire_properly_english,
                        R.raw.please_do_your_best_in_measuring_english,
                        R.raw.do_not_stop_measuring_until_the_end_comment_is_made_english,
                        R.raw.the_measurement_wil_begin_shortly_english});
    }


    public void setBottomBar(boolean isShow) {
        if (!isShow)
            Main.getBottombar().findViewById(R.id.btn_next).setVisibility(View.INVISIBLE);
        else
            Main.getBottombar().findViewById(R.id.btn_next).setVisibility(View.VISIBLE);
    }

    public void setViews(View v) {

        Up = (ImageButton) v.findViewById(R.id.btn_up);
        Down = (ImageButton) v.findViewById(R.id.btn_down);

        Low = new DCButton(main, (ImageButton) v.findViewById(R.id.btn_low), getResources().getDrawable(R.drawable.pressed_btn_low));
        Mid = new DCButton(main, (ImageButton) v.findViewById(R.id.btn_mid), getResources().getDrawable(R.drawable.pressed_btn_mid));
        High = new DCButton(main, (ImageButton) v.findViewById(R.id.btn_high), getResources().getDrawable(R.drawable.pressed_btn_high));

        ready = new DCActionButton(main, (ImageButton) v.findViewById(R.id.btn_ready), getResources().getDrawable(R.drawable.pressed_btn_ready));
        go = new DCActionButton(main, (ImageButton) v.findViewById(R.id.btn_start), getResources().getDrawable(R.drawable.pressed_btn_start));

        power = (ProgressBar) v.findViewById(R.id.progressBar_power);


        Low.getButton().setOnClickListener(this);
        Mid.getButton().setOnClickListener(this);
        High.getButton().setOnClickListener(this);

        Up.setOnTouchListener(this);
        Down.setOnTouchListener(this);

        ready.getButton().setOnClickListener(this);
//        go.getButton().setOnClickListener(this);
        go.getButton().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        go.setPressedwithNoSound();
                        break;
                    case MotionEvent.ACTION_UP:
                        go.setPressed();
                        go.setPause();
                        if(!go.isPause())
                        {
                            main.PlaySound(new int[]{R.raw.bee_measurement_begin});
                            if (resCalculator != null)
                                main.getusbService().write(Commands.MeasureStart(main.getMeasureWeight(), main.getMeasureTime()).getBytes());
                            resCalculator = new ResCalculator();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    timer = new CountDownTimer(Integer.parseInt(main.getMeasureTime()) * 1000, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                        }

                                        @Override
                                        public void onFinish() {
                                            ready.setPressed();
                                            go.setPressed();
                                            go.setPause();
                                            go.getButton().setImageDrawable(getResources().getDrawable(R.drawable.btn_start));
                                            go.setButton(go.getButton(), getResources().getDrawable(R.drawable.pressed_btn_start));
                                            main.PlaySound(new int[]{R.raw.measurement_complete_sound,
                                                    R.raw.stopping_measurement,
                                                    R.raw.thank_you_for_your_efforts,
                                                    R.raw.show_your_result,
                                                    R.raw.the_measurement_is_going_to_stop_english,
                                                    R.raw.thank_you_for_your_efforts_english,
                                                    R.raw.please_check_the_results_english,
                                                    R.raw.dynamic_care});
                                            setBottomBar(true);
                                        }
                                    };
                                    timer.start();
                                }
                            });
                            go.getButton().setImageDrawable(getResources().getDrawable(R.drawable.btn_stop));
                            go.setButton(go.getButton(), getResources().getDrawable(R.drawable.pressed_btn_stop));
                        }
                        else {
                            if(timer !=null)
                                timer.cancel();
                            main.getusbService().write("$CSP0#".getBytes());

                            go.getButton().setImageDrawable(getResources().getDrawable(R.drawable.btn_start));
                            go.setButton(go.getButton(), getResources().getDrawable(R.drawable.pressed_btn_start));
                        }
                        break;
                }
                return false;
            }
        });



        go.getButton().setClickable(false);
        go.getButton().setColorFilter(Color.parseColor("#28FFFFFF"),
                PorterDuff.Mode.SRC_ATOP);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() { // Thread 로 작업할 내용을 구현
                int value = 0;
                int add = 1;
                while (true) {
                    value = value + add;
                    if (value >= 100) {
                        break;
                    }
                    final int value2 = value;
                    handler.post(new Runnable() {
                        @Override
                        public void run() { // 화면에 변경하는 작업을 구현
                            power.setProgress(value2);
                        }
                    });

                    try {
                        Thread.sleep(50); // 시간지연
                    } catch (InterruptedException e) {
                    }
                } // end of while
            }
        });
//        t.start(); // 쓰레드 시작


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (v.getId() == R.id.btn_up) {
                    Up.setImageDrawable(getResources().getDrawable(R.drawable.pressed_btn_up));
                    main.getusbService().write(Commands.Position("U").getBytes());
                } else {
                    Down.setImageDrawable(getResources().getDrawable(R.drawable.pressed_btn_down));
                    main.getusbService().write(Commands.Position("D").getBytes());
                }
                break;
            case MotionEvent.ACTION_UP:
                if (v.getId() == R.id.btn_up) {
                    Up.setImageDrawable(getResources().getDrawable(R.drawable.btn_up));
                } else {
                    Down.setImageDrawable(getResources().getDrawable(R.drawable.btn_down));
                }
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ready:
                ready.setPressed();
                if (ready.getButton().isPressed()) {
                    main.PlaySound(new int[]{R.raw.mesurement_will_begin_after_bee_sound, R.raw.the_measurement_starts_when_you_hear_the_beep_sound_english});
                    main.getusbService().write(Commands.MeasureReady(String.valueOf(main.getMeasureWeight()), String.valueOf(main.getMeasureTime())).getBytes());
                    go.getButton().setClickable(true);
                    go.getButton().setColorFilter(Color.parseColor("#00000000"),
                            PorterDuff.Mode.SRC_ATOP);
                } else
                    main.getusbService().write("$CSP0#".getBytes());
                break;
            case R.id.btn_start:
//                go.setPressed();
//                if (go.IsPressed()) {
//                    main.PlaySound(new int[]{R.raw.bee_measurement_begin});
//                    if (resCalculator != null)
//                        main.getusbService().write(Commands.MeasureStart(main.getMeasureWeight(), main.getMeasureTime()).getBytes());
//                    resCalculator = new ResCalculator();
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            CountDownTimer timer = new CountDownTimer(Integer.parseInt(main.getMeasureTime()) * 1000, 1000) {
//                                @Override
//                                public void onTick(long millisUntilFinished) {
//                                }
//
//                                @Override
//                                public void onFinish() {
//                                    ready.setPressed();
//                                    go.setPressed();
//                                    main.PlaySound(new int[]{R.raw.measurement_complete_sound,
//                                            R.raw.stopping_measurement,
//                                            R.raw.thank_you_for_your_efforts,
//                                            R.raw.show_your_result,
//                                            R.raw.the_measurement_is_going_to_stop_english,
//                                            R.raw.thank_you_for_your_efforts_english,
//                                            R.raw.please_check_the_results_english,
//                                            R.raw.dynamic_care});
//                                    setBottomBar(true);
//                                }
//                            };
//                            timer.start();
//                        }
//                    });
//                } else
//                    main.getusbService().write("$CSP0#".getBytes());
                break;

            case R.id.btn_low: {
                Low.setPressed();
                if (Low.IsPressed())
                    main.getusbService().write(Commands.MeasureLevelCheck("L").getBytes());
                break;
            }
            case R.id.btn_mid: {
                Mid.setPressed();
                if (Mid.IsPressed())
                    main.getusbService().write(Commands.MeasureLevelCheck("M").getBytes());
                break;
            }

            case R.id.btn_high: {
                High.setPressed();
                if (High.IsPressed())
                    main.getusbService().write(Commands.MeasureLevelCheck("H").getBytes());
                break;
            }

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try{
        View view = inflater.inflate(R.layout.fragment_result_graph, container, false);

        setViews(view);
        setBottomBar(false);
            return view;
        }catch (Exception e)
        {
            Toast.makeText(main,e.toString(),Toast.LENGTH_LONG).show();
            return null;
        }
    }


    @Override
    public void HandleACK(ACK ack) {
        switch (ack.getCommandCode()) {
            case "AME":
                if (Integer.parseInt(ack.getTime()) % 120 == 0 && Integer.parseInt(ack.getTime()) > 1000) {
                    resCalculator.putNumber(Integer.parseInt(ack.getmTension()));
                    power.setMax(resCalculator.getStart() + 300000);
                    power.setProgress(Integer.parseInt(ack.getmTension()));
                }
                break;
        }
    }

    @Override
    public String getTitle() {
        if (Main.getisIsoTonic())
            return "등척성 측정 모드";
        else
            return "등장성 측정 모드";
    }

    @Override
    public int isHomeVisible() {
        return View.VISIBLE;
    }

    @Override
    public DCfragment getBackFragment() {
        return new Instruction(main);
    }

    @Override
    public DCfragment getNextFragment() {
        return new DetailResult(main, resCalculator.getStart(), resCalculator.getMax(), resCalculator.getMin(), resCalculator.getAverage());
    }


    private class ResCalculator {
        int sum = 0, count = 0, max = 0, min = 0, average = 0, start = -1;

        public void putNumber(int entry) {
            count++;
            start = start == -1 ? entry : 0;
            sum += entry;
            average = sum / count;
            if (entry > max)
                max = entry;
            if (entry < min)
                min = entry;
        }

        public int getStart() {
            return start;
        }

        public int getAverage() {
            return average;
        }

        public int getMax() {
            return max;
        }

        public int getMin() {
            return min;
        }

    }
}
