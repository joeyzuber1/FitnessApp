package com.hevs.gym.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Matthias on 07.04.2017.
 */

public class CallMainActivitys {

    /**
     * Show the exercises split in categories
     *
     */
    public static void showExersiseCatagory(View v, AppCompatActivity previusActivity){
        showExersiseCategoriesFromPlan(v, previusActivity, -1, -1, "", true);//Hardcoded
    }

    /**
     * Show the exercises split in categories from PlanID
     *
     */
    public static void showExersiseCategoriesFromPlan(View v, AppCompatActivity previusActivity, long planID, long idUser, String titel, boolean isFromRandom){
        Intent inten = new Intent(previusActivity, ExcersisesCatActivity.class);
        inten.putExtra("Titel", titel);
        inten.putExtra("planID", planID);
        inten.putExtra("idUser", idUser);
        inten.putExtra("isFromRandom", isFromRandom);
        previusActivity.startActivity(inten);
    }

    /**
     * Show plans from users
     *
     */
    public static void showExersisePlans(View v, AppCompatActivity previusActivity, long idUser, boolean isFromRandom){
        Intent inten = new Intent(previusActivity, PlansActivity.class);
        inten.putExtra("idUser", idUser);
        inten.putExtra("isFromRandom", isFromRandom);
        previusActivity.startActivity(inten);
    }

    public static void showExersisesFromPlan(View v, AppCompatActivity previusActivity, long idPlan, long idBodyPart, long idUser, String titel)
    {
        Intent intent = new Intent(previusActivity, ExersisesActivity.class);
        intent.putExtra("idPlan", idPlan);
        intent.putExtra("idBodyPart", idBodyPart);
        intent.putExtra("idUser", idUser);
        intent.putExtra("Titel", titel);
        previusActivity.startActivity(intent);
    }

    /**
     * Show the exercises split in categories from other user Plan
     *
//     */
//    public static void showExersisePlanFromUser(View v, AppCompatActivity previusActivity, long userID, String username)
//    {
//        Intent inten = new Intent(previusActivity, ExcersisesCatActivity.class);
//        inten.putExtra("Titel", username);
//        inten.putExtra("isMyPlan", true);
//        inten.putExtra("idUser", userID);
//        previusActivity.startActivity(inten);
//    }


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
