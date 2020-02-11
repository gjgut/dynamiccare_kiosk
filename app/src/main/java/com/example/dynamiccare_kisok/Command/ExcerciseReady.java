package com.example.dynamiccare_kisok.Command;


//운동모드 READY
public class ExcerciseReady extends Command{

    public ExcerciseReady(String Mode, String weight, String count, String rData)
    {
        data = TrimParameter(Mode,"00")+TrimParameter(weight,"010")+TrimParameter(count,"010")+TrimParameter(rData,"03");
    }

    @Override
    protected String getCommandCode() {
        return "CER";
    }
}
