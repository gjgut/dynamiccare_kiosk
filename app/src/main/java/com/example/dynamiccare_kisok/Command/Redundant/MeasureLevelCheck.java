package com.example.dynamiccare_kisok.Command.Redundant;


//측정모드 레벨 체크
public class MeasureLevelCheck extends Command {

    public MeasureLevelCheck(String data) {
        this.data = data;
        withChecksum=false;
    }

    @Override
    protected String getCommandCode() {
        return "CLV";
    }
}
