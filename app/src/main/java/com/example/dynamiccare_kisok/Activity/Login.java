package com.example.dynamiccare_kisok.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dynamiccare_kisok.Common.Component.DCEditText;
import com.example.dynamiccare_kisok.R;

public class Login extends AppCompatActivity {

    Button btn_download,btn_login;
    DCEditText edt_code;
    ImageView dclogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
//                startActivity(new Intent(getApplicationContext(),SelectMode.class));
                startActivity(new Intent(getApplicationContext(), Main.class));
                overridePendingTransition(R.anim.right_in,R.anim.left_out);
                finish();
            }
        });
    }
}
