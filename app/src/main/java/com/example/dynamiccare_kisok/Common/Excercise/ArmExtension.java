package com.example.dynamiccare_kisok.Common.Excercise;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.R;

public class ArmExtension extends Excercise {
    public ArmExtension(Main main)
    {
        super(main);
    }

    @Override
    public void getVideo() {
    }

    @Override
    public String getInstruction() {
        return main.getResources().getString(R.string.extension_instruction);
    }

    @Override
    public String getSimpleName() {
        return "암 익스텐션";
    }

    @Override
    public String getMuscleName() { return "상완삼두근";}

    @Override
    public Drawable getMappingBody() {
        return main.getResources().getDrawable(R.drawable.body_biceps);
    }

    @Override
    public Uri getVideoUri()
    {
        return Uri.parse("android.resource://"+main.getPackageName()+"/"+R.raw.video_extension);
    }
}
