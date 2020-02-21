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
import com.example.dynamiccare_kisok.Common.Util.Commands;
import com.example.dynamiccare_kisok.R;

public class DetailResult extends DCfragment{
    ProgressBar start,average,max,min;

    Handler handler = new Handler(); // Thread 에서 화면에 그리기 위해서 필요

    final int MSG_PROGRESS=10;

    public DetailResult(Main main)
    {
        super(main);
    }



    @Override
    public void onClick(View v)
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_result_detail,container, false);

        start = (ProgressBar)v.findViewById(R.id.progressBar_start);
        average = (ProgressBar)v.findViewById(R.id.progressBar_average);
        max = (ProgressBar)v.findViewById(R.id.progressBar_max);
        min = (ProgressBar)v.findViewById(R.id.progressBar_min);

        return v;
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
        return new GraphResult(main);
    }

    @Override
    public DCfragment getNextFragment()
    {
        return null;
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
                    break;
                default:
                    break;
            }
        }
    }

}
