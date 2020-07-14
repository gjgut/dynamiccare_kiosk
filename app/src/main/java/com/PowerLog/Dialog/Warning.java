package com.PowerLog.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.PowerLog.R;

public class Warning extends Dialog {
    Context context;
    Button yes;
    String msg;
    TextView txt_messsage;
    boolean isNormal=true;

    public Warning(@NonNull Context context, String msg) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.msg = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.3f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.kiosk_dialog_limit_alert);


        yes = findViewById(R.id.btn_yes);
        txt_messsage = findViewById(R.id.txt_message);
        txt_messsage.setText(msg);

        yes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dismiss();
            }
        });
    }

}
