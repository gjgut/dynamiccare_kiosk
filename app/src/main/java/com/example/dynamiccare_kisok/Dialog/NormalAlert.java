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

public class NormalAlert extends Dialog {
    Main main;
    Button yes;
    String msg;
    TextView txt_messsage;

    public NormalAlert(@NonNull Context context, View.OnClickListener yes, View.OnClickListener no,String msg) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.main = (Main)context;
        main = (Main)context;
        this.msg = msg;
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
        txt_messsage = findViewById(R.id.txt_message);
        txt_messsage.setText(msg);

        yes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dismiss();
                Intent intent = new Intent(main, Login.class);
                main.startActivity(intent);
                main.overridePendingTransition(R.anim.left_in, R.anim.right_out);
                main.finish();
            }
        });
    }

}
