package com.example.dynamiccare_kisok.Common.Excercise;

import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.R;

public class Squat extends Excercise {
    public Squat(Main main)
    {
        super(main);
    }

    @Override
    public void getVideo() {
    }

    @Override
    public String getInstruction() {
        return main.getResources().getString(R.string.squat_instruction);
    }

    @Override
    public String getSimpleName() {
        return "스쿼트";
    }
    @Override
    public String getMuscleName() { return "대퇴사두근";}

    @Override
    public Drawable getMappingBody() {
        return main.getResources().getDrawable(R.drawable.body_quad);
    }
}
