package com.example.dynamiccare_kisok.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCButton;
import com.example.dynamiccare_kisok.Common.Component.DCButtonManager;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
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
import com.example.dynamiccare_kisok.Common.Util.ACKListener;
import com.example.dynamiccare_kisok.Common.Util.Commands;
import com.example.dynamiccare_kisok.R;

public class Explain extends DCfragment {
    DCButton bench, squat, deadlift, press, curl, extension, latpull, carf;
    DCButtonManager dcButtonManager;
    Handler handler;
    ImageView Body;
    boolean isResume;

    public Explain(Main main) {
        super(main);
        try {
            main.getusbService().write(Commands.ExcerciseMode(main.getisIsoTonic()));
            if (main.getisIsoTonic())
                main.PlaySound(new int[]{R.raw.sotonic_log_mode, R.raw.sotonic_log_mode_english});
            else
                main.PlaySound(new int[]{R.raw.metric_log_mode, R.raw.metric_log_mode_english});

        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    public void setBottomBar(boolean value) {
        try {
            if (value)
                Main.getBottombar().findViewById(R.id.btn_next).setVisibility(View.VISIBLE);
            else
                Main.getBottombar().findViewById(R.id.btn_next).setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.mes_btn_bench:
                    setExcercise(bench, new BenchPress(main));
                    break;
                case R.id.mes_btn_squat:
                    setExcercise(squat, new Squat(main));
                    break;
                case R.id.mes_btn_deadlift:
                    setExcercise(deadlift, new DeadLift(main));
                    break;
                case R.id.mes_btn_shoulderpress:
                    setExcercise(press, new ShoulderPress(main));
                    break;
                case R.id.mes_btn_latpulldown:
                    setExcercise(latpull, new LatPullDown(main));
                    break;
                case R.id.mes_btn_carfraise:
                    setExcercise(carf, new CarfRaise(main));
                    break;
                case R.id.mes_btn_armcurl:
                    setExcercise(curl, new ArmCurl(main));
                    break;
                case R.id.mes_btn_armextension:
                    setExcercise(extension, new ArmExtension(main));
                    break;
            }
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    public void setExcercise(DCButton button, Excercise excercise) {
        try {
            if (DCButton.getPressedButton() != button) {
                if (isResume)
                    button.setPressedWithNoSound();
                else
                    button.setPressed();
            }
            if (button.IsPressed() &&
                    (dcButtonManager.getDCState().equals(DCButtonManager.State.Clear) ||
                            dcButtonManager.getDCState().equals(DCButtonManager.State.Setted))) {
                main.setCurrentExcercise(excercise);

                if (!isResume)
                    dcButtonManager.setDCState(DCButtonManager.State.StartSetting);

                if (!isResume) {
                    Main.getusbService().write(
                            Commands.MeasureSet(excercise.getMode(),
                                    String.valueOf(main.getMeasureWeight()),
                                    "000", String.valueOf(main.getMeasureTime()),
                                    "1",
                                    "0"));
//                    handler.postDelayed(new Runnable() {
//                        public void run() {
//                            main.HandleACK(ACKListener.ParseACK("$PCA#"));
//                        }
//                    }, 3000);
                }
                setBottomBar(true);
                isResume = false;
            }
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (main.getCurrentFragment().getClass() != TimeSetting.class &&
                main.getCurrentFragment().getClass() != Instruction.class &&
                main.getCurrentFragment().getClass() != GraphResult.class)
                main.getusbService().write(Commands.ExcerciseStop(main.getCurrentExcercise().getMode(),
                        "0",
                        "0",
                        "0"));

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explain, container, false);
        setViews(view);
        return view;
    }

    public void setViews(View view) {
        try {
            bench = new DCButton(main, view.findViewById(R.id.mes_btn_bench),
                    getResources().getDrawable(R.drawable.pressed_btn_benchpress),
                    getResources().getDrawable(R.drawable.exp_pec));
            squat = new DCButton(main, view.findViewById(R.id.mes_btn_squat),
                    getResources().getDrawable(R.drawable.pressed_btn_squat),
                    getResources().getDrawable(R.drawable.exp_quad));
            deadlift = new DCButton(main, view.findViewById(R.id.mes_btn_deadlift),
                    getResources().getDrawable(R.drawable.pressed_btn_deadlift),
                    getResources().getDrawable(R.drawable.exp_spine));
            press = new DCButton(main, view.findViewById(R.id.mes_btn_shoulderpress),
                    getResources().getDrawable(R.drawable.pressed_btn_shoulderpress),
                    getResources().getDrawable(R.drawable.exp_shoulder));
            latpull = new DCButton(main, view.findViewById(R.id.mes_btn_latpulldown),
                    getResources().getDrawable(R.drawable.pressed_btn_latpulldown),
                    getResources().getDrawable(R.drawable.exp_lat));
            carf = new DCButton(main, view.findViewById(R.id.mes_btn_carfraise),
                    getResources().getDrawable(R.drawable.pressed_btn_carfraise),
                    getResources().getDrawable(R.drawable.exp_lower_leg));
            curl = new DCButton(main, view.findViewById(R.id.mes_btn_armcurl),
                    getResources().getDrawable(R.drawable.pressed_btn_amrcurl),
                    getResources().getDrawable(R.drawable.exp_biceps));
            extension = new DCButton(main, view.findViewById(R.id.mes_btn_armextension),
                    getResources().getDrawable(R.drawable.pressed_btn_armextension),
                    getResources().getDrawable(R.drawable.exp_triceps));
            Body = view.findViewById(R.id.exp_body);
            DCButton.setBody(Body);

            dcButtonManager = new DCButtonManager(bench, squat, deadlift, press, curl, extension, latpull, carf,dcButtonManager.getDCState());
            handler = new Handler();

            setListener();


            if (main.getCurrentExcercise() != null)
            {
                isResume = true;
            }


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
                case "CarfRaise":
                    setExcercise(carf, new CarfRaise(main));
                    break;
                case "ArmCurl":
                    setExcercise(curl, new ArmCurl(main));
                    break;
                case "ArmExtension":
                    setExcercise(extension, new ArmExtension(main));
                    break;
                case "LatPullDown":
                    setExcercise(latpull, new LatPullDown(main));
                    break;
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return new SelectMode(main);
    }

    @Override
    public DCfragment getNextFragment() {
        return new Instruction(main);
    }
}
