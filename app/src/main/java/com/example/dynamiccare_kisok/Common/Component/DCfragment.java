package com.example.dynamiccare_kisok.Common.Component;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Object.ACK;

public abstract class DCfragment extends Fragment implements View.OnClickListener{
    protected  Main main;
    public DCfragment(){}
    public DCfragment(Main main)
    {
        this.main = main;
    }
    public abstract DCfragment getBackFragment();
    public abstract DCfragment getNextFragment();
    public abstract String getTitle();
    public abstract int isHomeVisible();
    public void HandleACK(ACK ack)
    {
        switch (ack.getData())
        {

        }
    }
}
