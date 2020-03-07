package com.example.dynamiccare_kisok.Fragment.Administrator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.dynamiccare_kisok.Activity.Administrator;
import com.example.dynamiccare_kisok.Activity.Login;
import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCEditText;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.Object.ACK;
import com.example.dynamiccare_kisok.R;

public class Authentification extends DCfragment {

    ImageButton limitoff,modify;
    Intent intent;


    public Authentification() {
        super();
    }

    public Authentification(Administrator admin) {
        super(admin);
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
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_limit_off:
                admin.PlaySound(R.raw.normal_button);
                admin.ReplaceFragment(new LimitOff(admin),true);
                break;
            case R.id.btn_modify_psswd:
                admin.PlaySound(R.raw.normal_button);
                admin.ReplaceFragment(new ModifyAdmin(admin),true);
                break;
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_authentification,container, false);
        super.onCreate(savedInstanceState);
        try{
            limitoff = view.findViewById(R.id.btn_limit_off);
            modify = view.findViewById(R.id.btn_modify_psswd);

            limitoff.setOnClickListener(this);
            modify.setOnClickListener(this);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return view;
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
