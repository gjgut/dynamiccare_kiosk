package com.example.dynamiccare_kisok.Common;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Util.DCSoundPlayer;

import org.json.JSONObject;

public class DynamicCare extends Application {
    public  static DCSoundPlayer dcSoundPlayer;
    public static String CurrentUser;
    public static JSONObject CurrentUserJson;
    public static int limit=600;
    public SharedPreferences Admin;

    public String getAdminPassword() {
        return Admin.getString("password","");
    }

    public void setAdminPassword(String password) {
        SharedPreferences.Editor editor = Admin.edit();
        editor.putString("password",password);

        editor.commit();
    }

    public static int getLimit() {
        return limit;
    }

    public static void setLimit(int limit) {
        DynamicCare.limit = limit;
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
//        setAdminPassword("0000");
    }
}
