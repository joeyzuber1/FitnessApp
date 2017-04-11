package com.hevs.gym.fitnessapp.db.objects;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class PlanExercise {
    private long planExerciseID;
    private long exerciseID;
    private long planID;

    public long getPlanExerciseID() {
        return planExerciseID;
    }

    public void setPlanExerciseID(long planExerciseID) {
        this.planExerciseID = planExerciseID;
    }

    public long getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(long exerciseID) {
        this.exerciseID = exerciseID;
    }

    public long getPlanID() {
        return planID;
    }

    public void setPlanID(long planID) {
        this.planID = planID;
    }


}
