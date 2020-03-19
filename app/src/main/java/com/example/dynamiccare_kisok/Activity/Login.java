package com.example.dynamiccare_kisok.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dynamiccare_kisok.Common.Component.DCActivity;
import com.example.dynamiccare_kisok.Common.Component.DCEditText;
import com.example.dynamiccare_kisok.Common.DynamicCare;
import com.example.dynamiccare_kisok.Common.Util.DCHttp;
import com.example.dynamiccare_kisok.R;

import org.json.JSONObject;


public class Login extends DCActivity implements View.OnClickListener {

    Button btn_download, btn_login;
    DCEditText edt_code;
    ImageView dclogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            ViewMapping();
            setDeviceID();
            setListener();

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
                }
                else
                    throw new Exception();
            } else if (v.getId() == R.id.bt_dwload) {
                act = QRlink.class;
            }
            ChangeActivity(act);
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "고유번호에 해당하는 사용자가 없습니다.", Toast.LENGTH_SHORT).show();
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

    public void setDeviceID() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 승인이 필요합니다", Toast.LENGTH_LONG).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
                Toast.makeText(this, "000부분 사용을 위해 카메라 권한이 필요합니다.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        1001);
                Toast.makeText(this, "000부분 사용을 위해 카메라 권한이 필요합니다.", Toast.LENGTH_LONG).show();
            }
        }
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String device = manager.getDeviceId();
        care.setDeviceID(device);
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
