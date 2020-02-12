package com.example.dynamiccare_kisok.Command.Redundant;

public abstract class Command {
    protected String Checksums="";
    protected String data="";
    protected boolean withChecksum=true;
     public String getFullCommand()
     {
         return "$"+getCommandCode()+getData()+(withChecksum?getCheckSums():"")+"#";
     }
     public byte[] getBytes()
     {
         return getFullCommand().getBytes();
     }
     protected String getCheckSums()
     {
         int asciisum=0;
         String src = getCommandCode()+data;
         for(char datum:src.toCharArray())
         {
             asciisum +=(int)datum;
         }
         String CheckSumBase = Integer.toHexString(asciisum & 0x00FF);
         Checksums = CheckSumBase.substring(0,1)+CheckSumBase.substring(CheckSumBase.length()-1);
         return Checksums;
     }
     protected String TrimParameter(String str, String def)
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
     protected abstract String getCommandCode();
     protected String getData()
     {
         return data;
     }

}
