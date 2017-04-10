package com.hevs.gym.fitnessapp.db.objects;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class User {
    private int userID;
    private String namelogin;
    private String password;
    private String firstname;
    private String lastname;
    private boolean administrator;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getNamelogin() {
        return namelogin;
    }

    public void setNamelogin(String namelogin) {
        this.namelogin = namelogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    private boolean isMale;


}
