package com.example.dynamiccare_kisok.Fragment.Administrator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dynamiccare_kisok.Activity.Administrator;
import com.example.dynamiccare_kisok.Activity.Login;
import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCEditText;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.DynamicCare;
import com.example.dynamiccare_kisok.Common.Object.ACK;
import com.example.dynamiccare_kisok.Fragment.Administrator.Authentification;
import com.example.dynamiccare_kisok.R;

public class ModifyAdmin extends DCfragment {

    ImageButton back, newpwvisible;
    Button ok;
    DCEditText prev, New, correct;
    TextView reject;
    boolean isVisible;

    public ModifyAdmin() {
        super();
    }

    public ModifyAdmin(Administrator admin) {
        super(admin);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_set_admin_ok:
                DynamicCare care = (DynamicCare) admin.getApplication();
                if (prev.getSource().getText().toString().equals(care.getAdminPassword())) {
                    care.setAdminPassword(New.getSource().getText().toString());
                    if (New.getSource().getText().toString().equals(correct.getSource().getText().toString()))
                        admin.ReplaceFragment(new Authentification(admin), false);
                }
                else if(!New.getSource().getText().toString().equals(correct.getSource().getText().toString()))
                {
                    reject.setVisibility(View.VISIBLE);
                    reject.setText("새로운 비밀번호를 다시 입력해주십시오.");
                }
                else if(!prev.getSource().getText().toString().equals(care.getAdminPassword()))
                {
                    reject.setVisibility(View.VISIBLE);
                    reject.setText("관리자 비밀번호가 틀립니다.");
                }
                break;
            case R.id.btn_newpw_visible:
                if (isVisible) {
                    newpwvisible.setImageDrawable(admin.getDrawable(R.drawable.ic_visibility_off));
                    newpwvisible.setPadding(30, 30, 30, 30);
                    New.getSource().setTransformationMethod(PasswordTransformationMethod.getInstance());
                    New.getSource().setSelection(New.getSource().length());
                    isVisible = false;
                } else {
                    newpwvisible.setImageDrawable(admin.getDrawable(R.drawable.ic_visibility_on));
                    newpwvisible.setPadding(40, 35, 31, 38);
                    New.getSource().setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    New.getSource().setSelection(New.getSource().length());
                    isVisible = true;
                }
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_set_admin, container, false);
        super.onCreate(savedInstanceState);
        try {

            back = view.findViewById(R.id.btn_back);
            newpwvisible = view.findViewById(R.id.btn_newpw_visible);
            ok = view.findViewById(R.id.btn_set_admin_ok);

            prev = new DCEditText(view.findViewById(R.id.et_prevpw));
            New = new DCEditText(view.findViewById(R.id.et_newpw));
            correct = new DCEditText(view.findViewById(R.id.et_pwconfirm));


            reject = view.findViewById(R.id.password_reject);
            reject.setVisibility(View.INVISIBLE);

            prev.getSource().addTextChangedListener(new TextWatcher() {
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
            New.getSource().addTextChangedListener(new TextWatcher() {
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
            correct.getSource().addTextChangedListener(new TextWatcher() {
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

            ok.setOnClickListener(this);
            newpwvisible.setOnClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

}
