package com.example.dynamiccare_kisok.Test.Runnable;

import android.os.Handler;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Util.ACKListener;


public class ExcerciseStart implements Runnable {
    public Main main;
    public ExcerciseStart(Main main)
    {
        this.main = main;
    }
    @Override
    public void run() {
        try {
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        main.HandleACK(ACKListener.ParseACK("$ACB1#"));
                        Thread.sleep(5000);
                        main.HandleACK(ACKListener.ParseACK("$ACD03030#"));
                        main.HandleACK(ACKListener.ParseACK("$ACB2#"));
                        Thread.sleep(1500);
                        main.HandleACK(ACKListener.ParseACK("$ACS03#"));
                        main.HandleACK(ACKListener.ParseACK("$ACD02030#"));
                        Thread.sleep(1500);
                        main.HandleACK(ACKListener.ParseACK("$ACB2#"));
                        Thread.sleep(1500);
                        main.HandleACK(ACKListener.ParseACK("$ACS02#"));
                        main.HandleACK(ACKListener.ParseACK("$ACD01030#"));
                        Thread.sleep(1500);
                        main.HandleACK(ACKListener.ParseACK("$ACB2#"));
                        main.HandleACK(ACKListener.ParseACK("$ACD00030#"));
                        Thread.sleep(1500);
                        main.HandleACK(ACKListener.ParseACK("$ACS01#"));
                        Thread.sleep(1500);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });


        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
