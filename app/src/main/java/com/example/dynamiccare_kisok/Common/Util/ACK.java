package com.example.dynamiccare_kisok.Common.Util;

public class ACK {
    String CommandCode,mTension,time,data,checksums,Full;

    public void setFullString(String a)
    {
        Full = a;
    }

    public String getCommandCode() {
        return CommandCode;
    }

    public void setCommandCode(String commandCode) {
        CommandCode = commandCode;
    }

    public String getmTension() {
        return mTension;
    }

    public void setmTension(String mTension) {
        this.mTension = mTension;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getChecksums() {
        return checksums;
    }

    public void setChecksums(String checksums) {
        this.checksums = checksums;
    }
}