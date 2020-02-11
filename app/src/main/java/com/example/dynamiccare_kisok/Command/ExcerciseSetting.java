package com.example.dynamiccare_kisok.Command;

//운동모드 SET
public class ExcerciseSetting extends Command{

    public ExcerciseSetting(String Mode, String weight, String count, String rData)
    {
        data = TrimParameter(Mode,"00")+TrimParameter(weight,"010")+TrimParameter(count,"010")+TrimParameter(rData,"03");
    }

    @Override
    protected String getCommandCode() {
        return "CET";
    }
}
