package com.PowerLog.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.PowerLog.Activity.Main;
import com.PowerLog.Common.Component.DCfragment;
import com.PowerLog.R;

public class DetailResult extends DCfragment {
    ProgressBar start, average, max, min;
    TextView txt_start, txt_max, txt_min, txt_average;
    int value_start, value_max, value_min, value_average;
    TextView uppertitle, lowertitle;
    ImageView body;
    Handler handler = new Handler(); // Thread 에서 화면에 그리기 위해서 필요



    public DetailResult(Main main) {
        super(main);
    }

    public DetailResult(Main main, int start, int max, int min, int average) {
        super(main);
        value_start = start;
        value_average = average;
        value_max = max;
        value_min = min;

    }
    private void progressThread(ProgressBar p,int value)
    {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() { // Thread 로 작업할 내용을 구현
                for (int i = 0; i <= value; i += 5000) {
                    final int value2 = i;
                    handler.post(new Runnable() {
                        @Override
                        public void run() { // 화면에 변경하는 작업을 구현
                            p.setProgress(value2);
                        }
                    });

                    try {
                        Thread.sleep(50); // 시간지연
                    } catch (InterruptedException e) {
                    }
                } // end of while
            }
        });
        th.start();
    }

    @Override
    public void onClick(View v) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v;
        if (care.IsKiosk())
            v = inflater.inflate(R.layout.kiosk_fragment_result_detail, container, false);
        else
            v = inflater.inflate(R.layout.fragment_result_detail, container, false);
        try {

            Main.getBottombar().findViewById(R.id.btn_next).setVisibility(View.INVISIBLE);

            txt_start =  v.findViewById(R.id.txt_start);
            txt_max = v.findViewById(R.id.txt_max);
            txt_min = v.findViewById(R.id.txt_min);
            txt_average =  v.findViewById(R.id.txt_average);


            txt_start.setText(value_start / 1000 + "kg");
            txt_max.setText(value_max / 1000 + "kg");
            txt_min.setText(value_min / 1000 + "kg");
            txt_average.setText(value_average / 1000 + "kg");

            Log.i("Value_start", String.valueOf(value_start));
            Log.i("Value_max", String.valueOf(value_max));
            Log.i("Value_min", String.valueOf(value_min));
            Log.i("Value_average", String.valueOf(value_average));

            start = v.findViewById(R.id.progressBar_start);
            average =  v.findViewById(R.id.progressBar_average);
            max = v.findViewById(R.id.progressBar_max);
            min = v.findViewById(R.id.progressBar_min);


            uppertitle = v.findViewById(R.id.txt_detail_title);
            lowertitle = v.findViewById(R.id.txt_detail_cont_title);
            body = v.findViewById(R.id.detail_body);

            uppertitle.setText("측정 결과 확인");
            lowertitle.setText(main.getCurrentExcercise().getSimpleName() + " " + main.getCurrentExcercise().getMuscleName());
            body.setImageDrawable(main.getCurrentExcercise().getMappingBody());

            Log.i("value_max", String.valueOf(value_max));

            progressThread(start,value_start);
            progressThread(max,value_max);
            progressThread(min,value_min);
            progressThread(average,value_average);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return v;
    }

    @Override
    public String getTitle() {
        if (main.getisIsoTonic())
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
        return new GraphResult(main);
    }



}