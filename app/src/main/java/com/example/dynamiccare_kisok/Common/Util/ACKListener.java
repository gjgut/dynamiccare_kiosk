package com.example.dynamiccare_kisok.Common.Util;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.dynamiccare_kisok.Activity.Main;

import java.lang.ref.WeakReference;

public class ACKListener extends Handler{
    private WeakReference <Main> mActivity;
    private Main main;

    public ACKListener(Main activity) {
        mActivity = new WeakReference<>(activity);
        main = activity;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case UsbService.MESSAGE_FROM_SERIAL_PORT:
                {
                    main.HandleACK(ACKParser.ParseACK(msg.obj.toString()));
                 }
                break;
            case UsbService.CTS_CHANGE:
                Toast.makeText(mActivity.get(), "CTS_CHANGE", Toast.LENGTH_LONG).show();
                break;
            case UsbService.DSR_CHANGE:
                Toast.makeText(mActivity.get(), "DSR_CHANGE", Toast.LENGTH_LONG).show();
                break;
        }
    }
    private static class ACKParser
    {
        public static ACK ParseACK(String ack)
        {
            ack = ack.substring(1,ack.length()-1);
            ACK result = new ACK();
            switch (ack.substring(0,3))
            {
                case "AME":
                {
                    result.setCommandCode(ack.substring(0,3));
                    result.setTime(ack.substring(3,9));
                    result.setmTension(ack.substring(9,15));
                    result.setData(ack.substring(15,16));
                    result.setChecksums(ack.substring(16,18));
                    break;
                }
                case "ASP":
                {
                    result.setData(ack.substring(3,4));
                    result.setChecksums(ack.substring(4,6));
                    break;
                }
                case "ACD":
                {
                    result.setCommandCode(ack.substring(0,3));
                    result.setData(ack.substring(3,ack.length()));
                    break;
                }
                case "ACB":
                case "AET":
                {
                    result.setCommandCode(ack.substring(0,3));
                    result.setData(ack.substring(3,4));
                    break;
                }
                case "ACS":
                case "AEE":
                case "ASS":
                {
                    result.setCommandCode(ack.substring(0,3));
                    result.setData(ack.substring(3,5));
                    break;
                }
            }
            return result;
        }
    }
}
