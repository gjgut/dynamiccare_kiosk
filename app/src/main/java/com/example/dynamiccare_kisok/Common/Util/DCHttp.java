package com.example.dynamiccare_kisok.Common.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.dynamiccare_kisok.Common.Object.Workout;
import com.example.dynamiccare_kisok.Common.Util.AsyncTask.LoginTask;
import com.example.dynamiccare_kisok.Common.Util.AsyncTask.SendMeasureResultTask;
import com.example.dynamiccare_kisok.Common.Util.AsyncTask.SendWorkoutTask;

import org.json.JSONObject;

public class DCHttp {
    LoginTask loginTask;
    SendWorkoutTask sendWorkoutTask;
    SendMeasureResultTask sendMeasureResultTask;

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
        this.loginTask = new LoginTask();
        this.sendWorkoutTask = new SendWorkoutTask();
        this.sendMeasureResultTask = new SendMeasureResultTask();
    }

    public JSONObject Login(String uid)
    {
        try {
            return loginTask.execute(uid).get();
        }catch (Exception e){
            return  null;
        }
    }
    public JSONObject SendWorkout(Workout workout)
    {
        try {
            return sendWorkoutTask.execute("").get();
        }catch (Exception e){
            return  null;
        }
    }
    public JSONObject SendResult(String uid)
    {
        try {
            return sendMeasureResultTask.execute(uid).get();
        }catch (Exception e){
            return  null;
        }
    }
}
