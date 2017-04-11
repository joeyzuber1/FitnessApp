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

    //create exersis
    public void creatExersis(View v){
        Intent intent = new Intent(this, CreatExcersisActivity.class);
        startActivity(intent);
    }

    //show all
    public void showExersiseCatagory(View v){
        CallMainActivitys.showExersiseCatagory(v, this);
    }

    //show personal plan
    public void showExersisePlan(View v){
        CallMainActivitys.showExersisePlan(v, this);
    }

    //go back
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //show my groups
    public void showGroups(View v){
        Intent intent = new Intent(this, MyGroupsActivity.class);
        startActivity(intent);
    }
}
