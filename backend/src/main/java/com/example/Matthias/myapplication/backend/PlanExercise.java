package com.example.Matthias.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

@Entity
public class PlanExercise {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
