package com.example.dynamiccare_kisok.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.dynamiccare_kisok.Common.Component.DCActivity;
import com.example.dynamiccare_kisok.R;

public class QRlink extends DCActivity {

    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_etc_qr_link);
            btn_back = findViewById(R.id.btn_back);
            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChangeActivity(Login.class);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
