package com.hevs.gym.fitnessapp.db.objects;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class Plan {
    private long planID;
    private long userID;

    public long getPlanID() {
        return planID;
    }

    public void setPlanID(long planID) {
        this.planID = planID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    private String planName;

}
