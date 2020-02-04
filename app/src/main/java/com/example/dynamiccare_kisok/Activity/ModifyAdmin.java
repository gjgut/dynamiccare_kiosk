package com.example.dynamiccare_kisok.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.dynamiccare_kisok.Common.Component.DCEditText;
import com.example.dynamiccare_kisok.R;

public class ModifyAdmin extends AppCompatActivity {

    ImageButton back;
    Button ok;
    DCEditText prev,New,correct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_admin);

        back = (ImageButton)findViewById(R.id.btn_back);
        ok = (Button)findViewById(R.id.btn_set_admin_ok);

        prev = new DCEditText((EditText)findViewById(R.id.et_prevpw));
        New = new DCEditText((EditText)findViewById(R.id.et_newpw));
        correct = new DCEditText((EditText)findViewById(R.id.et_pwconfirm));
        
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Authentification.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in,R.anim.right_out);
                finish();
            }
        });
        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),Authentification.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in,R.anim.right_out);
                finish();
            }
        });


    }
}
