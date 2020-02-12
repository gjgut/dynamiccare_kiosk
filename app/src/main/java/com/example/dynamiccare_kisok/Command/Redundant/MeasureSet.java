package com.example.dynamiccare_kisok.Command.Redundant;


//측정모드 SET
public class MeasureSet extends Command {

    public MeasureSet(String Mode, String iTension, String tTension, String time, String man, String old) {

        data = TrimParameter(Mode,"00")+
                TrimParameter(iTension,"000")+
                TrimParameter(tTension,"000")+
                TrimParameter(time,"000")+TrimParameter(man,"1")+TrimParameter(old,"0");
    }

    @Override
    protected String getCommandCode() {
        return "CSE";
    }
}
