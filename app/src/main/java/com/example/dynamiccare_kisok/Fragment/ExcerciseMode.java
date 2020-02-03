package com.example.dynamiccare_kisok.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCButton;
import com.example.dynamiccare_kisok.Common.Component.DCEditText;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.R;

import java.util.ArrayList;
import java.util.List;

public class ExcerciseMode extends DCfragment{

    DCButton bench,squat,deadlift,press,curl,extension,latpull,carf,start,stop,ready;
    DCEditText edt_rest,edt_weight,edt_set,edt_count;
    TextView txt_count,txt_set;
    ImageView Body;
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
        switch (v.getId())
        {
            case R.id.exc_tab_btn_bench:
            {
                bench.setPressed();
                break;
            }
            case R.id.exc_tab_btn_squat:
            {
                squat.setPressed();
                break;
            }
            case R.id.exc_tab_btn_deadlift:
            {
                deadlift.setPressed();
                break;
            }
            case R.id.exc_tab_btn_shoulderpress:
            {
                press.setPressed();
                break;
            }
            case R.id.exc_tab_btn_latpulldown:
            {
                latpull.setPressed();
                break;
            }
            case R.id.exc_tab_btn_carfraise:
            {
                carf.setPressed();
                break;
            }
            case R.id.exc_tab_btn_armcurl:
            {
                curl.setPressed();
                break;
            }
            case R.id.exc_tab_btn_armextension:
            {
                extension.setPressed();
                break;
            }
            case R.id.exc_btn_start:
            {
                start.setPressed();
                break;
            }
            case R.id.exc_btn_stop:
            {
                stop.setPressed();
                break;
            }
            case R.id.exc_btn_ready:
            {
                ready.setPressed();
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

            bench = new DCButton();
            squat = new DCButton();
            deadlift = new DCButton();
            press = new DCButton();
            latpull = new DCButton();
            carf = new DCButton();
            curl = new DCButton();
            extension = new DCButton();
            start = new DCButton();
            stop = new DCButton();
            ready = new DCButton();
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

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public String getTitle() {
        return "운동 모드";
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
        ((Button)convertView.findViewById(R.id.spinnerbutton)).setText(text);
        ((Button)convertView.findViewById(R.id.spinnerbutton)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                v.getBackground().setColorFilter(Color.parseColor("#33ffffff"),
                        PorterDuff.Mode.SRC_ATOP);
            }
        });

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