package com.example.dynamiccare_kisok.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCButton;
import com.example.dynamiccare_kisok.Common.Component.DCEditText;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.DynamicCare;
import com.example.dynamiccare_kisok.Common.Excercise.Excercise;
import com.example.dynamiccare_kisok.Common.Object.ACK;
import com.example.dynamiccare_kisok.R;

public class TimeSetting extends DCfragment {
    DCButton btn_minutes, btn_hour;
    DCEditText edt_adminpw;
    TextView reject;
    Button btn_ok;
    DynamicCare care;
    DCfragment prev;
    ExcerciseMode excerciseMode;
    int count = 0;


    public TimeSetting() {
        super();
    }

    public TimeSetting(Main main) {
        super(main);
        care = (DynamicCare) main.getApplication();
    }

    public TimeSetting(Main main, DCfragment prev) {
        super(main);
        care = (DynamicCare) main.getApplication();
        this.prev = prev;
        if(prev.getClass()== ExcerciseMode.class)
        this.excerciseMode = (ExcerciseMode) prev;
    }

    @Override
    public void onClick(View v) {
        try{
        switch (v.getId()) {
            case R.id.btn_ok:
                if (edt_adminpw.getSource().getText().toString().equals(care.getAdminPassword())) {
                    if(prev == null)
                        main.ReplaceFragment(new SelectMode(main),true);
                    else if (excerciseMode != null) {
                        if (timer != null)
                            timer.cancel();
                        Bundle outState = excerciseMode.getSaveState();
                        outState.putInt("count", count);
                        main.ReplaceFragment(new ExcerciseMode(main,outState),false);
                    }
                    else if(prev.getClass()==SelectMode.class)
                        main.ReplaceFragment(prev,false);
                    main.setTimer(care.getLimit());
                } else
                    reject.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_minutes:
                btn_minutes.setPressed();
                if (btn_minutes.IsPressed()) {
                    care.setLimit(30);
                }
                break;
            case R.id.btn_hour:
                btn_hour.setPressed();
                if (btn_hour.IsPressed()) {
                    care.setLimit(3600);
                }
                break;
        }
        }catch (Exception e)
        {
            Log.i("Error",e.toString());
        }

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_time_setting, container, false);
//        if (prev == null)
//            prev = new SelectMode(main);
        if (excerciseMode != null) {
            count = excerciseMode.getSaveState().getInt("count");
        }
        try {
            btn_minutes = new DCButton(main, view.findViewById(R.id.btn_minutes), getResources().getDrawable(R.drawable.btn_minute_c));
            btn_hour = new DCButton(main, view.findViewById(R.id.btn_hour), getResources().getDrawable(R.drawable.btn_hr_c));
            btn_ok = view.findViewById(R.id.btn_ok);
            edt_adminpw = new DCEditText(view.findViewById(R.id.et_adminpw),reject);

            btn_minutes.getButton().setOnClickListener(this);
            btn_hour.getButton().setOnClickListener(this);
            btn_ok.setOnClickListener(this);

            btn_minutes.setPressedWithNoSound();
            if (btn_minutes.IsPressed()) {
                care.setLimit(30);
            }

            reject = view.findViewById(R.id.password_reject);
            reject.setVisibility(View.INVISIBLE);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public DCfragment getBackFragment() {
        try{
            if (excerciseMode != null) {
                if (timer != null)
                    timer.cancel();
                Bundle outState = excerciseMode.getSaveState();
                outState.putInt("count", count);
                return new ExcerciseMode(main,outState);
            } else
                return prev;
        }catch (Exception e)
        {
            Log.i("Error",e.toString());
            return null;
        }
    }
    @Override
    public String getTitle() {
        return "사용 시간 설정";
    }

    @Override
    public int isHomeVisible() {
        return View.INVISIBLE;
    }

}
