package com.example.dynamiccare_kisok.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Util.Commands;
import com.example.dynamiccare_kisok.Common.Component.DCActionButton;
import com.example.dynamiccare_kisok.Common.Component.DCButton;
import com.example.dynamiccare_kisok.Common.Component.DCButtonManager;
import com.example.dynamiccare_kisok.Common.Component.DCEditText;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.R;

import java.util.ArrayList;
import java.util.List;

public class ExcerciseMode extends DCfragment{

    DCButton bench,squat,deadlift,press,curl,extension,latpull,carf;
    DCActionButton start,stop,ready;
    DCButtonManager dcButtonManager;
    DCEditText edt_rest,edt_weight,edt_set,edt_count;
    TextView txt_count,txt_set;
    ImageView Body;
    LinearLayout container;
    Spinner spin_level;
    boolean isIsokinetic;

    public ExcerciseMode(Main main)
    {
        super(main);
    }
    public ExcerciseMode(Main main,boolean isIsokinetic)
    {
        super(main);
        this.isIsokinetic = isIsokinetic;
    }

    @Override
    public void onClick(View v)
    {
        Handler handler = new Handler();
        switch (v.getId())
        {
            case R.id.exc_tab_btn_bench:
            {
                bench.setPressed();
                if(bench.isPressed()) {
                    main.PlaySound(R.raw.normal_button);
                    dcButtonManager.setDCState(DCButtonManager.State.StartSetting);
                    Main.getusbService().write(
                            Commands.ExcerciseStart("05",
                                    edt_weight.getSource().getText().toString(),
                                    edt_count.getSource().getText().toString(),
                                    edt_set.getSource().getText().toString()).getBytes());
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            dcButtonManager.setDCState(DCButtonManager.State.Setted);
                        }
                    }, 5000);
                }
                else
                {
                    dcButtonManager.setDCState(DCButtonManager.State.Clear);
                }
                break;
            }
            case R.id.exc_tab_btn_squat:
            {
                squat.setPressed();
                if(squat.isPressed()) {
                    dcButtonManager.setDCState(DCButtonManager.State.StartSetting);
                    Main.getusbService().write(
                            Commands.ExcerciseStart("01",
                                    edt_weight.getSource().getText().toString(),
                                    edt_count.getSource().getText().toString(),
                                    edt_set.getSource().getText().toString()).getBytes());
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            dcButtonManager.setDCState(DCButtonManager.State.Setted);
                        }
                    }, 5000);
                }
                else
                {
                    dcButtonManager.setDCState(DCButtonManager.State.Clear);
                }
                break;
            }
            case R.id.exc_tab_btn_deadlift:
            {
                deadlift.setPressed();
                if(deadlift.isPressed()) {
                    dcButtonManager.setDCState(DCButtonManager.State.StartSetting);
                    Main.getusbService().write(
                            Commands.ExcerciseStart("02",
                                    edt_weight.getSource().getText().toString(),
                                    edt_count.getSource().getText().toString(),
                                    edt_set.getSource().getText().toString()).getBytes());
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            dcButtonManager.setDCState(DCButtonManager.State.Setted);
                        }
                    }, 5000);
                }
                else
                {
                    dcButtonManager.setDCState(DCButtonManager.State.Clear);
                }
                break;
            }
            case R.id.exc_tab_btn_shoulderpress:
            {
                press.setPressed();
                if(press.isPressed()) {
                    dcButtonManager.setDCState(DCButtonManager.State.StartSetting);
                    Main.getusbService().write(
                            Commands.ExcerciseStart("03",
                                    edt_weight.getSource().getText().toString(),
                                    edt_count.getSource().getText().toString(),
                                    edt_set.getSource().getText().toString()).getBytes());
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            dcButtonManager.setDCState(DCButtonManager.State.Setted);
                        }
                    }, 5000);
                }
                else
                {
                    dcButtonManager.setDCState(DCButtonManager.State.Clear);
                }
                break;
            }
            case R.id.exc_tab_btn_latpulldown:
            {
                latpull.setPressed();
                if(latpull.isPressed()) {
                    dcButtonManager.setDCState(DCButtonManager.State.StartSetting);
                    Main.getusbService().write(
                            Commands.ExcerciseStart("18",
                                    edt_weight.getSource().getText().toString(),
                                    edt_count.getSource().getText().toString(),
                                    edt_set.getSource().getText().toString()).getBytes());
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            dcButtonManager.setDCState(DCButtonManager.State.Setted);
                        }
                    }, 5000);
                }
                else
                {
                    dcButtonManager.setDCState(DCButtonManager.State.Clear);
                }
                break;
            }
            case R.id.exc_tab_btn_carfraise:
            {
                carf.setPressed();
                if(carf.isPressed()) {
                    dcButtonManager.setDCState(DCButtonManager.State.StartSetting);
                    Main.getusbService().write(
                            Commands.ExcerciseStart("14",
                                    edt_weight.getSource().getText().toString(),
                                    edt_count.getSource().getText().toString(),
                                    edt_set.getSource().getText().toString()).getBytes());
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            dcButtonManager.setDCState(DCButtonManager.State.Setted);
                        }
                    }, 5000);
                }
                else
                {
                    dcButtonManager.setDCState(DCButtonManager.State.Clear);
                }
                break;
            }
            case R.id.exc_tab_btn_armcurl:
            {
                curl.setPressed();
                if(curl.isPressed()) {
                    dcButtonManager.setDCState(DCButtonManager.State.StartSetting);
                    Main.getusbService().write(
                            Commands.ExcerciseStart("16",
                                    edt_weight.getSource().getText().toString(),
                                    edt_count.getSource().getText().toString(),
                                    edt_set.getSource().getText().toString()).getBytes());
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            dcButtonManager.setDCState(DCButtonManager.State.Setted);
                        }
                    }, 5000);
                }
                else
                {
                    dcButtonManager.setDCState(DCButtonManager.State.Clear);
                }
                break;
            }
            case R.id.exc_tab_btn_armextension:
            {
                extension.setPressed();
                if(extension.isPressed()) {
                    dcButtonManager.setDCState(DCButtonManager.State.StartSetting);
                    Main.getusbService().write(
                            Commands.ExcerciseStart("17",
                                    edt_weight.getSource().getText().toString(),
                                    edt_count.getSource().getText().toString(),
                                    edt_set.getSource().getText().toString()).getBytes());
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            dcButtonManager.setDCState(DCButtonManager.State.Setted);
                        }
                    }, 5000);
                }
                else
                {
                    dcButtonManager.setDCState(DCButtonManager.State.Clear);
                }
                break;
            }
            case R.id.exc_btn_start:
            {
                start.setPressed();
                dcButtonManager.setDCState(DCButtonManager.State.StartSetting);
                break;
            }
            case R.id.exc_btn_stop:
            {
                stop.setPressed();
                dcButtonManager.setDCState(DCButtonManager.State.Stop);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        dcButtonManager.setDCState(DCButtonManager.State.Setted);
                        stop.setPressed();
                    }
                }, 5000);
                break;
            }
            case R.id.exc_btn_ready:
            {
                ready.setPressed();
                dcButtonManager.setDCState(DCButtonManager.State.Excercise);
                Main.getusbService().write(
                        Commands.ExcerciseReady("01",edt_weight.getSource().getText().toString(),edt_count.getSource().getText().toString(),edt_set.getSource().getText().toString()).getBytes()
                );
                break;
            }


        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_excercise_mode,container, false);
        setViews(view);


        return view;
    }

    public void setViews(View view)
    {
        try {
            if(Main.getisIsoKinetic()) {
                view.findViewById(R.id.container_weight).setVisibility(View.GONE);
                view.findViewById(R.id.container_level).setVisibility(View.VISIBLE);
            }
            else {
                view.findViewById(R.id.container_level).setVisibility(View.GONE);
                view.findViewById(R.id.container_weight).setVisibility(View.VISIBLE);
            }


            bench = new DCButton(main);
            squat = new DCButton(main);
            deadlift = new DCButton(main);
            press = new DCButton(main);
            latpull = new DCButton(main);
            carf = new DCButton(main);
            curl = new DCButton(main);
            extension = new DCButton(main);
            start = new DCActionButton(main);
            stop = new DCActionButton(main);
            ready = new DCActionButton(main);
            edt_count = new DCEditText((EditText)view.findViewById(R.id.et_count));
            edt_weight = new DCEditText((EditText)view.findViewById(R.id.et_weight));
            edt_rest = new DCEditText((EditText)view.findViewById(R.id.et_rest));
            edt_set = new DCEditText((EditText)view.findViewById(R.id.et_set));

            bench.setButton((ImageButton) view.findViewById(R.id.exc_tab_btn_bench),
                    getResources().getDrawable(R.drawable.pressed_btn_benchpress),
                    getResources().getDrawable(R.drawable.body_pec));
            squat.setButton((ImageButton) view.findViewById(R.id.exc_tab_btn_squat),
                    getResources().getDrawable(R.drawable.pressed_btn_squat),
                    getResources().getDrawable(R.drawable.body_quad));
            deadlift.setButton((ImageButton) view.findViewById(R.id.exc_tab_btn_deadlift),
                    getResources().getDrawable(R.drawable.pressed_btn_deadlift),
                    getResources().getDrawable(R.drawable.body_spine));
            press.setButton((ImageButton) view.findViewById(R.id.exc_tab_btn_shoulderpress),
                    getResources().getDrawable(R.drawable.pressed_btn_shoulderpress),
                    getResources().getDrawable(R.drawable.body_deltoid));
            latpull.setButton((ImageButton) view.findViewById(R.id.exc_tab_btn_latpulldown),
                    getResources().getDrawable(R.drawable.pressed_btn_latpulldown),
                    getResources().getDrawable(R.drawable.body_lat));
            carf.setButton((ImageButton) view.findViewById(R.id.exc_tab_btn_carfraise),
                    getResources().getDrawable(R.drawable.pressed_btn_carfraise),
                    getResources().getDrawable(R.drawable.body_carf));
            curl.setButton((ImageButton) view.findViewById(R.id.exc_tab_btn_armcurl),
                    getResources().getDrawable(R.drawable.pressed_btn_amrcurl),
                    getResources().getDrawable(R.drawable.body_biceps));
            extension.setButton((ImageButton) view.findViewById(R.id.exc_tab_btn_armextension),
                    getResources().getDrawable(R.drawable.pressed_btn_armextension),
                    getResources().getDrawable(R.drawable.body_triceps));
            start.setButton((ImageButton) view.findViewById(R.id.exc_btn_start),
                    getResources().getDrawable(R.drawable.pressed_btn_start));
            stop.setButton((ImageButton) view.findViewById(R.id.exc_btn_stop),
                    getResources().getDrawable(R.drawable.pressed_btn_stop));
            ready.setButton((ImageButton) view.findViewById(R.id.exc_btn_ready),
                    getResources().getDrawable(R.drawable.pressed_btn_ready));
            txt_count = view.findViewById(R.id.txt_labelCount);
            txt_set = view.findViewById(R.id.txt_realset);
            spin_level = view.findViewById(R.id.spin_level);
            Body = view.findViewById(R.id.exc_body);
            DCButton.setBody(Body);

            List<String> data = new ArrayList<String>();
            data.add("1");data.add("2");data.add("3");data.add("4");data.add("5");

            spinnerAdapter spinnerAdapter = new spinnerAdapter(main,data);
            spin_level.setAdapter(spinnerAdapter);

            bench.getButton().setOnClickListener(this);
            squat.getButton().setOnClickListener(this);
            deadlift.getButton().setOnClickListener(this);
            press.getButton().setOnClickListener(this);
            latpull.getButton().setOnClickListener(this);
            carf.getButton().setOnClickListener(this);
            curl.getButton().setOnClickListener(this);
            extension.getButton().setOnClickListener(this);
            start.getButton().setOnClickListener(this);
            stop.getButton().setOnClickListener(this);
            ready.getButton().setOnClickListener(this);
            txt_count.setOnClickListener(this);
            txt_set.setOnClickListener(this);

            dcButtonManager = new DCButtonManager(bench,squat,deadlift,press,curl,extension,latpull,carf,start,ready,stop);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public String getTitle() {
        if(Main.getisIsoKinetic())
            return "등속성 운동 모드";
        else
            return "등장성 운동 모드";
    }

    @Override
    public int isHomeVisible() {
        return  View.VISIBLE;
    }

    @Override
    public DCfragment getBackFragment()
    {
        return new SelectMode(main);
    }

    @Override
    public DCfragment getNextFragment()
    {
        return null;
    }
}


class spinnerAdapter extends BaseAdapter
{
    Context context;
    List<String> data;
    LayoutInflater inflater;


    public spinnerAdapter(Context context, List<String> data){
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        if(data!=null) return data.size();
        else return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = inflater.inflate(R.layout.spinner_normal, parent, false);
        }

        if(data!=null){
            //데이터세팅
            String text = data.get(position);
            ((TextView)convertView.findViewById(R.id.spinnerText)).setText(text);
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(R.layout.spinner_drop_down, parent, false);
        }
        //데이터세팅
        String text = data.get(position);
        ((TextView)convertView.findViewById(R.id.spinnerText)).setText(text);


        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}