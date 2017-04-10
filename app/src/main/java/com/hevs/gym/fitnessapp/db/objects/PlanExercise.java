package com.hevs.gym.fitnessapp.db.objects;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class PlanExercise {
    private int planExerciseID;
    private int exerciseID;
    private int planID;

    public int getPlanExerciseID() {
        return planExerciseID;
    }

    public void setPlanExerciseID(int planExerciseID) {
        this.planExerciseID = planExerciseID;
    }

    public int getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(int exerciseID) {
        this.exerciseID = exerciseID;
    }

    public int getPlanID() {
        return planID;
    }

    public void setPlanID(int planID) {
        this.planID = planID;
    }


}
