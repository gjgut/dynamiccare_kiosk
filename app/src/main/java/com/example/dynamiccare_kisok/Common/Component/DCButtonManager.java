package com.example.dynamiccare_kisok.Common.Component;

import android.widget.Toast;

public class DCButtonManager {
    static DCButton Bench,Squat,Deadlift,Press,Carf,Curl,Extension,Lat;
    static DCButton Union[];
    static DCActionButton Start,Ready,Stop;
    static State DCState;
    public static State getDCState()
    {
        return DCState;
    }
    public enum State { Clear,StartSetting,Setted,Excercise,Stop }

    public DCButtonManager(DCButton Bench,
                           DCButton Squat,
                           DCButton Deadlift,
                           DCButton Press,
                           DCButton Carf,
                           DCButton Curl,
                           DCButton Extension,
                           DCButton Lat,
                           DCActionButton Start,
                           DCActionButton Ready,
                           DCActionButton Stop)
    {
        this.Bench = Bench;
        this.Squat = Squat;
        this.Deadlift = Deadlift;
        this.Press = Press;
        this.Carf = Carf;
        this.Curl = Curl;
        this.Extension = Extension;
        this.Lat = Lat;

        this.Start = Start;
        this.Ready = Ready;
        this.Stop = Stop;
        Union = new DCButton[]{Bench,Squat,Deadlift,Press,Carf,Curl,Extension,Lat};

        DCState = State.Clear;
    }

    public static void setDCState(State state)
    {
        try {
            switch (state) {
                case Clear: {
                    DCButton.PressedOff();
                    for (DCButton i : Union) {
                        i.Activate();
                    }
                    Ready.Deactivate();
                    Start.Deactivate();
                    Stop.Deactivate();

                    DCState = State.Clear;
                    break;
                }
                case StartSetting: {
                    for (DCButton i : Union) {
                        if (i != DCButton.PressedButton) {
                            i.Deactivate();
                        }
                    }
                    Ready.Deactivate();
                    Start.Deactivate();
                    Stop.Activate();

                    DCState = State.StartSetting;
                    break;
                }
                case Setted: {
                    for (DCButton i : Union) {
                        i.Activate();
                    }
                    DCState = State.Setted;
                    Ready.Activate();
                    Start.Activate();
                    break;
                }
                case Excercise: {

                    DCState = State.Excercise;
                    break;
                }
                case Stop: {
                    DCState = State.Stop;
                    break;
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }



}