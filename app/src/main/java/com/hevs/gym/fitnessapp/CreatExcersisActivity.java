package com.hevs.gym.fitnessapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hevs.gym.fitnessapp.db.adabter.BodyPartDataSource;
import com.hevs.gym.fitnessapp.db.adabter.ExerciseDataSource;
import com.hevs.gym.fitnessapp.db.adabter.UserDataSource;
import com.hevs.gym.fitnessapp.db.objects.BodyPart;
import com.hevs.gym.fitnessapp.db.objects.Exercise;
import com.hevs.gym.fitnessapp.db.objects.User;

import java.util.ArrayList;
import java.util.List;


public class CreatExcersisActivity extends AppCompatActivity {
    ExerciseDataSource exerciseDataSource;
    List<BodyPart> bodyParts;
    Spinner sBodyParts;
    long exid;
    boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Preferences
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        setTheme(SettingInfos.getResource(sharedPrefs.getString("pref_lang", "18")));
        setTheme(SettingInfos.getResourceColor(sharedPrefs.getString("pref_color", "black")));
        setContentView(R.layout.activity_creat_excersis);

        long idBodyPart = getIntent().getLongExtra("idBodyPart", 0);
        isUpdate = getIntent().getBooleanExtra("update", false);
        exid = getIntent().getLongExtra("exersiseID", 0);

        //Spinner
        List<String> spinnerArray = new ArrayList<String>();
        BodyPartDataSource bodyPartDataSource = new BodyPartDataSource(this);
        bodyParts = bodyPartDataSource.getAllBodyParts();

        for (BodyPart bp : bodyParts) {
            spinnerArray.add(bp.getBodySection());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sBodyParts = (Spinner) findViewById(R.id.spin_bodyPart);
        sBodyParts.setAdapter(adapter);
        for (int i = 0; i < bodyParts.size(); i++) {
            if (bodyParts.get(i).getPartOfBodyID() == idBodyPart) {
                sBodyParts.setSelection(i);
            }
        }
        //Spinner end

        exerciseDataSource = new ExerciseDataSource(this);
        List<Exercise> exercise = exerciseDataSource.getAllExercises();
        Button create = (Button) findViewById(R.id.creatEx);
        Button updateCreate = (Button) findViewById(R.id.updatecreate);
        create.setVisibility(View.VISIBLE);
        updateCreate.setVisibility(View.INVISIBLE);
        /**
         *  2te View for updating the exercise
         *
         */

        for (int i = 0; i < exercise.size(); i++) {
            if (exercise.get(i).getExerciseID() == exid) {
                create.setVisibility(View.INVISIBLE);
                updateCreate.setVisibility(View.VISIBLE);

                ((EditText) findViewById(R.id.in_exname)).setText(exercise.get(i).getExerciseName());
                ((EditText) findViewById(R.id.in_exdes)).setText(exercise.get(i).getExerciseDescription());

            }
        }
    }


    /**
     * if you click create this method will be called
     */
    public void createEx(View v) {
        long bodyID = -1;
        String selected = sBodyParts.getSelectedItem().toString();
        for (BodyPart bp : bodyParts) {
            if (selected.equals(bp.getBodySection())) {
                bodyID = bp.getPartOfBodyID();
            }
        }
        ExerciseDataSource exerciseDataSource = new ExerciseDataSource(this);
        Exercise ex = new Exercise();
        if (((EditText) findViewById(R.id.in_exname)).getText().length() > 0 && ((EditText) findViewById(R.id.in_exdes)).getText().length() > 0 && bodyID > -1) {

            ex.setExerciseName(((EditText) findViewById(R.id.in_exname)).getText().toString());
            ex.setExerciseDescription(((EditText) findViewById(R.id.in_exdes)).getText().toString());
            ex.setBodyPart(bodyID);
            if (!alreadyInserted(ex, false)) {
                exerciseDataSource.createExercise(ex);
                finish();
            }
        } else {
            notAllFilledAlert();
        }
    }

    /**
     * if you click create this method will be called
     */
    public void update(View v) {

        long bodyID = -1;
        String selected = sBodyParts.getSelectedItem().toString();
        for (BodyPart bp : bodyParts) {
            if (selected.equals(bp.getBodySection())) {
                bodyID = bp.getPartOfBodyID();
            }
        }

        if (((EditText) findViewById(R.id.in_exname)).getText().length() > 0 && ((EditText) findViewById(R.id.in_exdes)).getText().length() > 0 && bodyID > -1) {
            Exercise ex = exerciseDataSource.getExerciseById(exid);
            ex.setExerciseName(((EditText) findViewById(R.id.in_exname)).getText().toString());
            ex.setBodyPart(bodyID);
            ex.setExerciseDescription(((TextView) findViewById(R.id.in_exdes)).getText().toString());
            if (!alreadyInserted(ex, true)) {
                exerciseDataSource.updateExercises(ex);
            finish();
            }
        } else {
            notAllFilledAlert();
        }

    }
    /**
     * Checks if an exercise already exists
     */

    private boolean alreadyInserted(Exercise ex, boolean isUpdate)
    {
        List<Exercise> exerciseList = exerciseDataSource.getAllExercisesFromBodyPartID(ex.getBodyPart());
        boolean isIn = false;
        for (Exercise ex2 : exerciseList)
        {
            if (isUpdate)
            {
                if (ex2.getBodyPart() == ex.getBodyPart() && ex2.getExerciseName().equals(ex.getExerciseName()) && ex2.getExerciseID() != ex.getExerciseID())
                {
                    isIn = true;
                }

            }
            if (!isUpdate)
            {
                if (ex2.getBodyPart() == ex.getBodyPart() && ex2.getExerciseName().equals(ex.getExerciseName()))
                {
                    isIn = true;
                }
            }
        }
        if (isIn)
        {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning") //Hardcoded
                    .setMessage("This Exersices is already in this Categorie, you want continue?")//Hardcoded
                    .setPositiveButton("No", new DialogInterface.OnClickListener() { //Hardcoded
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNegativeButton("Yes", null).show(); //hardcoded
        }
        return isIn;
    }

    private void notAllFilledAlert() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning") //Hardcoded
                .setMessage("You didn't fill in all fields. Do you wanna continue with creation?")//Hardcoded
                .setPositiveButton("No", new DialogInterface.OnClickListener() { //Hardcoded
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("Yes", null).show(); //hardcoded
    }


}
