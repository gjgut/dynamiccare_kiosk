package com.PowerLog.Common;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.PowerLog.Common.Component.DCfragment;
import com.PowerLog.Common.Util.DCHttp;
import com.PowerLog.Common.Util.DCSoundPlayer;

import org.json.JSONObject;

public class DynamicCare extends Application {
    public static DCSoundPlayer dcSoundPlayer;
    public static String CurrentUser, DeviceID="000000000000000";
    public static JSONObject CurrentUserJson;
    public static String UserId;
    public static int limit = 0;
    public static boolean isLimit = true;
    public static DCfragment tempFragment;
    public SharedPreferences Admin;
    public static boolean isKiosk=false;
    private Thread.UncaughtExceptionHandler androidDefaultUEH;
    private UnCaughtExceptionhan unCaughtExceptionhan;

    public UnCaughtExceptionhan getUnCaughtExceptionhan(){
        return unCaughtExceptionhan;
    }


    public static boolean IsKiosk() {
        return isKiosk;
    }

    public void setIsKiosk() {
        DynamicCare.isKiosk = checkTabletDeviceWithProperties(getApplicationContext());
    }

    public static DCfragment getTempFragment() {
        return tempFragment;
    }

    public static void setTempFragment(DCfragment tempFragment) {
        DynamicCare.tempFragment = tempFragment;
    }

    public static String getUserId() {
        return UserId;
    }

    public static void setUserId(String userId) {
        UserId = userId;
    }

    public String getAdminPassword() {
        return Admin.getString("password", "");
    }

    public String getMeasureTime() {
        return "300";
    }

    public String getMeasureWeight() {
        return "10";
    }

    public static String getDeviceID() {
        return DeviceID;
    }

    public static void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public void setAdminPassword(String password) {
        SharedPreferences.Editor editor = Admin.edit();
        editor.putString("password", password);

        editor.commit();
    }

    public void setMeasureTime(String time) {
        SharedPreferences.Editor editor = Admin.edit();
        editor.putString("mTime", time);

        editor.commit();
    }

    public void setMeasureWeight(String weight) {
        SharedPreferences.Editor editor = Admin.edit();
        editor.putString("mWeight", weight);

        editor.commit();
    }

    public boolean isTherePlan()
    {
        try {
            JSONObject resultData = (JSONObject) getCurrentUserJson().get("resultData");
            if (resultData.toString().contains("\"plnVwIsDone\":\"false\"") && (!resultData.get("programList").toString().equals("null") || !resultData.get("privateList").toString().equals("null"))) {
                return true;
            } else
                return false;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static int getLimit() {
        return limit;
    }

    public static void setLimit(int limits) {
        limit = limits;
    }

    public void offLimit() {
        SharedPreferences.Editor editor = Admin.edit();
        editor.putBoolean("isLimit", false);

        editor.commit();
    }

    public void onLimit() {
        SharedPreferences.Editor editor = Admin.edit();
        editor.putBoolean("isLimit", true);

        editor.commit();
    }

    public boolean isLimit() {
        return Admin.getBoolean("isLimit", true);
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

    public void UpdateJson() {
        try {
            CurrentUserJson = new DCHttp().Login(new JSONObject().accumulate("uid", getUserId()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkTabletDeviceWithProperties(Context context)
    {
        return context.getResources().getConfiguration().smallestScreenWidthDp >= 600;

    }

    public class UnCaughtExceptionhan implements Thread.UncaughtExceptionHandler
    {
        @Override
        public void uncaughtException(Thread thread,Throwable ex)
        {
            Log.i("ERRORRRRRR",ex.toString());
            Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreate() {
        androidDefaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        unCaughtExceptionhan = new UnCaughtExceptionhan();

        Thread.setDefaultUncaughtExceptionHandler(unCaughtExceptionhan);
        super.onCreate();

        dcSoundPlayer = new DCSoundPlayer();
        Admin = getSharedPreferences("password", MODE_PRIVATE);
        if(getAdminPassword()=="")
            setAdminPassword("0000");
        setIsKiosk();
    }
}