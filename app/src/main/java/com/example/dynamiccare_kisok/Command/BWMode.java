package com.example.dynamiccare_kisok.Command;


//Bench/Wire 모드
public class BWMode extends Command {

    public BWMode(String Mode, String Kind)
    {
        data = Mode+Kind;
    }

    @Override
    protected String getCommandCode() {
        return "CMD";
    }



}
