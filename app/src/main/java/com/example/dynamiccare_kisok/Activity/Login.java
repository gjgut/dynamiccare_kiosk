package com.example.dynamiccare_kisok.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.dynamiccare_kisok.Common.Component.DCEditText;
import com.example.dynamiccare_kisok.R;

public class Login extends AppCompatActivity {

    Button btn_download, btn_login;
    DCEditText edt_code;
    ImageView dclogo;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            dclogo = (ImageView) findViewById(R.id.dc_logo);
            btn_download = (Button) findViewById(R.id.bt_dwload);
            btn_login = (Button) findViewById(R.id.bt_login);
            edt_code = new DCEditText((EditText) findViewById(R.id.et_code));

            videoView = findViewById(R.id.video_instruct);

            videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/raw/dynamic_care_logo.mp4"));
            videoView.setMediaController(new MediaController(this));
            videoView.requestFocus();
            videoView.start();
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                int count = 0; // initialize somewhere before, not sure if this will work here
                int maxCount = 5;

                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                        videoView.seekTo(0);
                        videoView.start();
                }});

            dclogo.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Authentification.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    finish();
                    return false;
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
//                startActivity(new Intent(getApplicationContext(),SelectMode.class));
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
