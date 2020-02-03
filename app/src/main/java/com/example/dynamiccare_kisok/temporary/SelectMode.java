package com.example.dynamiccare_kisok.temporary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dynamiccare_kisok.R;

public class SelectMode extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);
//        DCActionBar customActionBar = new DCActionBar(this,getSupportActionBar(),"모드 선택");
//        customActionBar.OffHome();
    }

    @Override
    public void onClick(View v){
        switch (v.getId())
        {
            case R.id.btn_select_exec:
            {
                startActivity(new Intent(getApplicationContext(),ExcerciseMode.class));
                overridePendingTransition(R.anim.right_in,R.anim.left_out);
                finish();
                break;
            }
            case R.id.btn_sel_mes_isometric:
            {
                startActivity(new Intent(getApplicationContext(),Explain.class));
                overridePendingTransition(R.anim.right_in,R.anim.left_out);
                finish();
                break;
            }
            case R.id.btn_sel_mes_isotonic:
            {
                startActivity(new Intent(getApplicationContext(),Explain.class));
                overridePendingTransition(R.anim.right_in,R.anim.left_out);
                finish();
                break;

            }
            default:

        }
    }
}
