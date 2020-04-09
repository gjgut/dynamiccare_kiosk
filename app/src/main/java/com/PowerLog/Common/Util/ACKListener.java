package com.PowerLog.Common.Util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.PowerLog.Activity.Main;
import com.PowerLog.Common.Object.ACK;

import java.lang.ref.WeakReference;

public class ACKListener extends Handler {
    private WeakReference<Main> mActivity;
    private Main main;

    public ACKListener(Main activity) {
        mActivity = new WeakReference<>(activity);
        main = activity;
    }

    @Override
    public void handleMessage(Message msg) {
        try {
            switch (msg.what) {
                case UsbService.MESSAGE_FROM_SERIAL_PORT: {
                    if (!msg.obj.toString().contains("$"))
                        return;
//                    Toast.makeText(main, "Command:" + msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    main.HandleACK(ParseACK(msg.obj.toString()));
                    Log.i("Tossed_main", msg.obj.toString());
//                    Toast.makeText(main, "ACK:" + msg.obj.toString(), Toast.LENGTH_SHORT).show();
                }
                break;
                case UsbService.CTS_CHANGE:
                    Toast.makeText(mActivity.get(), "CTS_CHANGE", Toast.LENGTH_LONG).show();
                    break;
                case UsbService.DSR_CHANGE:
                    Toast.makeText(mActivity.get(), "DSR_CHANGE", Toast.LENGTH_LONG).show();
                    break;
            }
        } catch (Exception e) {
            Log.i("ParseError", e.toString());
            e.printStackTrace();
        }
    }

    public static ACK ParseACK(String ack) {
        try {
            Log.i("ParseACK:", ack);
            ack = ack.substring(1, ack.length() - 1);
            ACK result = new ACK();
            switch (ack.substring(0, 3)) {
                case "CHM":
                case "ACS":
                case "AEE":
                case "ASS":
                    result.setCommandCode(ack.substring(0, 3));
                    result.setData(ack.substring(3, 5));
                    break;
                case "AME":
                    result.setCommandCode(ack.substring(0, 3));
                    result.setTime(ack.substring(3, 9));
                    result.setmTension(ack.substring(9, 15));
                    result.setData(ack.substring(15, 16));
                    result.setChecksums(ack.substring(16, 18));
                    break;
                case "ASP":
                    result.setCommandCode(ack.substring(0, 3));
                    Log.i("Parsing ASP", "");
                    result.setData(ack.substring(3, 4));
                    result.setChecksums(ack.substring(4, 6));
                    break;
                case "ACD":
                    result.setCommandCode(ack.substring(0, 3));
                    result.setData(ack.substring(3, ack.length()));
                    break;
                case "ACB":
                case "AET":
                    result.setCommandCode(ack.substring(0, 3));
                    result.setData(ack.substring(3, 4));
                    break;
                case "AHM":
                case "PCA":
                    result.setCommandCode(ack.substring(0, 3));
                    break;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("ParseError", e.toString());
            return null;
        }
    }

    

}
