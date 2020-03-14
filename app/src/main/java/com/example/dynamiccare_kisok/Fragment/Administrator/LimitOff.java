package com.example.dynamiccare_kisok.Fragment.Administrator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dynamiccare_kisok.Activity.Administrator;
import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCEditText;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.DynamicCare;
import com.example.dynamiccare_kisok.Common.Object.ACK;
import com.example.dynamiccare_kisok.R;

public class LimitOff extends DCfragment {
    DCEditText edt_adminpw;
    TextView reject;
    Button limit_off;
    DynamicCare care;

    public LimitOff() {
        super();
    }

    public LimitOff(Administrator admin) {
        super(admin);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_limit_deactivate:
                admin.PlaySound(R.raw.normal_button);
                if (edt_adminpw.getSource().getText().toString().equals(care.getAdminPassword().toString()))
                {
                    care.offLimit();
                    admin.ReplaceFragment(new Authentification(admin), false);
                }
                else
                    reject.setVisibility(View.VISIBLE);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_limit_off, container, false);
        super.onCreate(savedInstanceState);
        care = (DynamicCare) admin.getApplication();

        try {
            edt_adminpw = new DCEditText(view.findViewById(R.id.et_adminpw));
            limit_off = view.findViewById(R.id.btn_limit_deactivate);

            limit_off.setOnClickListener(this);

            reject = view.findViewById(R.id.password_reject);
            reject.setVisibility(View.INVISIBLE);

            edt_adminpw.getSource().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    reject.setVisibility(View.INVISIBLE);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public DCfragment getBackFragment() {
        return new Authentification(admin);
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
