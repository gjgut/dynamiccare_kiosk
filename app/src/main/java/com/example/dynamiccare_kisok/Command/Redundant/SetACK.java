package com.example.dynamiccare_kisok.Command.Redundant;


//측정모드 SET
public class SetACK extends Command {

    public SetACK(String Mode, String iTension, String tTension, String time, String man, String old)
    {
        data = TrimParameter(Mode,"00")+
                TrimParameter(iTension,"000")+
                TrimParameter(tTension,"000")+
                TrimParameter(time,"000")+man+old;
    }

    @Override
    protected String getCommandCode() {
        return "ASE";
    }
}
