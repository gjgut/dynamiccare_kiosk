package com.example.dynamiccare_kisok.Command;


//Position 명령
public class Position extends Command {

    public Position(String direction) {
        data = direction;
    }

    @Override
    protected String getCommandCode() {
        return "CPS";
    }
}
