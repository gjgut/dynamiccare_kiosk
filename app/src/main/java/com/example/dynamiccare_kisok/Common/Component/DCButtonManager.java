package com.example.dynamiccare_kisok.Common.Component;

import android.widget.Toast;

public class DCButtonManager {
    static DCButton Bench, Squat, Deadlift, Press, Carf, Curl, Extension, Lat;
    static DCButton Union[];
    static DCActionButton Start, Ready, Stop;
    static State DCState;

    public static State getDCState() {
        return DCState;
    }

    public enum State {Clear, StartSetting, Setted,Ready, Excercise,onRest, Paused, Stop}
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
            e.printStackTrace();
        }
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
            e.printStackTrace();
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
                    Start.Deactivate();
                    break;
                }
                case Ready:
                    for (DCButton i : Union) {
                        if (i != DCButton.PressedButton) {
                            i.Deactivate();
                        }
                    }
                    Ready.Activate();
                    Start.Deactivate();
                    DCState = State.Ready;
                    break;
                case Excercise:
                    Ready.Activate();
                    Start.Activate();
                    DCState = State.Excercise;
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
                        Ready.setPressed();
                    if (Start.isPressed)
                        Start.setPressed();
                    DCState = State.Stop;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
