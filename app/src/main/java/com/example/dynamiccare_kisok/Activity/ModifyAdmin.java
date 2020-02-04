package com.example.dynamiccare_kisok.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.dynamiccare_kisok.Common.Component.DCEditText;
import com.example.dynamiccare_kisok.R;

public class ModifyAdmin extends AppCompatActivity {

    ImageButton back,newpwvisible;
    Button ok;
    DCEditText prev,New,correct;
    boolean isVisible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_admin);

        back = (ImageButton)findViewById(R.id.btn_back);
        newpwvisible = (ImageButton)findViewById(R.id.btn_newpw_visible);
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

        newpwvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVisible)
                {
                    newpwvisible.setImageDrawable(getDrawable(R.drawable.ic_visibility_off));
                    newpwvisible.setPadding(30,30,30,30);
                    New.getSource().setTransformationMethod(PasswordTransformationMethod.getInstance());
                    New.getSource().setSelection(New.getSource().length());
                    isVisible=false;
                }
                else
                {
                    newpwvisible.setImageDrawable(getDrawable(R.drawable.ic_visibility_on));
                    newpwvisible.setPadding(40,35,31,38);
                    New.getSource().setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    New.getSource().setSelection(New.getSource().length());
                    isVisible=true;
                }
        }
        });


    }
}
