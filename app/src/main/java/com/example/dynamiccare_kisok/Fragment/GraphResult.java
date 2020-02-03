package com.example.dynamiccare_kisok.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCButton;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.R;

public class GraphResult extends DCfragment {

    DCButton Low,Mid,High;

    public GraphResult(Main main)
    {
        super(main);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_low:
            {
                Low.setPressed();
                break;
            }
            case R.id.btn_mid:
            {
                Mid.setPressed();
                break;
            }

            case R.id.btn_high:
            {
                High.setPressed();
                break;
            }

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_graph,container, false);
        setViews(view);
        return view;
    }

    public void setViews(View v)
    {
        Low = new DCButton((ImageButton)v.findViewById(R.id.btn_low),getResources().getDrawable(R.drawable.pressed_btn_low));
        Mid = new DCButton((ImageButton)v.findViewById(R.id.btn_mid),getResources().getDrawable(R.drawable.pressed_btn_mid));
        High = new DCButton((ImageButton)v.findViewById(R.id.btn_high),getResources().getDrawable(R.drawable.pressed_btn_high));

        Low.getButton().setOnClickListener(this);
        Mid.getButton().setOnClickListener(this);
        High.getButton().setOnClickListener(this);

    }

    @Override
    public String getTitle() {
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
        return new DetailResult(main);
    }
}
