package com.hevs.gym.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.matthias.myapplication.backend.userApi.model.User;
import com.hevs.gym.fitnessapp.db.adabter.UserDataSource;

public class MainMenuActivitiy extends AppCompatActivity {

    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Preferences
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        setTheme(SettingInfos.getResource(sharedPrefs.getString("pref_lang", "18")));
        setTheme(SettingInfos.getResourceColor(sharedPrefs.getString("pref_color", "black")));
        setContentView(R.layout.activity_main_menu_activitiy);
        if (UserInfos.isIsAdmin()==false) {
            Button users = (Button) findViewById(R.id.buttonusers);
            Button createEx = (Button) findViewById(R.id.addnewEx);
            users.setVisibility(View.INVISIBLE);
            createEx.setVisibility(View.INVISIBLE);
        }


        TextView titel = (TextView) findViewById(R.id.id_welcomeMessage);
        User user = new UserDataSource(this).getUserById(UserInfos.getUserID());
        titel.setText(getResources().getString(R.string.titelWelcome)+" " + user.getLastname()+" " +user.getFirstname());
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
    public void showExersisePlans(View v){

        CallMainActivitys.showExersisePlans(v, this, UserInfos.getUserID(), true);
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
