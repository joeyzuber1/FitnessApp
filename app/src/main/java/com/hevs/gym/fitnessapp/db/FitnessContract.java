package com.hevs.gym.fitnessapp.db;

import android.provider.BaseColumns;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class FitnessContract {

    public FitnessContract() {
        //empty constructor
        //should never be instantiated
    }
    /**
     *
     *  Represent a user Table
     */
    public static abstract class UserEntry implements BaseColumns {

        //Table User
        public static final String TABLE_USER = "User";

        //Fields user
        public static final String KEY_USERID = "UserID";
        public static final String KEY_NAMELOGIN = "Namelogin";
        public static final String KEY_PASSWORD = "Password";
        public static final String KEY_FIRSTNAME = "Firstname";
        public static final String KEY_LASTNAME = "Lastname";
        public static final String KEY_ADMINISTRATOR = "Administrator";
        public static final String KEY_ISMALE = "IsMale";

        //create user statment
        public static final String CREATE_TABLE_USER = "CREATE TABLE "
                + TABLE_USER + "("
                + UserEntry.KEY_USERID + " INTEGER PRIMARY KEY,"
                + UserEntry.KEY_NAMELOGIN + " TEXT, "
                + UserEntry.KEY_PASSWORD + " TEXT, "
                + UserEntry.KEY_FIRSTNAME + " TEXT, "
                + UserEntry.KEY_LASTNAME + " TEXT, "
                + UserEntry.KEY_ADMINISTRATOR + " INTEGER, "
                + UserEntry.KEY_ISMALE + " INTEGER "
                + ");";
    }

    /**
     * represent a group table
     *
     */
    public static abstract class GroupEntry implements BaseColumns {

        //Table Group
        public static final String TABLE_GROUP = "FitnessGroup";

        //Field group
        public static final String KEY_GROUPID = "GroupID";
        public static final String KEY_GROUPNAME = "Groupname";

        //Create statment table group
        public static final String CREATE_TABLE_GROUP = "CREATE TABLE "
                + TABLE_GROUP + "("
                + GroupEntry.KEY_GROUPID + " INTEGER PRIMARY KEY,"
                + GroupEntry.KEY_GROUPNAME + " TEXT "
                + ");";
    }

    /**
     * Representing the table BodyPart entry
     *
     */
    public static abstract class BodyPartEntry implements BaseColumns {

        //Table Body part
        public static final String TABLE_BODYPART = "BodyPart";

        //Fields body part
        public static final String KEY_PARTOFBODYID = "PartOfBodyID";
        public static final String KEY_BODYSECTION = "BodySection";

        //Create statment table bodypart
        public static final String CREATE_TABLE_BODYPART= "CREATE TABLE "
                + TABLE_BODYPART + "("
                + BodyPartEntry.KEY_PARTOFBODYID + " INTEGER PRIMARY KEY,"
                + BodyPartEntry.KEY_BODYSECTION + " TEXT"
                + ");";
    }

    /**
     *
     * Representing the table Exercise
     */
    public static abstract class ExerciseEntry implements BaseColumns {

        //Table Exercises
        public static final String TABLE_EXERCISE = "Exercise";

        //Field Exercises
        public static final String KEY_EXERCISEID= "ExerciseID";
        public static final String KEY_PARTOFBODYID= "PartOfBodyID";
        public static final String KEY_NAME= "Name";
        public static final String KEY_DESCRITPION= "Description";

        //creat statment table
        public static final String CREATE_TABLE_EXERCISE = "CREATE TABLE "
                + TABLE_EXERCISE + "("
                + ExerciseEntry.KEY_EXERCISEID + " INTEGER PRIMARY KEY,"
                + ExerciseEntry.KEY_NAME + " TEXT, "
                + ExerciseEntry.KEY_DESCRITPION + " TEXT, "
                + ExerciseEntry.KEY_PARTOFBODYID + " INTEGER, "
                + "FOREIGN KEY (" + KEY_PARTOFBODYID + ") REFERENCES " + BodyPartEntry.TABLE_BODYPART + " (" + BodyPartEntry.KEY_PARTOFBODYID +") "
                + ");";
    }

    /**
     * Representing a plan table
     *
     */
    public static abstract class PlanEntry implements BaseColumns {

        //Table Plan
        public static final String TABLE_PLAN = "Plan";

        //Fields plan
        public static final String KEY_PLANID= "PlanID";
        public static final String KEY_USERID= "UserID";
        public static final String KEY_NAME= "Name";

        //create statment plan table
        public static final String CREATE_TABLE_PLAN = "CREATE TABLE "
                + TABLE_PLAN + "("
                + PlanEntry.KEY_PLANID + " INTEGER PRIMARY KEY,"
                + PlanEntry.KEY_NAME + " TEXT, "
                + PlanEntry.KEY_USERID + " INTEGER, "
                + "FOREIGN KEY (" + KEY_USERID + ") REFERENCES " + UserEntry.TABLE_USER + " (" + UserEntry.KEY_USERID +") "
                + ");";
    }

    /**
     * Representing the Group user table
     *
     */
    public static abstract class GroupUsersEntry implements BaseColumns {

        //Table Group user
        public static final String TABLE_GROUPUSER = "GroupUsers";

        //Fields Group Users
        public static final String KEY_GROUPUSERID= "GrouUserID";
        public static final String KEY_USERID= "UserID";
        public static final String KEY_GroupID= "GroupID";

        //Create statment table user groups
        public static final String CREATE_TABLE_GROUPUSER= "CREATE TABLE "
                + TABLE_GROUPUSER + "("
                + GroupUsersEntry.KEY_GROUPUSERID + " INTEGER PRIMARY KEY,"
                + GroupUsersEntry.KEY_USERID + " INTEGER, "
                + GroupUsersEntry.KEY_GroupID + " INTEGER, "
                + "FOREIGN KEY (" + KEY_USERID + ") REFERENCES " + UserEntry.TABLE_USER + " (" + UserEntry.KEY_USERID +"), "
                + "FOREIGN KEY (" + KEY_GroupID + ") REFERENCES " + GroupEntry.TABLE_GROUP + " (" + GroupEntry.KEY_GROUPID +") "
                + ");";
    }

    /**
     * Representing the Plan exercise Entry
     *
     */
    public static abstract class PlanExerciseEntry implements BaseColumns {

        //Table Plan Exercises
        public static final String TABLE_PLANEXERCISE= "PlanExercise";

        //Field Plan exercises
        public static final String KEY_PLANEXERCISEID= "PlanExerciseID";
        public static final String KEY_PLANID= "PlanID";
        public static final String KEY_EXERCISEID= "ExerciseID";

        //create statment plan exercises table
        public static final String CREATE_TABLE_PLANEXERCISE = "CREATE TABLE "
                + TABLE_PLANEXERCISE + "("
                + PlanExerciseEntry.KEY_PLANEXERCISEID + " INTEGER PRIMARY KEY,"
                + PlanExerciseEntry.KEY_PLANID + " INTEGER, "
                + PlanExerciseEntry.KEY_EXERCISEID + " INTEGER, "
                + "FOREIGN KEY (" + KEY_PLANID + ") REFERENCES " + PlanEntry.TABLE_PLAN + " (" + PlanEntry.KEY_PLANID +"), "
                + "FOREIGN KEY (" + KEY_EXERCISEID + ") REFERENCES " + ExerciseEntry.TABLE_EXERCISE + " (" + ExerciseEntry.KEY_EXERCISEID +") "
                + ");";
    }
}


