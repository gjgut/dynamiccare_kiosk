package com.example.dynamiccare_kisok.Common.Util;

import com.example.dynamiccare_kisok.Common.Object.Workout;
import com.example.dynamiccare_kisok.Common.Util.AsyncTask.LoginTask;
import com.example.dynamiccare_kisok.Common.Util.AsyncTask.SendMeasureResultTask;
import com.example.dynamiccare_kisok.Common.Util.AsyncTask.SendWorkoutTask;

import org.json.JSONObject;

public class DCHttp {
    LoginTask loginTask;
    SendWorkoutTask sendWorkoutTask;
    SendMeasureResultTask sendMeasureResultTask;

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
