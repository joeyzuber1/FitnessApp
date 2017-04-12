package com.hevs.gym.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.hevs.gym.fitnessapp.db.adabter.BodyPartDataSource;
import com.hevs.gym.fitnessapp.db.adabter.ExerciseDataSource;
import com.hevs.gym.fitnessapp.db.objects.BodyPart;
import com.hevs.gym.fitnessapp.db.objects.Exercise;

import java.util.ArrayList;
import java.util.List;


public class CreatExcersisActivity extends AppCompatActivity {


    List<BodyPart> bodyParts;
    Spinner sBodyParts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_excersis);


        List<String> spinnerArray =  new ArrayList<String>();
        BodyPartDataSource bodyPartDataSource = new BodyPartDataSource(this);
        bodyParts = bodyPartDataSource.getAllBodyParts();

        for (BodyPart bp : bodyParts)
        {
            spinnerArray.add(bp.getBodySection());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sBodyParts = (Spinner) findViewById(R.id.spin_bodyPart);
        sBodyParts.setAdapter(adapter);


    }

    /**
     *  if you click create this method will be called
     *
     */
    public void createEx(View v)
    {
        long bodyID = -1;
        String selected = sBodyParts.getSelectedItem().toString();
        for (BodyPart bp: bodyParts) {
            if (selected.equals(bp.getBodySection())) {
                bodyID = bp.getPartOfBodyID();
            }
        }
        ExerciseDataSource exerciseDataSource = new ExerciseDataSource(this);
        Exercise ex = new Exercise();
        ex.setExerciseName(((EditText) findViewById(R.id.in_exname)).getText().toString());
        ex.setExerciseDescription(((EditText) findViewById(R.id.in_exdes)).getText().toString());
        ex.setBodyPart(bodyID);

        exerciseDataSource.createExercise(ex);
        Intent intent = new Intent(this, MainMenuActivitiy.class);
        startActivity(intent);
    }
}
