package com.example.dynamiccare_kisok.Common.Component;

import android.util.Log;
import android.widget.Toast;

public class DCButtonManager {
    static DCButton Bench, Squat, Deadlift, Press, Carf, Curl, Extension, Lat;
    static DCButton Union[];
    static DCActionButton Start, Ready, Stop, Up, Down;
    static State DCState;

    public static State getDCState() {
        return DCState;
    }

    public enum State {Clear, StartSetting, Setted, Ready, Excercise, onRest, Paused, Stop}

    public DCButtonManager(DCButton Bench,
                           DCButton Squat,
                           DCButton Deadlift,
                           DCButton Press,
                           DCButton Carf,
                           DCButton Curl,
                           DCButton Extension,
                           DCButton Lat) {
        try {
            this.Bench = Bench;
            this.Squat = Squat;
            this.Deadlift = Deadlift;
            this.Press = Press;
            this.Carf = Carf;
            this.Curl = Curl;
            this.Extension = Extension;
            this.Lat = Lat;

            Union = new DCButton[]{Bench, Squat, Deadlift, Press, Carf, Curl, Extension, Lat};
            setDCState(State.Clear);
        }catch (Exception e)
        {
            Log.e("Error",e.toString());
        }
    }

    public DCButtonManager(DCActionButton Start,
                           DCActionButton Ready,
                           DCActionButton Up,
                           DCActionButton Down) {
        this.Start = Start;
        this.Ready = Ready;
        this.Up = Up;
        this.Down = Down;
    }

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
                           DCActionButton Stop) {
        try {
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
            Union = new DCButton[]{Bench, Squat, Deadlift, Press, Carf, Curl, Extension, Lat};
            setDCState(State.Clear);
        }catch (Exception e)
        {
            Log.e("Error",e.toString());
        }
    }

    public static void setDCState(State state) {
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
                    DCState = State.StartSetting;
                    for (DCButton i : Union) {
                        if (i != DCButton.PressedButton) {
                            i.Deactivate();
                        }
                    }
                    Ready.Deactivate();
                    Start.Deactivate();
                    if (Stop != null)
                        Stop.Activate();
                    Up.Deactivate();
                    Down.Deactivate();

                    break;
                }
                case Setted: {
                    DCState = State.Setted;
                    for (DCButton i : Union) {
                        i.Activate();
                    }
                    Ready.Activate();
                    Start.Deactivate();
                    Up.Activate();
                    Down.Activate();
                    break;
                }
                case Ready:
                    DCState = State.Ready;
                    for (DCButton i : Union) {
                        if (i != DCButton.PressedButton) {
                            i.Deactivate();
                        }
                    }
                    Ready.Activate();
                    Start.Deactivate();
                    break;
                case Excercise:
                    DCState = State.Excercise;
                    Ready.Activate();
                    Start.Activate();
                    Up.Deactivate();
                    Down.Deactivate();
                    break;
                case onRest:
                    Ready.Deactivate();
                    Start.Deactivate();
                    break;
                case Paused: {
                    DCState = State.Paused;
                    break;
                }
                case Stop: {
                    if (Ready.isPressed)
                        Ready.setPressedwithNoSound();
                    if (Start.isPressed)
                        Start.setPressedwithNoSound();
                    DCState = State.Stop;
                    break;
                }
            }
        }catch (Exception e)
        {
            Log.e("Error",e.toString());
        }
    }


}
