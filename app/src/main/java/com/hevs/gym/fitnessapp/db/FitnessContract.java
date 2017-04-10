package com.hevs.gym.fitnessapp.db;

import android.provider.BaseColumns;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class FitnessContract {

    public FitnessContract(){
        //empty constructor
        //should never be instantiated
    }

    //Represents the rows of a table
    public static abstract class UserEntry implements BaseColumns{
        //Table name
        public static final String TABLE_USER = "User";

        //Person Column names
        public static final String KEY_USERID = "UserID";
        public static final String KEY_NAMELOGIN = "Namelogin";
        public static final String KEY_PASSWORD = "Password";
        public static final String KEY_FIRSTNAME = "Firstname";
        public static final String KEY_LASTNAME = "Lastname";
        public static final String KEY_ADMINISTRATOR = "Administrator";
        public static final String KEY_ISMALE= "IsMale";

        //Table person create statement
        public static final String CREATE_TABLE_USER = "CREATE TABLE "
                + TABLE_USER + "("
                + UserEntry.KEY_USERID + " INTEGER PRIMARY KEY,"
                + UserEntry.KEY_NAMELOGIN + " TEXT, "
                + UserEntry.KEY_PASSWORD + " TEXT, "
                + UserEntry.KEY_FIRSTNAME + " TEXT, "
                + UserEntry.KEY_LASTNAME+ " TEXT, "
                + UserEntry.KEY_ADMINISTRATOR + " INTEGER, "
                + UserEntry.KEY_ISMALE + " INTEGER "
                + ");";


    }


}
