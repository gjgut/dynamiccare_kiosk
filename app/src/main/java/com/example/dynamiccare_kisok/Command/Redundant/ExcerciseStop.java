package com.example.dynamiccare_kisok.Command.Redundant;


//운동모드 STOP
public class ExcerciseStop extends Command{

    public ExcerciseStop(String Mode, String weight, String count, String rData)
    {
        data = TrimParameter(Mode,"00")+TrimParameter(weight,"000")+TrimParameter(count,"000")+TrimParameter(rData,"03");
    }

    @Override
    protected String getCommandCode() {
        return "CEP";
    }
}
