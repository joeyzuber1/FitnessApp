package com.example.Matthias.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

@Entity
public class Group {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long groupID;
    private String groupname;

    public long getGroupID() {
        return groupID;
    }

    public void setGroupID(long groupID) {
        this.groupID = groupID;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }



}
