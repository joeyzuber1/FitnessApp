package com.hevs.gym.fitnessapp.db.objects;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class GroupUser {
    private int groupUserID;
    private int userID;
    private int groupID;

    public int getGroupUserID() {
        return groupUserID;
    }

    public void setGroupUserID(int groupUserID) {
        this.groupUserID = groupUserID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
}
