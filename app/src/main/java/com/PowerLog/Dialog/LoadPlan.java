package com.PowerLog.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.PowerLog.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LoadPlan extends Dialog {
    ImageButton yes,no;
    View.OnClickListener yeslistner,nolistener;
    TextView txt_today;

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
        layoutParams.dimAmount = 0.3f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.dialog_isload_workout_plan);

        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(currentTime);

        txt_today = findViewById(R.id.txt_today);
        txt_today.setText(date_text);

        yes = findViewById(R.id.btn_yes);
        no = findViewById(R.id.btn_no);

        yes.setOnClickListener(yeslistner);
        no.setOnClickListener(nolistener);
    }

}
