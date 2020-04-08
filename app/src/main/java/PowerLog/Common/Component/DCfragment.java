package PowerLog.Common.Component;

import android.os.CountDownTimer;
import android.view.View;

import androidx.fragment.app.Fragment;

import PowerLog.Activity.Administrator;
import PowerLog.Activity.Main;
import PowerLog.Common.DynamicCare;
import PowerLog.Common.Object.ACK;

public abstract class DCfragment extends Fragment implements View.OnClickListener{
    protected  Main main;
    protected DCfragment prev;
    protected DynamicCare care;
    protected Administrator admin;
    protected CountDownTimer timer;
    public DCfragment(){}
    public DCfragment(Main main)
    {
        this.main = main;
        care = (DynamicCare)main.getApplication();
    }
    public DCfragment(Administrator admin){
        this.admin = admin;
        care = (DynamicCare)admin.getApplication();
    }
    public DCfragment getBackFragment() {
        return null;
    }
    public DCfragment getNextFragment() {
        return null;
    }
    public String getTitle()
    {
        return null;
    }
    public abstract int isHomeVisible();

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(timer !=null)
        {
            timer.cancel();
        }
    }
    public void HandleACK(ACK ack)
    {
        switch (ack.getData())
        {

        }
    }
}
