package com.example.Matthias.myapplication.backend;

/**
 * Created by Matthias and Joey on 09.05.2017.
 */

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;


@Entity
public class BodyPart{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long partOfBodyID;

    public long getPartOfBodyID() {
        return partOfBodyID;
    }

    public void setPartOfBodyID(long partOfBodyID) {
        this.partOfBodyID = partOfBodyID;
    }

    public String getBodySection() {
        return bodySection;
    }

    public void setBodySection(String bodySection) {
        this.bodySection = bodySection;
    }

    private String bodySection;

}
