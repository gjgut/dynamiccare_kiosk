package com.example.dynamiccare_kisok.Common.Component;

import android.util.Log;
import android.widget.Toast;

import com.example.dynamiccare_kisok.Activity.Main;

public class DCButtonManager {
    static DCButton Bench, Squat, Deadlift, Press, Carf, Curl, Extension, Lat;
    static DCButton Union[];
    static DCActionButton Start, Ready, Stop, Up, Down;
    static State DCState;
    private static Main main;

    public static State getDCState() {
        return DCState;
    }

    public enum State {Clear, StartSetting, Setted, Ready, Excercise, onRest, Paused, Stop}

    public static void setMainContext(Main main)
    {
        DCButtonManager.main = main;
    }

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
        } catch (Exception e) {
            Log.e("Error", e.toString());
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
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    public static void setDCState(State state) {
        try {
            switch (state) {
                case Clear: {
                    DCState = State.Clear;
                    Log.i("State", "Clear");
                    DCButton.PressedOff();
                    for (DCButton i : Union) {
                        if(i.IsPressed())
                            i.setPressedWithNoSound();
                        i.Activate();
                    }
                    if (Ready.IsPressed())
                        Ready.setPressedwithNoSound();
                    if (Start.IsPressed())
                        Start.setPressedwithNoSound();
                    if (Stop.IsPressed())
                        Stop.setPressedwithNoSound();
                    Ready.Deactivate();
                    Start.Deactivate();
                    Stop.Deactivate();
                    break;
                }
                case StartSetting: {
                    Log.i("State", "StartSetting");
                    DCState = State.StartSetting;
                    for (DCButton i : Union) {
                        if (i != DCButton.PressedButton) {
                            i.Deactivate();
                        }
                    }
                    Ready.Deactivate();
                    Start.Deactivate();
                    if (Stop != null)
                        Stop.Deactivate();
                    Up.Deactivate();
                    Down.Deactivate();

                    break;
                }
                case Setted: {
                    Log.i("State", "Setted");
                    DCState = State.Setted;
                    for (DCButton i : Union) {
                        i.Activate();
                    }
                    Ready.Activate();
                    if (Start != null)
                        Start.Deactivate();
                    if (Stop != null)
                        Stop.Activate();
                    Up.Activate();
                    Down.Activate();
                    break;
                }
                case Ready:
                    Log.i("State", "Ready");
                    DCState = State.Ready;
                    for (DCButton i : Union) {
                        if (i != DCButton.PressedButton) {
                            i.Deactivate();
                        }
                    }
                    Ready.Activate();
                    if (!Ready.IsPressed())
                        Ready.setPressedwithNoSound();
                    Start.Deactivate();
                    Stop.Activate();
                    break;
                case Excercise:
                    Log.i("State", "Excercise");
                    DCState = State.Excercise;
                    for (DCButton i : Union) {
                        if (i != DCButton.PressedButton) {
                            i.Deactivate();
                        }
                    }
                    Stop.Activate();
                    Ready.Activate();
                    if (!Ready.IsPressed())
                        Ready.setPressedwithNoSound();
                    Start.Activate();
                    Up.Deactivate();
                    Down.Deactivate();
                    break;
                case onRest:
                    Log.i("State", "onRest");
                    DCState = State.onRest;
                    for (DCButton i : Union) {
                        if (i != DCButton.PressedButton) {
                            i.Deactivate();
                        }
                    }
                    Ready.Deactivate();
                    Start.Deactivate();
                    Stop.Activate();
                    break;
                case Paused: {
                    Log.i("State", "Paused");
                    DCState = State.Paused;
                    for (DCButton i : Union) {
                        if (i != DCButton.PressedButton) {
                            i.Deactivate();
                        }
                    }
                    Stop.Activate();
                    Ready.Activate();
                    if (!Ready.IsPressed())
                        Ready.setPressedwithNoSound();
                    Start.Activate();
                    break;
                }
                case Stop: {
                    DCState = State.Stop;
                    for (DCButton i : Union) {
                        if (i != DCButton.PressedButton) {
                            i.Activate();
                        }
                    }
                    if (Ready.isPressed)
                        Ready.setPressedwithNoSound();
                    if (Start.isPressed)
                        Start.setPressedwithNoSound();
                    break;
                }
            }
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }


}
