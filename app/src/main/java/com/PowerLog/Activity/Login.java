package com.PowerLog.Activity;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.PowerLog.Common.Component.DCActivity;
import com.PowerLog.Common.Component.DCEditText;
import com.PowerLog.Common.Util.DCHttp;
import com.PowerLog.Dialog.NormalAlert;
import com.PowerLog.R;

import org.json.JSONObject;

import java.util.UUID;

import static android.view.HapticFeedbackConstants.LONG_PRESS;


public class Login extends DCActivity implements View.OnClickListener {

    Button btn_download, btn_login;
    DCEditText edt_code;
    ImageView dclogo;
    VideoView videoView;
    Handler mHandler;
    CountDownTimer mcountDownTimer;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            if (care.IsKiosk())
                setContentView(R.layout.kiosk_activity_login);
            else
                setContentView(R.layout.activity_login);
            if (care.IsKiosk()) {
                KioskViewMapping();
                KiosksetListener();
            } else {
                ViewMapping();
                setListener();
            }
            setDeviceID();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        try {
            Class act = null;
            if (v.getId() == R.id.bt_login) {
                JSONObject response = Httplogin(edt_code.getSource().getText().toString());
                if ((Boolean) response.get("isPresent") != false && (Boolean) response.get("isError") != true) {
                    care.setCurrentUserJson(response);
                    act = Main.class;
                } else
                    new NormalAlert(this, "고유번호에 해당하는 사용자가 없습니다.").show();
            } else if (v.getId() == R.id.bt_dwload) {
                act = QRlink.class;
            }
            care.setUserId(edt_code.getSource().getText().toString());
            ChangeActivity(act);
        } catch (Exception e) {
            new NormalAlert(this, "오류가 발생하였습니다. \n고유번호를 확인해주십시오.").show();
        }
    }

    @Override
    protected void ViewMapping() {
        dclogo = findViewById(R.id.dc_logo);
        btn_download = findViewById(R.id.bt_dwload);
        btn_login = findViewById(R.id.bt_login);
        edt_code = new DCEditText(findViewById(R.id.et_code));
    }

    @Override
    protected void setListener() {
        dclogo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ChangeActivity(Administrator.class);
                return false;
            }
        });
        btn_download.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }


    protected void KioskViewMapping() {
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
        try {
            videoView = findViewById(R.id.video_instruct);
            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dynamic_care_logo));
            videoView.setMediaController(new MediaController(this));
            videoView.requestFocus();
            videoView.start();
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
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
                    finish();
                }
            };
        } catch (Exception e) {
        }

    }

    protected void KiosksetListener() {
        btn_download.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        videoView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mcountDownTimer.start();
            } else if (event.getAction() == MotionEvent.ACTION_UP)
                mcountDownTimer.cancel();
            return true;
        });
        videoView.setOnLongClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Administrator.class);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
            finish();
            return true;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDeviceID() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "권한 승인이 필요합니다", Toast.LENGTH_LONG).show();
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_PHONE_STATE)) {
                    Toast.makeText(this, "기기식별번호 전송을 위해 카메라 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_PHONE_STATE},
                            1001);
                    Toast.makeText(this, "기기식별번호 전송을 위해 카메라 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                }
            }
            final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            final String tmDevice, tmSerial, androidId;
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            String deviceId = deviceUuid.toString();
            care.setDeviceID(deviceId);
        } catch (Exception e) {
//            new NormalAlert(this, e.toString(), true).show();
            e.printStackTrace();
        }
    }

    public JSONObject Httplogin(String code) {
        try {
            return new DCHttp().Login(new JSONObject().accumulate("uid", code).toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
