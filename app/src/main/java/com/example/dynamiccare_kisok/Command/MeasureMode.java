package com.example.dynamiccare_kisok.Command;


//측정모드 SET
public class MeasureMode extends Command {

    public MeasureMode(boolean isIsotonic)
    {
        withChecksum = false;
        data = isIsotonic ? "M" : "T";
    }

    @Override
    protected String getCommandCode() {
        return "CM";
    }
}
