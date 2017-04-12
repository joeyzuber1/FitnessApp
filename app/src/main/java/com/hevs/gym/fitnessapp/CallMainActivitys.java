package com.hevs.gym.fitnessapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hevs.gym.fitnessapp.db.adabter.UserDataSource;

/**
 * Created by Matthias on 07.04.2017.
 */

public class CallMainActivitys {

    /**
     * Show the exercises split in categories
     *
     */
    public static void showExersiseCatagory(View v, AppCompatActivity previusActivity){
        Intent inten = new Intent(previusActivity, ExcersisesCatActivity.class);
        inten.putExtra("Titel", "Excersises");
        inten.putExtra("isMyPlan", false);
        inten.putExtra("idUser", -1); //-1 == All
        previusActivity.startActivity(inten);
    }

    /**
     * Show the exercises split in categories from user Plan
     *
     */
    public static void showExersisePlan(View v, AppCompatActivity previusActivity){
        Intent inten = new Intent(previusActivity, ExcersisesCatActivity.class);
        inten.putExtra("Titel", "My Plan");
        inten.putExtra("isMyPlan", true);
        inten.putExtra("idUser", UserInfos.getUserID());
        previusActivity.startActivity(inten);
    }

    /**
     * Show the exercises split in categories from other user Plan
     *
     */
    public static void showExersisePlanFromUser(View v, AppCompatActivity previusActivity, long userID, String username)
    {
        Intent inten = new Intent(previusActivity, ExcersisesCatActivity.class);
        inten.putExtra("Titel", username);
        inten.putExtra("isMyPlan", true);
        inten.putExtra("idUser", userID);
        previusActivity.startActivity(inten);
    }
    /**
     * Show the exercises split in categories from user Plan
     *
     */
    public static void showExersise(View v, AppCompatActivity previusActivity, long exID)
    {
        Intent intent =  new Intent(previusActivity, ShowActivity.class);
        intent.putExtra("exID", exID);
        previusActivity.startActivity(intent);
    }



}
