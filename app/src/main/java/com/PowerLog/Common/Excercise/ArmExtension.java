package com.PowerLog.Common.Excercise;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.PowerLog.Activity.Main;
import com.PowerLog.R;

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

    @Override
    public String getMode(){
        return "16";
    }

    @Override
    public String getDBCode() {
        return "G";
    }
}
