package com.example.dynamiccare_kisok.Command.Redundant;


//홈 버튼 명령
public class Home extends Command {

    String CommandCode="";
    public Home(boolean isHome) {
        if(isHome)
        {
            withChecksum = false;
            CommandCode = "CHM";
        }
        else
            CommandCode = "CSP";
    }

    @Override
    protected String getCommandCode() {
        return CommandCode;
    }

    @Override
    protected String getData()
    {
        return "0";
    }
}
