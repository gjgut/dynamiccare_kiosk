package com.example.dynamiccare_kisok.Common.Excercise;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.R;

public class LatPullDown extends Excercise {

    public LatPullDown(Main main)
    {
        super(main);
    }
    @Override
    public void getVideo() {
    }

    @Override
    public String getInstruction() {
        return main.getResources().getString(R.string.lat_instruction);
    }

    @Override
    public String getSimpleName() {
        return "랫 풀 다운";
    }
    @Override
    public String getMuscleName() { return "광배근";}

    @Override
    public Drawable getMappingBody() {
        return main.getResources().getDrawable(R.drawable.body_lat);
    }

    @Override
    public Uri getVideoUri()
    {
        return Uri.parse("android.resource://"+main.getPackageName()+"/raw/video_lat.mp4");
    }
}
