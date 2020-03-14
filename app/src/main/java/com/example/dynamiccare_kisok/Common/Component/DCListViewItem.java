package com.example.dynamiccare_kisok.Common.Component;

import com.example.dynamiccare_kisok.Common.Object.Workout;

public class DCListViewItem {
    String exc_title, exc_content;
    Workout workout;

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public String getExc_title() {
        return exc_title;
    }

    public void setExc_title(String exc_title) {
        this.exc_title = exc_title;
    }

    public String getExc_content() {
        return exc_content;
    }

    public void setExc_content(String exc_content) {
        this.exc_content = exc_content;
    }
}