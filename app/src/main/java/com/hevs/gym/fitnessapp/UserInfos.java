package com.hevs.gym.fitnessapp;

import com.hevs.gym.fitnessapp.db.adabter.UserDataSource;

/**
 * Created by Matthias and Joey on 07.04.2017.
 */

class UserInfos {
    private static long userID = -1;


    static long getUserID()
    {
        return  userID;
    }

    static void setUserID(long userIDNew){
        userID = userIDNew;
    }

    private static long planID = -1;

    public static long getPlanID(){
        return planID;
    }

    public static void setIsAdmin(boolean isAdmin) {
        UserInfos.isAdmin = isAdmin;
    }

    private static boolean isAdmin = false;

    static boolean isIsAdmin(){
        return isAdmin;
    }
}
