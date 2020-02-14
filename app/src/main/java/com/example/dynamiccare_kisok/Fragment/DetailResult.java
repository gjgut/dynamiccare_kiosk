package com.example.dynamiccare_kisok.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCButton;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.Util.Commands;
import com.example.dynamiccare_kisok.R;

public class DetailResult extends DCfragment {
    DCButton Low,Mid,High;
    ProgressBar start,average,max,min;
    TextView txt_start,txt_average,txt_max,txt_min;

    Handler handler = new Handler(); // Thread 에서 화면에 그리기 위해서 필요

    final int MSG_PROGRESS=10;

    public DetailResult(Main main)
    {
        super(main);
    }

    public void setViews(View v)
    {
        Low = new DCButton((ImageButton)v.findViewById(R.id.btn_low),getResources().getDrawable(R.drawable.pressed_btn_low));
        Mid = new DCButton((ImageButton)v.findViewById(R.id.btn_mid),getResources().getDrawable(R.drawable.pressed_btn_mid));
        High = new DCButton((ImageButton)v.findViewById(R.id.btn_high),getResources().getDrawable(R.drawable.pressed_btn_high));

        start = (ProgressBar)v.findViewById(R.id.progressBar_start);
        average = (ProgressBar)v.findViewById(R.id.progressBar_average);
        max = (ProgressBar)v.findViewById(R.id.progressBar_max);
        min = (ProgressBar)v.findViewById(R.id.progressBar_min);

        txt_start = (TextView) v.findViewById(R.id.txt_weight_start);
        txt_average = (TextView)v.findViewById(R.id.txt_weight_average);
        txt_max = (TextView)v.findViewById(R.id.txt_weight_max);
        txt_min = (TextView)v.findViewById(R.id.txt_weight_min);

        Low.getButton().setOnClickListener(this);
        Mid.getButton().setOnClickListener(this);
        High.getButton().setOnClickListener(this);
//        ThreadToProgress threadToProgress = new ThreadToProgress();
////        threadToProgress.start();



        Thread t = new Thread(new Runnable() {
            @Override
            public void run() { // Thread 로 작업할 내용을 구현
                 int value = 0;
                 int add = 1;
                while(true) {
                    value = value + add;
                        if (value>=100) {
                            break;
                        }
                        final int value2=value;
                    handler.post(new Runnable() {
                        @Override
                        public void run() { // 화면에 변경하는 작업을 구현
                            start.setProgress(value2);
                            average.setProgress(value2);
                            max.setProgress(value2);
                            min.setProgress(value2);
                        }
                    });

                    try {
                        Thread.sleep(50); // 시간지연
                    } catch (InterruptedException e) {    }
                } // end of while
            }
        });
        t.start(); // 쓰레드 시작



    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_low:
            {
                Low.setPressed();
                if(Low.isPressed())
                    Commands.MeasureLevelCheck("L");
                break;
            }
            case R.id.btn_mid:
            {
                Mid.setPressed();
                if(Mid.isPressed())
                    Commands.MeasureLevelCheck("M");
                break;
            }

            case R.id.btn_high:
            {
                High.setPressed();
                if(High.isPressed())
                    Commands.MeasureLevelCheck("H");
                break;
            }

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_detail,container, false);
        setViews(view);

        return view;
    }

    @Override
    public String getTitle() {
        if(Main.getisIsoTonic())
            return "등척성 측정 모드";
        else
            return "등장성 측정 모드";
    }

    @Override
    public int isHomeVisible() {
        return  View.VISIBLE;
    }

    @Override
    public DCfragment getBackFragment()
    {
        return new Instruction(main);
    }

    @Override
    public DCfragment getNextFragment()
    {
        return null;
    }

    private class ThreadToProgress extends Thread
    {
        ProgressHandler ProgressHandler = new ProgressHandler();

        public void run()
        {
            for (int i = 0; i <= 100; i++)
            {
               final Message msg = ProgressHandler.obtainMessage(MSG_PROGRESS, i, 0);

                ProgressHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ProgressHandler.sendMessage(msg);
                    }
                }, 1000);
            }
        }
    }

    private class ProgressHandler extends Handler
    {
        public void handleMessage(Message msg)
        {
            int currentProgress = msg.arg1;

            switch (msg.what)
            {
                // 프로그레스 바의 진행과 관계된 메시지 코드입니다.
                case MSG_PROGRESS:
                    // 텍스트 뷰와 프로그레스 바를 갱신합니다.
                    start.setProgress(currentProgress);
                    break;
                default:
                    break;
            }
        }
    }

}
