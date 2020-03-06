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
import com.example.dynamiccare_kisok.Common.Excercise.Excercise;
import com.example.dynamiccare_kisok.Fragment.Administrator.TimeSetting;
import com.example.dynamiccare_kisok.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FinishAlert extends Dialog {
    ImageButton yes,no;
    Main main;
    View.OnClickListener yeslistner,nolistener;
    TextView txt_today;

    public FinishAlert(@NonNull Context context, View.OnClickListener yes, View.OnClickListener no) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.main = (Main)context;
        this.yeslistner = yes;
        this.nolistener = no;

    }

    public FinishAlert(Main main)
    {
        super(main);
        this.main = main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.3f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.dialog_excercise_finished);


        yes = findViewById(R.id.btn_yes);
        no = findViewById(R.id.btn_no);

        yes.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                main.ReplaceFragment(new TimeSetting(main), true);
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
