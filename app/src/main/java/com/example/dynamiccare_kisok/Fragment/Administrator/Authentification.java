package com.example.dynamiccare_kisok.Fragment.Administrator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.dynamiccare_kisok.Activity.Administrator;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.R;

public class Authentification extends DCfragment {

    ImageButton limit, modify;

    public Authentification() {
        super();
    }

    public Authentification(Administrator admin) {
        super(admin);
    }

    @Override
    public void onClick(View v) {
        try {
            admin.PlaySound(R.raw.normal_button);
            switch (v.getId()) {
                case R.id.btn_limit:
                    admin.ReplaceFragment(new LimitOff(admin), true);
                    break;
                case R.id.btn_modify_psswd:
                    admin.ReplaceFragment(new ModifyAdmin(admin), true);
                    break;
            }
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_authentification, container, false);
        super.onCreate(savedInstanceState);
        try {
            limit = view.findViewById(R.id.btn_limit);
            modify = view.findViewById(R.id.btn_modify_psswd);

            if (care.isLimit())
                limit.setImageDrawable(getResources().getDrawable(R.drawable.btn_limit_off));
            else
                limit.setImageDrawable(getResources().getDrawable(R.drawable.btn_limit_on));

            limit.setOnClickListener(this);
            modify.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }


    @Override
    public int isHomeVisible() {
        return 0;
    }


}
