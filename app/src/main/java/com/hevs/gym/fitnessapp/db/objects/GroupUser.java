package com.hevs.gym.fitnessapp.db.objects;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class GroupUser {
    private long groupUserID;
    private long userID;
    private long groupID;

    public long getGroupUserID() {
        return groupUserID;
    }

    public void setGroupUserID(long groupUserID) {
        this.groupUserID = groupUserID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getGroupID() {
        return groupID;
    }

    public void setGroupID(long groupID) {
        this.groupID = groupID;
    }
}
