package com.example.dynamiccare_kisok.Command;

public class StaticCommands {
    protected static String Checksums="";
    protected String data="";
    protected static boolean withChecksum=true;
     public static String getFullCommand(boolean isChecksum,String content)
     {
         return "$"+content+(withChecksum?getCheckSums(content):"")+"#";
     }
     protected static String getCheckSums(String content)
     {
         int asciisum=0;
         for(char datum:content.toCharArray())
         {
             asciisum +=(int)datum;
         }
         String CheckSumBase = Integer.toHexString(asciisum & 0x00FF);
         Checksums = CheckSumBase.substring(0,1)+CheckSumBase.substring(CheckSumBase.length()-1);
         return Checksums;
     }
     protected static String TrimParameter(String str, String def)
     {
         int strleng = str.length();
         int defleng = def.length();
         if(strleng==defleng)
             return str;
         else if(strleng<defleng)
         {
             for(int i=0;i<=defleng-strleng;i++)
                 str = "0"+str;
             return str;
         }
         else
             return def;
     }

     public static String BWMode(String Mode, String Kind){ return getFullCommand(true,"CMD"+Mode+Kind);}
    public static String Connect(){ return getFullCommand(true,"CON"+"00000000000");}
    public static String Home(boolean isHome){
        if(isHome)
        {
            return getFullCommand(false,"CHM0");
        }
        else
            return getFullCommand(true,"CSP0");
        }
    public static String Position(String direction){ return getFullCommand(true,"CPS"+direction);}
    public static String SetACK(String Mode, String iTension, String tTension, String time, String man, String old){
         return getFullCommand(true,"ASE"+TrimParameter(Mode,"00")+
            TrimParameter(iTension,"000")+
            TrimParameter(tTension,"000")+
            TrimParameter(time,"000")+man+old);
     }
    public static String ExcerciseMode(boolean isKinetic){ return getFullCommand(false,"CE"+(isKinetic ? "K" : "G"));}
    public static String ExcerciseSetting(String Mode, String weight, String count, String rData){ return getFullCommand(true,"CET"+TrimParameter(Mode,"00")+TrimParameter(weight,"010")+TrimParameter(count,"010")+TrimParameter(rData,"03"));}
    public static String ExcerciseReady(String Mode, String weight, String count, String rData){ return getFullCommand(true,"CER"+TrimParameter(Mode,"00")+TrimParameter(weight,"010")+TrimParameter(count,"010")+TrimParameter(rData,"03"));}
    public static String ExcercisePause(String Mode, String weight, String count, String rData){ return getFullCommand(true,"CEU"+TrimParameter(Mode,"00")+TrimParameter(weight,"000")+TrimParameter(count,"000")+TrimParameter(rData,"03"));}
    public static String ExcerciseStop(String Mode, String weight, String count, String rData){ return getFullCommand(true,"CEP"+TrimParameter(Mode,"00")+TrimParameter(weight,"000")+TrimParameter(count,"000")+TrimParameter(rData,"03"));}
    public static String MeasureLevelCheck(String data){ return getFullCommand(false,"CLV"+data);}
    public static String MeasureMode(boolean isIsotonic){ return getFullCommand(true,"CM"+(isIsotonic ? "M" : "T"));}
    public static String MeasureSet(String Mode, String iTension, String tTension, String time, String man, String old)
    {
         return getFullCommand(true,"CSE"+TrimParameter(Mode,"00")+
            TrimParameter(iTension,"000")+
            TrimParameter(tTension,"000")+
            TrimParameter(time,"000")+TrimParameter(man,"1")+TrimParameter(old,"0"));}
    public static String MeasureReady(String max, String time){ return getFullCommand(true,"CRY"+TrimParameter(max,"005")+TrimParameter(time,"005"));}
    public static String MeasureStart(String max, String time){ return getFullCommand(true,"CST"+TrimParameter(max,"005")+TrimParameter(time,"005"));}

}
