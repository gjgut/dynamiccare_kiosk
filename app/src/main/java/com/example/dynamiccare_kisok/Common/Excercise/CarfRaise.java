package com.example.dynamiccare_kisok.Common.Excercise;

import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.R;

public class CarfRaise extends Excercise {
    public CarfRaise(Main main)
    {
        super(main);
    }

    @Override
    public void getVideo() {
    }

    @Override
    public String getInstruction() {
        return main.getResources().getString(R.string.carf_instruction);
    }

    @Override
    public String getSimpleName() {
        return "카프 레이즈";
    }
    @Override
    public String getMuscleName() { return "하퇴삼두근";}

    @Override
    public Drawable getMappingBody() {
        return main.getResources().getDrawable(R.drawable.body_carf);
    }
}
