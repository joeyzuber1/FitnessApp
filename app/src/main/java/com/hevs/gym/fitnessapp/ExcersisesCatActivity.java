package com.hevs.gym.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class ExcersisesCatActivity extends AppCompatActivity {

    private ArrayList<Button> buttonList;
    private int idUser;
    private boolean isMyPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excersises_cat);

        generateButtons();
    }

    //Generate the Main Button GridLayout
    private void generateButtons() {
        buttonList = new ArrayList<Button>();
        ((TextView) findViewById(R.id.titelCatExcersis)).setText(getIntent().getStringExtra("Titel"));
        Bundle extras = getIntent().getExtras();

        isMyPlan = extras.getBoolean("isMyPlan");
        idUser = extras.getInt("idUser");

        String[] buttonsReceived;

        if (!isMyPlan) {
            buttonsReceived = getResources().getStringArray(R.array.cat_array); //später db
        } else
        {
            buttonsReceived = getResources().getStringArray(R.array.cat_array); //später db
        }

        String[] buttons = new String[buttonsReceived.length+1];
        buttons[0] = "All Exercises";
        for (int i = 1; i<buttons.length; i++)
        {
            buttons[i] = buttonsReceived[i-1];
        }

        LinearLayout ll = (LinearLayout) findViewById(R.id.mainCatEcersis);
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        for (int i = 0; i<buttons.length; i++)
        {
            Button b = new Button(this);
            b.setText(buttons[i]);
            b.setLayoutParams(lp);
            final int index = i;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExcersisesCatActivity.this.onClick(index);
                }
            });
            ll.addView(b);
            buttonList.add(b);
        }
    }

    //OnClickListener
    public void onClick(int index){
        String[] stringArray = new String[0];
        String titel = getIntent().getStringExtra("Titel");
        if (index == 0)
        {
            //take all
            stringArray = getResources().getStringArray(R.array.back_array);
            titel = titel + " -> "+"All";
        }
        for (int i = 1; i < buttonList.size(); i++){
           if (i == index)
           {
               //Take the right things
               titel = titel + " -> "+buttonList.get(i).getText();
               stringArray = getResources().getStringArray(R.array.back_array);
           }
        }

        if(stringArray.length != 0)
        {
            int[] a = {1,2,3};
            showExercises(titel, stringArray, a); //später array mit den IDs
        }
    }

    //show one Exercis
    public void showExercises(String titel, String[] array, int[] exIDs){
        Intent inten = new Intent(this, ExersisesActivity.class);
        inten.putExtra("Titel", titel); //better need
        inten.putExtra("isMyPlan", isMyPlan);
        startActivity(inten);
    }

    //show all
    public void showExercisesCat(View v){
        CallMainActivitys.showExersiseCatagory(v, this);
    }

    //show personal plan
    public void showMyPlan(View v){
        CallMainActivitys.showExersisePlan(v, this);
    }

    //go back
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainMenuActivitiy.class);
        startActivity(intent);
    }
}
