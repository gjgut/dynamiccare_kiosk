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


    public GraphResult(Main main)
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
        View view = inflater.inflate(R.layout.fragment_result_graph,container, false);
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
        return new Instruction(main);
    }

    @Override
    public DCfragment getNextFragment()
    {
        return new DetailResult(main);
    }
}
