package com.PowerLog.Fragment.Administrator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.PowerLog.Activity.Administrator;
import com.PowerLog.Common.Component.DCEditText;
import com.PowerLog.Common.Component.DCfragment;
import com.PowerLog.Common.DynamicCare;
import com.PowerLog.Common.Object.ACK;
import com.PowerLog.R;

public class LimitOff extends DCfragment {
    DCEditText edt_adminpw;
    TextView reject, title;
    Button limit;
    DynamicCare care;

    public LimitOff() {
        super();
    }

    public LimitOff(Administrator admin) {
        super(admin);
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btn_limit_deactivate:
                    admin.PlaySound(R.raw.normal_button);
                    if (edt_adminpw.getSource().getText().toString().equals(care.getAdminPassword().toString())) {
                        if (care.isLimit())
                            care.offLimit();
                        else
                            care.onLimit();
                        admin.ReplaceFragment(new Authentification(admin), false);
                    } else
                        reject.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        if (care.IsKiosk())
            view = inflater.inflate(R.layout.kiosk_fragment_auth_limit_off, container, false);
        else
            view = inflater.inflate(R.layout.fragment_auth_limit_off, container, false);
        super.onCreate(savedInstanceState);
        care = (DynamicCare) admin.getApplication();

        try {
            edt_adminpw = new DCEditText(view.findViewById(R.id.et_adminpw));
            limit = view.findViewById(R.id.btn_limit_deactivate);

            limit.setOnClickListener(this);

            reject = view.findViewById(R.id.password_reject);
            reject.setVisibility(View.INVISIBLE);

            title = view.findViewById(R.id.txt_ins_title);

            if (care.isLimit()) {
                title.setText("시간 제한 해제");
                limit.setText("해제");
            } else {
                title.setText("시간 제한");
                limit.setText("확인");
            }

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
    public int isHomeVisible() {
        return 0;
    }

    @Override
    public void HandleACK(ACK ack) {
        super.HandleACK(ack);
    }


}
