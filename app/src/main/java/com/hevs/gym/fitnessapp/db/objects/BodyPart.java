package com.hevs.gym.fitnessapp.db.objects;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class BodyPart {
    private int partOfBodyID;

    public int getPartOfBodyID() {
        return partOfBodyID;
    }

    public void setPartOfBodyID(int partOfBodyID) {
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
