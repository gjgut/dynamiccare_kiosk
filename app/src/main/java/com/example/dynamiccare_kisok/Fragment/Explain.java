package com.example.dynamiccare_kisok.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCButton;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.Excercise.ArmCurl;
import com.example.dynamiccare_kisok.Common.Excercise.ArmExtension;
import com.example.dynamiccare_kisok.Common.Excercise.BenchPress;
import com.example.dynamiccare_kisok.Common.Excercise.CarfRaise;
import com.example.dynamiccare_kisok.Common.Excercise.DeadLift;
import com.example.dynamiccare_kisok.Common.Excercise.LatPullDown;
import com.example.dynamiccare_kisok.Common.Excercise.ShoulderPress;
import com.example.dynamiccare_kisok.Common.Excercise.Squat;
import com.example.dynamiccare_kisok.R;

public class Explain extends DCfragment {
    DCButton bench,squat,deadlift,press,curl,extension,latpull,carf;
    ImageView Body;

    public Explain(Main main)
    {
        super(main);
    }

    public void setBottomBar()
    {
        if(DCButton.getPressedButton()==null)
            Main.getBottombar().findViewById(R.id.btn_next).setVisibility(View.INVISIBLE);
        else
            Main.getBottombar().findViewById(R.id.btn_next).setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.mes_btn_bench:
            {
                bench.setPressed();
                Main.setCurrentExcercise(new BenchPress(main));
//                Main.getusbService().write(Commands.MeasureSet());
                break;
            }
            case R.id.mes_btn_squat:
            {
                squat.setPressed();
                Main.setCurrentExcercise(new Squat(main));
                break;
            }
            case R.id.mes_btn_deadlift:
            {
                deadlift.setPressed();
                Main.setCurrentExcercise(new DeadLift(main));
                break;
            }
            case R.id.mes_btn_shoulderpress:
            {
                press.setPressed();
                Main.setCurrentExcercise(new ShoulderPress(main));
                break;
            }
            case R.id.mes_btn_latpulldown:
            {
                latpull.setPressed();
                Main.setCurrentExcercise(new LatPullDown(main));
                break;
            }
            case R.id.mes_btn_carfraise:
            {
                carf.setPressed();
                Main.setCurrentExcercise(new CarfRaise(main));
                break;
            }
            case R.id.mes_btn_armcurl:
            {
                curl.setPressed();
                Main.setCurrentExcercise(new ArmCurl(main));
                break;
            }
            case R.id.mes_btn_armextension:
            {
                extension.setPressed();
                Main.setCurrentExcercise(new ArmExtension(main));
                break;
            }
        }
        setBottomBar();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explain,container, false);
        setViews(view);
        return view;
    }

    public void setViews(View view)
    {
        try {
            bench = new DCButton((ImageButton) view.findViewById(R.id.mes_btn_bench),
                    getResources().getDrawable(R.drawable.pressed_btn_benchpress),
                    getResources().getDrawable(R.drawable.exp_pec));
            squat = new DCButton((ImageButton) view.findViewById(R.id.mes_btn_squat),
                    getResources().getDrawable(R.drawable.pressed_btn_squat),
                    getResources().getDrawable(R.drawable.exp_quad));
            deadlift = new DCButton((ImageButton) view.findViewById(R.id.mes_btn_deadlift),
                    getResources().getDrawable(R.drawable.pressed_btn_deadlift),
                    getResources().getDrawable(R.drawable.exp_spine));
            press = new DCButton((ImageButton) view.findViewById(R.id.mes_btn_shoulderpress),
                    getResources().getDrawable(R.drawable.pressed_btn_shoulderpress),
                    getResources().getDrawable(R.drawable.exp_shoulder));
            latpull = new DCButton((ImageButton) view.findViewById(R.id.mes_btn_latpulldown),
                    getResources().getDrawable(R.drawable.pressed_btn_latpulldown),
                    getResources().getDrawable(R.drawable.exp_lat));
            carf = new DCButton((ImageButton) view.findViewById(R.id.mes_btn_carfraise),
                    getResources().getDrawable(R.drawable.pressed_btn_carfraise),
                    getResources().getDrawable(R.drawable.exp_lower_leg));
            curl = new DCButton((ImageButton) view.findViewById(R.id.mes_btn_armcurl),
                    getResources().getDrawable(R.drawable.pressed_btn_amrcurl),
                    getResources().getDrawable(R.drawable.exp_biceps));
            extension = new DCButton((ImageButton) view.findViewById(R.id.mes_btn_armextension),
                    getResources().getDrawable(R.drawable.pressed_btn_armextension),
                    getResources().getDrawable(R.drawable.exp_triceps));
            Body = view.findViewById(R.id.exp_body);
            DCButton.setBody(Body);

            bench.getButton().setOnClickListener(this);
            squat.getButton().setOnClickListener(this);
            deadlift.getButton().setOnClickListener(this);
            press.getButton().setOnClickListener(this);
            latpull.getButton().setOnClickListener(this);
            carf.getButton().setOnClickListener(this);
            curl.getButton().setOnClickListener(this);
            extension.getButton().setOnClickListener(this);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
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
        return  View.INVISIBLE;
    }

    @Override
    public DCfragment getBackFragment()
    {
        return new SelectMode(main);
    }

    @Override
    public DCfragment getNextFragment()
    {
        return new Instruction(main);
    }
}
