package com.example.dynamiccare_kisok.Command.Redundant;

//측정모드 READY
public class MesureStart extends Command {

    public MesureStart(String max, String time)
    {
        data = TrimParameter(max,"005")+TrimParameter(time,"005");

    }

    @Override
    protected String getCommandCode() {
        return "CST";
    }

    @Override
    protected String getData() {
        return data;
    }
}
