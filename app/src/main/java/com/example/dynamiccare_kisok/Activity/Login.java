package com.example.dynamiccare_kisok.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.dynamiccare_kisok.Common.Component.DCEditText;
import com.example.dynamiccare_kisok.Fragment.SelectMode;
import com.example.dynamiccare_kisok.R;

import static android.view.HapticFeedbackConstants.LONG_PRESS;

public class Login extends AppCompatActivity {

    Button btn_download, btn_login;
    DCEditText edt_code;
    ImageView dclogo;
    VideoView videoView;
    Handler mHandler;
    CountDownTimer mcountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login2);
            dclogo = findViewById(R.id.dc_logo);
            btn_download = findViewById(R.id.bt_dwload);
            btn_login = findViewById(R.id.bt_login);
            edt_code = new DCEditText(findViewById(R.id.et_code));

            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case LONG_PRESS:
                    }
                }
            };

            videoView = findViewById(R.id.video_instruct);

            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dynamic_care_logo));
            videoView.setMediaController(new MediaController(this));
            videoView.requestFocus();
            videoView.start();
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer)
                {
                    videoView.seekTo(0);
                    videoView.start();
                }
            });

            mcountDownTimer = new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    Intent intent = new Intent(getApplicationContext(), Administrator.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    Toast.makeText(getApplicationContext(),"intented",Toast.LENGTH_SHORT).show();
                    finish();
                }
            };

            videoView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        mcountDownTimer.start();
                    }
                    else if(event.getAction() == MotionEvent.ACTION_UP)
                        mcountDownTimer.cancel();
                    return true;
                }
            });
            videoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Administrator.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        finish();
                    return true;
                }
            });

            btn_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), QRlink.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    finish();
                }
            });
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Main.class));
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
