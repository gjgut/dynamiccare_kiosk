package com.example.dynamiccare_kisok.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.example.dynamiccare_kisok.R;

public class LoadPlan extends Dialog {
    ImageButton yes,no;
    View.OnClickListener yeslistner,nolistener;

    public LoadPlan(@NonNull Context context, View.OnClickListener yes, View.OnClickListener no) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.yeslistner = yes;
        this.nolistener = no;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.dialog_isload_workout_plan);

        yes = findViewById(R.id.btn_yes);
        no = findViewById(R.id.btn_no);

        yes.setOnClickListener(yeslistner);
        no.setOnClickListener(nolistener);
    }

}
