package com.example.dynamiccare_kisok.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.R;

public class DetailResult extends DCfragment {
    TextView uppertitle,lowertitle;
    ImageView body;

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
        View view = inflater.inflate(R.layout.fragment_result_detail,container, false);
        uppertitle = view.findViewById(R.id.txt_detail_title);
        lowertitle = view.findViewById(R.id.txt_detail_cont_title);
        body = view.findViewById(R.id.detail_body);

        uppertitle.setText("측정 결과 확인");
        lowertitle.setText(main.getCurrentExcercise().getSimpleName()+" "+main.getCurrentExcercise().getMuscleName());
        body.setImageDrawable(main.getCurrentExcercise().getMappingBody());


        return view;
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
}
