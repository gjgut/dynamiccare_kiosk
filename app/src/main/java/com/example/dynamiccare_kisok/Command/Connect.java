package com.example.dynamiccare_kisok.Command;

//연결
public class Connect extends Command {

    @Override
    protected String getCommandCode() {
        return "CON";
    }

    @Override
    protected String getData()
    {
        return "00000000000";
    }
}
