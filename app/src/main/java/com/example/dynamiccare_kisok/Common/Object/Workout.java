package com.example.dynamiccare_kisok.Common.Object;

import com.example.dynamiccare_kisok.Common.Excercise.Excercise;

public class Workout {
    boolean isWorkout = false, isKinetic = false;
    Excercise excercise;
    int Weight = 0, Reps = 0,Set=0;

    public Workout(boolean isWorkout, boolean isKinetic, Excercise excercise, int weight, int reps, int set) {
        this.isWorkout = isWorkout;
        this.isKinetic = isKinetic;
        this.excercise = excercise;
        Weight = weight;
        Reps = reps;
        Set = set;
    }

    public Excercise getExcercise() {
        return excercise;
    }

    public void setExcercise(Excercise excercise) {
        this.excercise = excercise;
    }

    public int getSet() {
        return Set;
    }

    public void setSet(int set) {
        Set = set;
    }

    public boolean isWorkout() {
        return isWorkout;
    }

    public void setWorkout(boolean workout) {
        isWorkout = workout;
    }

    public String isKinetic() {
        return isKinetic ? "등속성" : "일반";
    }
    public boolean getIsKinetic()
    {
        return isKinetic;
    }

    public void setKinetic(boolean kinetic) {
        isKinetic = kinetic;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }

    public int getReps() {
        return Reps;
    }

    public void setReps(int reps) {
        Reps = reps;
    }

    public boolean equals(Workout target)
    {
             if(target.getExcercise()== getExcercise() &&
                target.getIsKinetic()== getIsKinetic() &&
                target.getWeight()== getWeight() &&
                target.getReps()== getReps() &&
                target.getSet()== getSet())
                    return true;
             else
                 return false;

    }
}
