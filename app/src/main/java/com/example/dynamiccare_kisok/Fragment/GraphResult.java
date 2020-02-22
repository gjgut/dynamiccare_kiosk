package com.example.dynamiccare_kisok.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCActionButton;
import com.example.dynamiccare_kisok.Common.Component.DCButton;
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

    Handler handler = new Handler(); // Thread 에서 화면에 그리기 위해서 필요

    final int MSG_PROGRESS = 10;

    public GraphResult(Main main) {
        super(main);
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
        t.start(); // 쓰레드 시작


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
                if (ready.getButton().isPressed()) {
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
                ready.setPressed();

                break;
            case R.id.btn_start:
                if (resCalculator != null)
                    main.getusbService().write(Commands.MeasureStart("300", "10").getBytes());
                resCalculator = new ResCalculator();
                break;

            case R.id.btn_low: {
                Low.setPressed();
                if (Low.isPressed())
                    Commands.MeasureLevelCheck("L");
                break;
            }
            case R.id.btn_mid: {
                Mid.setPressed();
                if (Mid.isPressed())
                    Commands.MeasureLevelCheck("M");
                break;
            }

            case R.id.btn_high: {
                High.setPressed();
                if (High.isPressed())
                    Commands.MeasureLevelCheck("H");
                break;
            }

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_graph, container, false);
        setViews(view);

        return view;
    }


    @Override
    public void HandleACK(ACK ack) {
        switch (ack.getCommandCode()) {
            case "AME":
                if (Integer.parseInt(ack.getTime()) % 120 == 0) {
                    resCalculator.putNumber(Integer.parseInt(ack.getmTension()));
                    power.setMax(resCalculator.getStart()+150);
                    power.setProgress(resCalculator.getMax());
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
        return new DetailResult(main);
    }


    private class ResCalculator {
        int sum = 0, count = 0, max = 0, min = 0, average = 0, start = -1;

        public void putNumber(int entry) {
            count++;
            start = start == -1 ? entry : 0;
            sum += entry;
            if (entry > max)
                max = entry;
            if (entry < min)
                min = entry;
        }
        public int getStart(){return start;}

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
