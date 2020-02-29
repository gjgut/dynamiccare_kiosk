package com.example.dynamiccare_kisok.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dynamiccare_kisok.Common.Component.DCEditText;
import com.example.dynamiccare_kisok.Common.DynamicCare;
import com.example.dynamiccare_kisok.Common.Util.HttpUtil;
import com.example.dynamiccare_kisok.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button btn_download,btn_login;
    DCEditText edt_code;
    ImageView dclogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dclogo = (ImageView)findViewById(R.id.dc_logo);
        btn_download = (Button)findViewById(R.id.bt_dwload);
        btn_login = (Button)findViewById(R.id.bt_login);
        edt_code = new DCEditText((EditText)findViewById(R.id.et_code));

        dclogo.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),Authentification.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in,R.anim.left_out);
                finish();
                return false;
            }
        });

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QRlink.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in,R.anim.left_out);
                finish();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                try {
                    JSONObject response = Httplogin("#137#249*4#3");
                    if (response.get("name") != null) {
                        DynamicCare care = (DynamicCare)getApplication();
                        care.setCurrentUserJson(response);
                        startActivity(new Intent(getApplicationContext(), Main.class));
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "고유번호에 해당하는 사용자가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
//                    startActivity(new Intent(getApplicationContext(), Main.class));
//                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                    finish();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public JSONObject Httplogin(String code)
    {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("uid", code);
            String json = jsonObject.toString();
            return new HttpUtil().execute(json).get();
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
