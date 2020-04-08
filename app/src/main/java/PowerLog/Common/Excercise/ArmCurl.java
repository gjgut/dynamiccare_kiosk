package PowerLog.Common.Excercise;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import PowerLog.Activity.Main;
import PowerLog.R;

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

    public String getMuscleName() { return "상완이두근";}

    @Override
    public Drawable getMappingBody() {
        return main.getResources().getDrawable(R.drawable.body_biceps);
    }

    @Override
    public Uri getVideoUri()
    {
        return Uri.parse("android.resource://"+main.getPackageName()+"/"+R.raw.video_curl);
    }

    @Override
    public String getMode() {
        return "15";
    }

    @Override
    public String getDBCode() {
        return "F";
    }
}
