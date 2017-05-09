package com.example.Matthias.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

@Entity
public class GroupUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
