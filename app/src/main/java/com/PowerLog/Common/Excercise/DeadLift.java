package com.PowerLog.Common.Excercise;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.PowerLog.Activity.Main;
import com.PowerLog.R;

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
    public String getMuscleName() { return "척추기립근";}

    @Override
    public Drawable getMappingBody() {
        return main.getResources().getDrawable(R.drawable.body_spine);
    }

    @Override
    public Uri getVideoUri()
    {
        return Uri.parse("android.resource://"+main.getPackageName()+"/"+R.raw.video_deadlift);

    }

    @Override
    public String getMode() {
        return "02";
    }

    @Override
    public String getDBCode() {
        return "C";
    }
}
