package com.example.dynamiccare_kisok.Common.Excercise;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.example.dynamiccare_kisok.Activity.Main;

public abstract class Excercise {
    Main main;
    public Excercise(Main main)
    {
        this.main = main;
    }
    public abstract void getVideo();
    public abstract String getInstruction();
    public abstract String getSimpleName();
    public abstract String getMuscleName();
    public abstract Drawable getMappingBody();
    public abstract Uri getVideoUri();
}
