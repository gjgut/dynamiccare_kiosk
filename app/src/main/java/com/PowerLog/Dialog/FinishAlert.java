package com.PowerLog.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.PowerLog.Activity.Main;
import com.PowerLog.Fragment.TimeSetting;
import com.PowerLog.R;


public class FinishAlert extends Dialog implements View.OnClickListener {
    ImageButton yes, no;
    Main main;

    public FinishAlert(Main main) {
        super(main, android.R.style.Theme_Translucent_NoTitleBar);
        this.main = main;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_yes)
            main.ReplaceFragment(new TimeSetting(main, main.getCurrentFragment()), true);
        dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.3f;
        getWindow().setAttributes(layoutParams);

            setContentView(R.layout.kiosk_dialog_warning_for_finish);

        yes = findViewById(R.id.btn_yes);
        no = findViewById(R.id.btn_no);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

}
