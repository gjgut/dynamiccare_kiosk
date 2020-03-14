package com.example.dynamiccare_kisok.Common.Component;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.dynamiccare_kisok.Activity.Administrator;
import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.DynamicCare;
import com.example.dynamiccare_kisok.Fragment.Administrator.Authentification;
import com.example.dynamiccare_kisok.Common.Object.ACK;

public abstract class DCfragment extends Fragment implements View.OnClickListener{
    protected  Main main;
    protected DynamicCare care;
    protected Administrator admin;
    protected CountDownTimer timer;
    public DCfragment(){}
    public DCfragment(Main main)
    {
        this.main = main;
        care = (DynamicCare)main.getApplication();
    }
    public DCfragment(Administrator admin){
        this.admin = admin;
        care = (DynamicCare)admin.getApplication();
    }
    public abstract DCfragment getBackFragment();
    public abstract DCfragment getNextFragment();
    public abstract String getTitle();
    public abstract int isHomeVisible();

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(timer !=null)
        {
            timer.cancel();
        }
    }
    public void HandleACK(ACK ack)
    {
        switch (ack.getData())
        {

        }
    }
}
