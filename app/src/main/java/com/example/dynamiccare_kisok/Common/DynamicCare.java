package com.example.dynamiccare_kisok.Common;

import android.app.Application;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Util.DCSoundPlayer;

public class DynamicCare extends Application {
    public  static DCSoundPlayer dcSoundPlayer;
    public static String CurrentUser;

    public static String getCurrentUser() {
        return CurrentUser;
    }

    public static void setCurrentUser(String currentUser) {
        CurrentUser = currentUser;
    }

    public DCSoundPlayer getDcSoundPlayer() {
        return dcSoundPlayer;
    }

    public void setDcSoundPlayer(DCSoundPlayer dcSoundPlayer) {
        DynamicCare.dcSoundPlayer = dcSoundPlayer;
    }

    public void initSounds()
    {
        dcSoundPlayer.initSounds(getApplicationContext());
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        dcSoundPlayer = new DCSoundPlayer();
    }
}
