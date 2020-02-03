package com.example.dynamiccare_kisok.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.R;

public class Instruction extends DCfragment {
    TextView title,content;

    public Instruction(Main main)
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
        View view = inflater.inflate(R.layout.fragment_instruction,container, false);

        title = view.findViewById(R.id.txt_ins_title);
        content = view.findViewById(R.id.txt_ins_content);

        title.setText(Main.getCurrentExcercise().getSimpleName());
        content.setText(Main.getCurrentExcercise().getInstruction());

        return view;
    }

    @Override
    public String getTitle() {
        return "등장성 측정 모드";
    }

    @Override
    public int isHomeVisible() {
        return View.VISIBLE;
    }

    @Override
    public DCfragment getBackFragment()
    {
        return new Explain(main);
    }

    @Override
    public DCfragment getNextFragment()
    {
        return new GraphResult(main);
    }
}
