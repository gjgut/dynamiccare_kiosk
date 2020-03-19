package com.example.dynamiccare_kisok.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Fragment.TimeSetting;
import com.example.dynamiccare_kisok.R;


public class FinishAlert extends Dialog {
    ImageButton yes,no;
    Main main;

    public FinishAlert(Main main)
    {
        super(main, android.R.style.Theme_Translucent_NoTitleBar);
        this.main = main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.3f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.dialog_warning_for_finish);


        yes = findViewById(R.id.btn_yes);
        no = findViewById(R.id.btn_no);

        yes.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                main.ReplaceFragment(new TimeSetting(main,main.getCurrentFragment()), true);
                dismiss();
            }
        });
        no.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
