package com.hevs.gym.fitnessapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ExersisesActivity extends AppCompatActivity {


    private ArrayList<Button> buttonList;
    private int[] exIDs;
    private boolean isMyPlan;
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exersises);


        isMyPlan = getIntent().getBooleanExtra("isMyPlan", false);
        idUser = getIntent().getIntExtra("idUser", -1);
        exIDs =new int[]{1,2,3,1,2,3,1,2,3,1,2,3,1,2,3}; //später db

        generateButtons();
    }

    //Diese klasse kann abgeändert werden da exIDs übergeben werden
    private void generateButtons() {
        buttonList = new ArrayList<Button>();
        ((TextView) findViewById(R.id.titelCatExcersis)).setText(getIntent().getStringExtra("Titel"));
        Bundle extras = getIntent().getExtras();
        String[] buttonsReceived = getResources().getStringArray(R.array.back_array);//später db

        String[] buttons = buttonsReceived;

        LinearLayout ll = (LinearLayout) findViewById(R.id.mainExersises);
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
                    ExersisesActivity.this.onClick(v, index);
                }
            });
            ll.addView(b);
            buttonList.add(b);
        }
    }

    //onclicklistener
    public void onClick(View v, int index){
        CallMainActivitys.showExersise(v, this, exIDs[index]);
    }

    //show all
    public void showExercisesCat(View v){
        CallMainActivitys.showExersiseCatagory(v, this);
    }


    //showpersonal plan
    public void showMyPlan(View v){
        CallMainActivitys.showExersisePlan(v, this);
    }

    //go back
    @Override
    public void onBackPressed() {
        if (isMyPlan){
           Button b = (Button) findViewById(R.id.btn_plan);
            b.performClick();
        }else
        {
            Button b = (Button) findViewById(R.id.btn_excat);
            b.performClick();
        }
    }


}
