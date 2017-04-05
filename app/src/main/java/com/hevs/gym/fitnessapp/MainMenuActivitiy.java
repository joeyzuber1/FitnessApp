package com.hevs.gym.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivitiy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_activitiy);
    }

    public void creatExersis(View v){
        Intent intent = new Intent(this, CreatExcersisActivity.class);
        startActivity(intent);
    }

    public void showExersiseCatagory(View v){
        Intent inten = new Intent(this, ExcersisesCatActivity.class);
        inten.putExtra("Titel", "Excersises");
        String[] stringArray = getResources().getStringArray(R.array.cat_array);
        inten.putExtra("buttonArray", stringArray);
        startActivity(inten);
    }

    public void showExersisePlan(View v){
        Intent inten = new Intent(this, ExcersisesCatActivity.class);
        inten.putExtra("Titel", "My Plan");
        String[] stringArray = getResources().getStringArray(R.array.cat_array);
        inten.putExtra("buttonArray", stringArray);
        startActivity(inten);
    }
}
