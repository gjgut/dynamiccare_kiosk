package com.example.dynamiccare_kisok.Common.Util;

import android.os.CountDownTimer;
import android.widget.TextView;

class DCTimer extends CountDownTimer
{
    TextView label;
    public DCTimer(long millisInFuture, long countDownInterval,TextView label)
    {
        super(millisInFuture, countDownInterval);
        this.label = label;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        label.setText(String.valueOf(millisUntilFinished/1000));
    }

    @Override
    public void onFinish() {
        label.setText("0");
    }
}
