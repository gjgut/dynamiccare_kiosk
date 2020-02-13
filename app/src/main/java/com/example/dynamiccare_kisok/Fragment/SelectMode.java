package com.example.dynamiccare_kisok.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dynamiccare_kisok.Common.Component.DCButton;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.R;

public class SelectMode extends DCfragment {
    ImageButton selectExec,isokinetic,isometronic,isotonic;


    public SelectMode(Main main)
    {
        super(main);
        Main.setCurrentExcercise(null);
        DCButton.PressedOff();
    }

    @Override
    public void onClick(View v)
    {
        try {

            switch (v.getId()) {
                case R.id.btn_select_exec: {
                    Main.setisIsoKinetic(false);
                    ((Main) getActivity()).ReplaceFragment(new ExcerciseMode(main), true);
                    break;
                }
                case R.id.btn_select_exec_isokinetic: {
                    Main.setisIsoKinetic(true);
                    ((Main) getActivity()).ReplaceFragment(new ExcerciseMode(main), true);
                    break;
                }
                case R.id.btn_sel_mes_isometric: {
                    Main.setIsIsoTonic(false);
                    ((Main) getActivity()).ReplaceFragment(new Explain(main), true);
                    break;
                }
                case R.id.btn_sel_mes_isotonic: {
                    Main.setIsIsoTonic(true);
                    ((Main) getActivity()).ReplaceFragment(new Explain(main), true);
                    break;
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(main, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_mode,container, false);
        selectExec = view.findViewById(R.id.btn_select_exec);
        isokinetic = view.findViewById(R.id.btn_select_exec_isokinetic);
        isometronic = view.findViewById(R.id.btn_sel_mes_isometric);
        isotonic = view.findViewById(R.id.btn_sel_mes_isotonic);

        selectExec.setOnClickListener(this);
        isokinetic.setOnClickListener(this);
        isometronic.setOnClickListener(this);
        isotonic.setOnClickListener(this);

        return view;
    }

    @Override
    public String getTitle() {
        return "모드 선택";
    }

    @Override
    public int isHomeVisible() {
        return View.INVISIBLE;
    }

    @Override
    public DCfragment getBackFragment()
    {
        return null;
    }

    @Override
    public DCfragment getNextFragment()
    {
        return null;
    }
}
