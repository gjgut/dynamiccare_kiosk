package com.example.dynamiccare_kisok.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCActionButton;
import com.example.dynamiccare_kisok.Common.Component.DCButton;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.Util.Commands;
import com.example.dynamiccare_kisok.R;

public class DetailResult extends DCfragment{
    ProgressBar start,average,max,min;
    TextView txt_start,txt_max,txt_min,txt_average;
    int value_start,value_max,value_min,value_average;
    TextView uppertitle,lowertitle;
    ImageView body;
    Handler handler = new Handler(); // Thread 에서 화면에 그리기 위해서 필요


    public DetailResult(Main main)
    {
        super(main);
    }
    public DetailResult(Main main,int start,int max,int min,int average)
    {
        super(main);
        value_start = start;
        value_average = average;
        value_max = max;
        value_min = min;

    }



    @Override
    public void onClick(View v)
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_result_detail,container, false);
        try {

            txt_start = (TextView) v.findViewById(R.id.txt_start);
            txt_max = (TextView) v.findViewById(R.id.txt_max);
            txt_min = (TextView) v.findViewById(R.id.txt_min);
            txt_average = (TextView) v.findViewById(R.id.txt_average);

            txt_start.setText(value_start/1000 + "kg");
            txt_max.setText(value_max/1000 + "kg");
            txt_min.setText(value_min/1000 + "kg");
            txt_average.setText(value_average/1000 + "kg");

            start = (ProgressBar) v.findViewById(R.id.progressBar_start);
            average = (ProgressBar) v.findViewById(R.id.progressBar_average);
            max = (ProgressBar) v.findViewById(R.id.progressBar_max);
            min = (ProgressBar) v.findViewById(R.id.progressBar_min);


            uppertitle = v.findViewById(R.id.txt_detail_title);
            lowertitle = v.findViewById(R.id.txt_detail_cont_title);
            body = v.findViewById(R.id.detail_body);

            uppertitle.setText("측정 결과 확인");
            lowertitle.setText(main.getCurrentExcercise().getSimpleName() + " " + main.getCurrentExcercise().getMuscleName());
            body.setImageDrawable(main.getCurrentExcercise().getMappingBody());

            Log.i("value_max",String.valueOf(value_max));

            Thread th_start =  new Thread(new Runnable() {
                @Override
                public void run() { // Thread 로 작업할 내용을 구현
                    for(int i=0;i>=value_start;i+=5)
                    {
                        final int value2 = i;
                        handler.post(new Runnable() {
                            @Override
                            public void run() { // 화면에 변경하는 작업을 구현
                                start.setProgress(value2);
                            }
                        });

                        try {
                            Thread.sleep(50); // 시간지연
                        } catch (InterruptedException e) {
                        }
                    } // end of while
                }
            });
            Thread th_max = new Thread(new Runnable() {
                @Override
                public void run() { // Thread 로 작업할 내용을 구현
                    for(int i=0;i>=value_max;i+=5)
                    {
                        final int value2 = i;
                        handler.post(new Runnable() {
                            @Override
                            public void run() { // 화면에 변경하는 작업을 구현
                                max.setProgress(value2);
                            }
                        });

                        try {
                            Thread.sleep(50); // 시간지연
                        } catch (InterruptedException e) {
                        }
                    } // end of while
                }
            });
            Thread th_min =  new Thread(new Runnable() {
                @Override
                public void run() { // Thread 로 작업할 내용을 구현
                    for(int i=0;i>=value_min;i+=5)
                    {
                        final int value2 = i;
                        handler.post(new Runnable() {
                            @Override
                            public void run() { // 화면에 변경하는 작업을 구현
                                min.setProgress(value2);
                            }
                        });

                        try {
                            Thread.sleep(50); // 시간지연
                        } catch (InterruptedException e) {
                        }
                    } // end of while
                }
            });
            Thread th_average =  new Thread(new Runnable() {
                @Override
                public void run() { // Thread 로 작업할 내용을 구현
                    for(int i=0;i>=value_average;i+=5)
                    {
                        final int value2 = i;
                        handler.post(new Runnable() {
                            @Override
                            public void run() { // 화면에 변경하는 작업을 구현
                                average.setProgress(value2);
                            }
                        });

                        try {
                            Thread.sleep(50); // 시간지연
                        } catch (InterruptedException e) {
                        }
                    } // end of while
                }
            });
            th_start.start(); // 쓰레드 시작
            th_max.start(); // 쓰레드 시작
            th_min.start(); // 쓰레드 시작
            th_average.start(); // 쓰레드 시작

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return v;
    }

    @Override
    public String getTitle() {
        if(main.getisIsoTonic())
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
        return new GraphResult(main);
    }

    @Override
    public DCfragment getNextFragment()
    {
        return null;
    }


}
