package com.example.dynamiccare_kisok.Command;

//    일반/등속성 운동모드 명령
public class ExcerciseMode extends Command {

    public ExcerciseMode(boolean isKinetic)
    {
        withChecksum = false;
        data = isKinetic ? "K" : "G";
    }

    @Override
    protected String getCommandCode() {
        return "CE";
    }
}
