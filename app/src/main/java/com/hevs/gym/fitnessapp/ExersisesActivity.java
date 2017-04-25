package com.hevs.gym.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hevs.gym.fitnessapp.db.adabter.BodyPartDataSource;
import com.hevs.gym.fitnessapp.db.adabter.ExerciseDataSource;
import com.hevs.gym.fitnessapp.db.adabter.PlanDataSource;
import com.hevs.gym.fitnessapp.db.objects.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExersisesActivity extends AppCompatActivity {

    private ArrayList<Button> buttonList;
    private long idPlan;
    private long idBodyPart;
    private long idUser;
    private List<Exercise> exercises;
    private List<Long> idExs;
    private Menu menu;
    private String titel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Preferences
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        setTheme(SettingInfos.getResource(sharedPrefs.getString("pref_lang", "18")));
        setTheme(SettingInfos.getResourceColor(sharedPrefs.getString("pref_color", "black")));
        setContentView(R.layout.activity_exersises);
        idPlan = getIntent().getLongExtra("idPlan", -1);
        idBodyPart = getIntent().getLongExtra("idBodyPart", -1);
        idUser = getIntent().getLongExtra("idUser", -1);
        ExerciseDataSource exerciseDataSource = new ExerciseDataSource(this);
        generateButtons();
    }

    /**
     * generate the buttons based on the userid and the plan id
     *
     */
    private void generateButtons() {
        BodyPartDataSource bodyPartDataSource = new BodyPartDataSource(this);
        buttonList = new ArrayList<Button>();
        idExs = new ArrayList<Long>();
        titel = getIntent().getStringExtra("Titel");
        if(idBodyPart != -1) {
            titel =titel+"->"+bodyPartDataSource.getBodyPartById(idBodyPart).getBodySection(); //Hardcoded
        }else
        {
            titel =titel+getResources().getString(R.string.dialog_allEx);
        }
        ((TextView) findViewById(R.id.titelCatExcersis)).setText(titel);
        Bundle extras = getIntent().getExtras();
        String[] buttonsReceived;

        ExerciseDataSource exerciseDataSource = new ExerciseDataSource(this);
        if (idPlan == -1)
        {
            if (idBodyPart != -1) {
                exercises = exerciseDataSource.getAllExercisesFromBodyPartID(idBodyPart);
            }else
            {
                exercises = exerciseDataSource.getAllExercises();
            }

        }else
        {
            PlanDataSource planDataSource = new PlanDataSource(this);;
            if (idBodyPart == -1) {
                exercises = exerciseDataSource.getExerciseByPlanID(idPlan);
            }else
            {
                exercises = exerciseDataSource.getExerciseByPlanIDAndBodyPartID(idPlan, idBodyPart);
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
            b.setTransformationMethod(null);
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
        CallMainActivitys.showExersisePlans(v, this, UserInfos.getUserID(), true);
    }
    /**
     * return
     *
     */
    @Override
    public void onBackPressed() {
       finish();
    }
    /**
     * top right the menu button
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (UserInfos.isIsAdmin())
        {
            MenuInflater inflator = getMenuInflater();
            inflator.inflate(R.menu.menu_exercisecat, menu);
            this.menu = menu;
        }
        return true;
    }
    /**
     * On click listener for the top right menu
     *
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

           if (id == R.id.menu_createEx) {
               //change
               Intent intent = new Intent(this, CreatExcersisActivity.class);
               intent.putExtra("idBodyPart", idBodyPart);
               startActivity(intent);
               return true;
       }
        return super.onOptionsItemSelected(item);
    }

}
