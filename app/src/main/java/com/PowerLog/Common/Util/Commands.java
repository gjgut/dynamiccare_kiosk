package com.PowerLog.Common.Util;

import android.util.Log;

public class Commands {
    protected static boolean withChecksum = true;

    public static String getFullCommand(boolean isChecksum, String content) {
        String s = "$" + content + (withChecksum ? getCheckSums(content) : "") + "#";
        Log.i("Command",s);
        return s;
    }

    protected static String getCheckSums(String content) {
        try {
            int asciisum = 0;
            for (char datum : content.toCharArray()) {
                asciisum += (int) datum;
            }
            String CheckSumBase = Integer.toHexString(asciisum & 0x00FF).toUpperCase();
            if (CheckSumBase.length() < 2)
                CheckSumBase = "0" + CheckSumBase;
            return CheckSumBase;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected static String TrimParameter(String str, String def) {
        int strleng = str.length();
        int defleng = def.length();
        if (strleng == defleng)
            return str;
        else if (strleng < defleng) {
            for (int i = 0; i < defleng - strleng; i++)
                str = "0" + str;
            return str;
        } else
            return def;
    }

    public static byte[] BWMode(String Mode, String Kind) {
        return getFullCommand(true, "CMD" + Mode + Kind).getBytes();
    }

    public static byte[] Connect() {
        return getFullCommand(true, "CON" + "00000000000").getBytes();
    }

    public static byte[] Home(boolean isHome) {
        if (isHome) {
            return getFullCommand(false, "CHM0").getBytes();
        } else
            return getFullCommand(true, "CSP0").getBytes();
    }

    public static byte[] Position(String direction) {
        return getFullCommand(true, "CPS" + direction).getBytes();
    }

    public static byte[] SetACK(String Mode, String iTension, String tTension, String time, String man, String old) {
        return getFullCommand(true, "ASE" + TrimParameter(Mode, "00") +
                TrimParameter(iTension, "000") +
                TrimParameter(tTension, "000") +
                TrimParameter(time, "000") + man + old).getBytes();
    }

    public static byte[] ExcerciseMode(boolean isKinetic) {
        Log.i("Sent Command:", getFullCommand(false, "CE" + (isKinetic ? "K" : "G")));
        return getFullCommand(false, "CE" + (isKinetic ? "K" : "G")).getBytes();
    }

    public static byte[] ExcerciseSetting(String Mode, String weight, String count, String rData) {
        return getFullCommand(true, "CET" + TrimParameter(Mode, "00") + TrimParameter(weight, "010") + TrimParameter(count, "010") + TrimParameter(rData, "03")).getBytes();
    }

    public static byte[] ExcerciseStart(String Mode, String weight, String count, String set) {
        return getFullCommand(true, "CES" + TrimParameter(Mode, "00") + TrimParameter(weight, "010") + TrimParameter(count, "010") + TrimParameter(set, "03")).getBytes();
    }

    public static byte[] ExcerciseReady(String Mode, String weight, String count, String set) {
        return getFullCommand(true, "CER" + TrimParameter(Mode, "00") + TrimParameter(weight, "010") + TrimParameter(count, "010") + TrimParameter(set, "03")).getBytes();
    }

    public static byte[] ExcercisePause(String Mode, String weight, String count, String rData) {
        return getFullCommand(true, "CEU" + TrimParameter(Mode, "00") + TrimParameter(weight, "000") + TrimParameter(count, "000") + TrimParameter(rData, "03")).getBytes();
    }

    public static byte[] ExcerciseStop(String Mode, String weight, String count, String set) {
        return getFullCommand(true, "CEP" + TrimParameter(Mode, "00") + TrimParameter(weight, "000") + TrimParameter(count, "000") + TrimParameter(set, "03")).getBytes();
    }

    public static byte[] MeasureLevelCheck(String data) {
        return getFullCommand(false, "CLV" + data).getBytes();
    }

    public static byte[] MeasureMode(boolean isIsotonic) {
        Log.i("Sent Command", getFullCommand(true, "CM" + (isIsotonic ? "M" : "T")));
        return getFullCommand(true, "CM" + (isIsotonic ? "M" : "T")).getBytes();
    }

    public static byte[] MeasureSet(String Mode, String iTension, String tTension, String time, String man, String old) {
        return getFullCommand(true, "CSE" + TrimParameter(Mode, "00") +
                TrimParameter(iTension, "000") +
                TrimParameter(tTension, "000") +
                TrimParameter(time, "000") + TrimParameter(man, "2") + TrimParameter(old, "0")).getBytes();
    }

    public static byte[] MeasureReady(String max, String time) {
        return getFullCommand(true, "CRY" + TrimParameter(max, "005") + TrimParameter(time, "005")).getBytes();
    }

    public static byte[] MeasureStart(String max, String time) {

        Log.i("Sent Command:", getFullCommand(true, "CST" + TrimParameter(max, "005") + TrimParameter(time, "005")));
        return getFullCommand(true, "CST" + TrimParameter(max, "005") + TrimParameter(time, "005")).getBytes();
    }

}
