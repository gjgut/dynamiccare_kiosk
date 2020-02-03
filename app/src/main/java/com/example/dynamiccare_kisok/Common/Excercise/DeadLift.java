package com.example.dynamiccare_kisok.Common.Excercise;

import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.R;

public class DeadLift extends Excercise {
    public DeadLift(Main main)
    {
        super(main);
    }

    @Override
    public void getVideo() {
    }

    @Override
    public String getInstruction() {
        return main.getResources().getString(R.string.deadlift_instruction);
    }

    @Override
    public String getSimpleName() {
        return "데드 리프트";
    }

    @Override
    public Drawable getMappingBody() {
        return main.getResources().getDrawable(R.drawable.body_spine);
    }
}
