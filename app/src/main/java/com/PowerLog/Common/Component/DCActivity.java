package com.PowerLog.Common.Component;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.PowerLog.Activity.Login;
import com.PowerLog.Common.DynamicCare;
import com.PowerLog.R;

public abstract class DCActivity extends AppCompatActivity {
    protected DynamicCare care;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        care = (DynamicCare) getApplication();
    }

    protected void ViewMapping(){}
    protected void setListener(){}

    public void ChangeActivity(Class name) {
        startActivity(new Intent(getApplicationContext(), name));
        if (name != Login.class)
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        else
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }


}
