package com.example.dynamiccare_kisok.Test.Runnable;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Excercise.Excercise;
import com.example.dynamiccare_kisok.Common.Object.ACK;
import com.example.dynamiccare_kisok.Common.Util.ACKListener;

public class ExcerciseReady1 implements Runnable {
    public Main main;
    public ExcerciseReady1(Main main)
    {
     this.main = main;
    }
    @Override
    public void run()
    {
        try {
            main.HandleACK(ACKListener.ParseACK("$AET1#"));
            Thread.sleep(5000);
            main.HandleACK(ACKListener.ParseACK("$ACS05#"));
            Thread.sleep(1500);
            main.HandleACK(ACKListener.ParseACK("$ACS04#"));
            Thread.sleep(1500);
            main.HandleACK(ACKListener.ParseACK("$ACS03#"));
            Thread.sleep(1500);
            main.HandleACK(ACKListener.ParseACK("$ACS02#"));
            Thread.sleep(1500);
            main.HandleACK(ACKListener.ParseACK("$ACS01#"));
            Thread.sleep(1500);
            main.HandleACK(ACKListener.ParseACK("$ACB3#"));
            Thread.sleep(1500);
            main.HandleACK(ACKListener.ParseACK("$AET2#"));
            Thread.sleep(5000);
            main.HandleACK(ACKListener.ParseACK("$ACS05#"));
            Thread.sleep(1500);
            main.HandleACK(ACKListener.ParseACK("$ACS04#"));
            Thread.sleep(1500);
            main.HandleACK(ACKListener.ParseACK("$ACS03#"));
            Thread.sleep(1500);
            main.HandleACK(ACKListener.ParseACK("$ACS02#"));
            Thread.sleep(1500);
            main.HandleACK(ACKListener.ParseACK("$ACS01#"));
            Thread.sleep(1500);
            main.HandleACK(ACKListener.ParseACK("$ACB3#"));


        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
