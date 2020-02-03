package com.example.dynamiccare_kisok.Common.Excercise;

import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.R;

public class ArmCurl extends Excercise {

    public ArmCurl(Main main)
    {
        super(main);
    }

    @Override
    public void getVideo() {
    }

    @Override
    public String getInstruction() {
        return main.getResources().getString(R.string.curl_instruction);
    }

    @Override
    public String getSimpleName() {
        return "암 컬";
    }

    @Override
    public Drawable getMappingBody() {
        return main.getResources().getDrawable(R.drawable.body_biceps);
    }
}
