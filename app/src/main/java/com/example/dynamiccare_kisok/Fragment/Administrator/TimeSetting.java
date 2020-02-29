package com.example.dynamiccare_kisok.Fragment.Administrator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCButton;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.Object.ACK;
import com.example.dynamiccare_kisok.Fragment.SelectMode;
import com.example.dynamiccare_kisok.R;

public class TimeSetting extends DCfragment {
    DCButton btn_minutes,btn_hour;
    Button btn_ok;

    public TimeSetting()
    {
        super();
    }

    public TimeSetting(Main main)
    {
        super(main);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_ok:
                main.ReplaceFragment(new SelectMode(main));
                break;
            case R.id.btn_minutes:
                btn_minutes.setPressed();
                if(btn_minutes.isPressed())
                    main.getDynamicCare().setLimit(30);
                break;
            case R.id.btn_hour:
                btn_hour.setPressed();
                if(btn_hour.isPressed())
                    main.getDynamicCare().setLimit(60);
                break;

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_setting,container, false);
        super.onCreate(savedInstanceState);
        try
        {
            btn_minutes = new DCButton(main,view.findViewById(R.id.btn_minutes),getResources().getDrawable(R.drawable.btn_minute_c));
            btn_hour = new DCButton(main,view.findViewById(R.id.btn_hour),getResources().getDrawable(R.drawable.btn_hr_c));
            btn_ok = view.findViewById(R.id.btn_ok);

            btn_minutes.getButton().setOnClickListener(this);
            btn_hour.getButton().setOnClickListener(this);
            btn_ok.setOnClickListener(this);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public DCfragment getBackFragment() {
        return null;
    }

    @Override
    public DCfragment getNextFragment() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public int isHomeVisible() {
        return 0;
    }

    @Override
    public void HandleACK(ACK ack) {
        super.HandleACK(ack);
    }
}
