package com.hevs.gym.fitnessapp;

/**
 * Created by Matthias and Joey on 07.04.2017.
 */

class UserInfos {
    private static int userID = -1;


    static int getUserID()
    {
        return  userID;
    }

    static void setUserID(int userIDNew){
        userID = userIDNew;
        //DB
        isAdmin = true;
    }

    private static int planID = -1;

    public static int getPlanID(){
        return planID;
    }

    private static boolean isAdmin = false;

    static boolean isIsAdmin(){
        return isAdmin;
    }
}
