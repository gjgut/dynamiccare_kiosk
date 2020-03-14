package com.example.dynamiccare_kisok.Common;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.Util.DCSoundPlayer;

import org.json.JSONObject;

public class DynamicCare extends Application {
    public  static DCSoundPlayer dcSoundPlayer;
    public static String CurrentUser,DeviceID;
    public static JSONObject CurrentUserJson;
    public static int limit=60;
    public static boolean isLimit=true;
    public static DCfragment tempFragment;
    public SharedPreferences Admin;

    public static DCfragment getTempFragment() {
        return tempFragment;
    }

    public static void setTempFragment(DCfragment tempFragment) {
        DynamicCare.tempFragment = tempFragment;
    }

    public String getAdminPassword() {
        return Admin.getString("password","");
    }
    public String getMeasureTime(){return Admin.getString("mTime","");}
    public String getMeasureWeight(){return Admin.getString("mWeight","");}

    public static String getDeviceID() {
        return DeviceID;
    }

    public static void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public void setAdminPassword(String password) {
        SharedPreferences.Editor editor = Admin.edit();
        editor.putString("password",password);

        editor.commit();
    }
    public void setMeasureTime(String time) {
        SharedPreferences.Editor editor = Admin.edit();
        editor.putString("mTime",time);

        editor.commit();
    }
    public void setMeasureWeight(String weight) {
        SharedPreferences.Editor editor = Admin.edit();
        editor.putString("mWeight",weight);

        editor.commit();
    }

    public static int getLimit() {
        return limit;
    }

    public static void setLimit(int limit) {
        DynamicCare.limit = limit;
    }
    public static void offLimit(){isLimit = false;}

    public static boolean isLimit()
    {
        return isLimit;
    }

    public static JSONObject getCurrentUserJson() {
        return CurrentUserJson;
    }

    public static void setCurrentUserJson(JSONObject currentUserJson) {
        CurrentUserJson = currentUserJson;
    }

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

    public void initSounds() {
        dcSoundPlayer.initSounds(getApplicationContext());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dcSoundPlayer = new DCSoundPlayer();
        Admin = getSharedPreferences("password",MODE_PRIVATE);
        setAdminPassword("0000");
    }
}
