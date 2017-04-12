package com.hevs.gym.fitnessapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hevs.gym.fitnessapp.db.adabter.ExerciseDataSource;
import com.hevs.gym.fitnessapp.db.adabter.PlanDataSource;
import com.hevs.gym.fitnessapp.db.objects.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExersisesActivity extends AppCompatActivity {


    private ArrayList<Button> buttonList;
    private boolean isMyPlan;
    private long idUser;
    private long idBodyPart;
    private List<Exercise> exercises;
    private List<Long> idExs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exersises);


        isMyPlan = getIntent().getBooleanExtra("isMyPlan", false);
        idUser = getIntent().getLongExtra("idUser", -1);
        idBodyPart = getIntent().getLongExtra("idBodyPart", -1);
        ExerciseDataSource exerciseDataSource = new ExerciseDataSource(this);
        exercises = exerciseDataSource.getAllExercises();

        generateButtons();
    }

    /**
     * generate the buttons based on the userid and the plan id
     *
     */
    private void generateButtons() {
        buttonList = new ArrayList<Button>();
        idExs = new ArrayList<Long>();
        ((TextView) findViewById(R.id.titelCatExcersis)).setText(getIntent().getStringExtra("Titel"));
        Bundle extras = getIntent().getExtras();
        String[] buttonsReceived;

        ExerciseDataSource exerciseDataSource = new ExerciseDataSource(this);
        if (!isMyPlan)
        {
            if (idBodyPart != -1) {
                exercises = exerciseDataSource.getAllExercisesFromBodyPartID(idBodyPart);
            }else
            {
                exercises = exerciseDataSource.getAllExercises();
            }

        }else
        {
            PlanDataSource planDataSource = new PlanDataSource(this);
            long planID = planDataSource.getPlanFromUserID(idUser).get(0).getPlanID();
            if (idBodyPart == -1) {
                exercises = exerciseDataSource.getExerciseByPlanID(planID);
            }else
            {
                exercises = exerciseDataSource.getExerciseByPlanIDAndBodyPartID(planID, idBodyPart);
            }
        }

        buttonsReceived = new String[exercises.size()];
        for (int i = 0; i < buttonsReceived.length; i++) {
            buttonsReceived[i] = exercises.get(i).getExerciseName();
            idExs.add(exercises.get(i).getExerciseID());
        }

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

    /**
     * if someone click oon a ex. this will wur
     *
     */
    public void onClick(View v, int index){
        CallMainActivitys.showExersise(v, this, idExs.get(index));
    }

    /**
     * Show all categories from all exercises
     *
     */
    public void showExercisesCat(View v){
        CallMainActivitys.showExersiseCatagory(v, this);
    }

    /**
     * run when the user returns
     *
     */
    @Override
    protected void onResume() {
        super.onResume();

        if(((LinearLayout) findViewById(R.id.mainExersises)).getChildCount() > 0)
            ((LinearLayout) findViewById(R.id.mainExersises)).removeAllViews();

        generateButtons();
    }

    /**
     * Show all categories from my plan
     *
     */
    public void showMyPlan(View v){
        CallMainActivitys.showExersisePlan(v, this);
    }

    /**
     * return
     *
     */
    @Override
    public void onBackPressed() {
        if (isMyPlan && idUser == UserInfos.getUserID()){
           Button b = (Button) findViewById(R.id.btn_plan);
            b.performClick();
        }else
        {
            super.onBackPressed();
        }
    }


}
