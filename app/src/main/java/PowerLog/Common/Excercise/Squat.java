package PowerLog.Common.Excercise;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import PowerLog.Activity.Main;
import PowerLog.R;

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

    @Override
    public Uri getVideoUri()
    {
        return Uri.parse("android.resource://"+main.getPackageName()+"/"+R.raw.video_squat);

    }

    @Override
    public String getMode() {
        return "01";
    }

    @Override
    public String getDBCode() {
        return "B";
    }
}
