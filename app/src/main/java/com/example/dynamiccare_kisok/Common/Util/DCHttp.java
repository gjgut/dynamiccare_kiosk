package com.example.dynamiccare_kisok.Common.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.dynamiccare_kisok.Common.Object.Workout;

import org.json.JSONObject;

public class DCHttp {
    NetworkTask task;

    public static final String WIFI_STATE = "WIFE";
    public static final String MOBILE_STATE = "MOBILE";
    public static final String NONE_STATE = "NONE";


    public static String getWhatKindOfNetwork(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return WIFI_STATE;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return MOBILE_STATE;
            }
        }
        return NONE_STATE;
    }

    public DCHttp() {
        this.task = new NetworkTask();
    }

    public JSONObject Login(String uid)
    {
        try {
            return task.execute("http://www.powerlogmobile.com/kiosk/uidlogin",uid).get();
        }catch (Exception e){
            return  null;
        }
    }
    public JSONObject SendWorkout(String workout)
    {
        try {
            return task.execute("http://www.powerlogmobile.com/kiosk/save/workout",workout).get();
        }catch (Exception e){
            return  null;
        }
    }
    public JSONObject SendResult(String uid)
    {
        try {
            return task.execute("http://www.powerlogmobile.com/kiosk/save/measure",uid).get();
        }catch (Exception e){
            return  null;
        }
    }
}
