package com.example.dynamiccare_kisok.Common.Excercise;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.R;

public class ShoulderPress extends Excercise {
    public ShoulderPress(Main main)
    {
        super(main);
    }

    @Override
    public void getVideo() {
    }

    @Override
    public String getInstruction() {
        return main.getResources().getString(R.string.press_instruction);
    }

    @Override
    public String getSimpleName() {
        return "숄더 프레스";
    }
    @Override
    public String getMuscleName() { return "삼각근";}

    @Override
    public Drawable getMappingBody() {
        return main.getResources().getDrawable(R.drawable.body_deltoid);
    }

    @Override
    public Uri getVideoUri()
    {
        return Uri.parse("android.resource://"+main.getPackageName()+"/"+R.raw.video_press);

    }
}
