package com.example.dynamiccare_kisok.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dynamiccare_kisok.Activity.Login;
import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.R;


public class ExcerciseFinished extends Dialog {
    Main main;
    Button yes;

    public ExcerciseFinished(@NonNull Context context, View.OnClickListener yes, View.OnClickListener no) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.main = (Main)context;
        main = (Main)context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.3f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.dialog_limit_alert);


        yes = findViewById(R.id.btn_yes);

        yes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(main, Login.class);
                main.startActivity(intent);
                main.overridePendingTransition(R.anim.left_in, R.anim.right_out);
                main.finish();
                dismiss();
            }
        });
    }

}
