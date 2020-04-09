package com.PowerLog.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.PowerLog.Common.DynamicCare;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            DynamicCare care = (DynamicCare) getApplicationContext();
            care.initSounds();
            Handler hd = new Handler();
            hd.postDelayed(new splashhandler(), 1000); // 1초 후에 hd handler 실행  3000ms = 3초
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }


    }

    private class splashhandler implements Runnable {
        public void run() {
            startActivity(new Intent(getApplication(), Login.class)); //로딩이 끝난 후, ChoiceFunction 이동
            Splash.this.finish(); // 로딩페이지 Activity stack에서 제거
        }
    }

    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }


}
