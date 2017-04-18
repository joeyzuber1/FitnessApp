package com.hevs.gym.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivitiy extends AppCompatActivity {

    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_activitiy);
    }

    /**
     * open creation exercis
     *
     */
    public void creatExersis(View v){
        Intent intent = new Intent(this, CreatExcersisActivity.class);
        startActivity(intent);
    }

    /**
     * show all categories
     *
     */
    public void showExersiseCatagory(View v){
        CallMainActivitys.showExersiseCatagory(v, this);
    }

    /**
     * show all categories from my plan
     *
     */
    public void showExersisePlan(View v){

        CallMainActivitys.showExersisePlan(v, this);
    }

    /**
     * go to make a User to an Admin
     *
     */

    public void Users(View v){
        Intent intent = new Intent(this, MakeAdmin.class);
        startActivity(intent);
    }
    /**
     * go back
     *
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * open the group activitys
     *
     */
    public void showGroups(View v){
        Intent intent = new Intent(this, MyGroupsActivity.class);
        startActivity(intent);
    }


}
