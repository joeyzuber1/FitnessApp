package com.hevs.gym.fitnessapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hevs.gym.fitnessapp.db.adabter.BodyPartDataSource;
import com.hevs.gym.fitnessapp.db.adabter.ExerciseDataSource;
import com.hevs.gym.fitnessapp.db.objects.BodyPart;
import com.hevs.gym.fitnessapp.db.objects.Exercise;

import java.util.List;

public class addDel_CategoryActivity extends AppCompatActivity {

    private List<BodyPart> bodyParts;
    private List<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_del__category);

    }


    public void addCat(View v) {

        boolean exist = false;
        String name = (((EditText) findViewById(R.id.in_catname)).getText().toString());
        BodyPartDataSource bodyPartDataSource = new BodyPartDataSource(this);
        ExerciseDataSource exerciseDataSource = new ExerciseDataSource(this);


        bodyParts = bodyPartDataSource.getAllBodyParts();


        for (int i = 0; i < bodyParts.size(); i++) {
            if (bodyParts.get(i).getBodySection().toString().equals(name)) {
                exist = true;

                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                        .setMessage("This bodysection already exist. Do you wanna continue?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() { //Hardcoded
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                System.exit(0);
                            }
                        }).setNegativeButton("Yes", null).show(); //hardcoded

            }
        }
        if (exist == false) {
            BodyPart bpcategory = new BodyPart();
            bpcategory.setBodySection(name);
            bodyPartDataSource.createBodyPart(bpcategory);
            finish();

        }
    }

    public void delCat(View v) {

        boolean exist = false;
        String name = (((EditText) findViewById(R.id.in_catname)).getText().toString());

        BodyPartDataSource bodyPartDataSource = new BodyPartDataSource(this);
        ExerciseDataSource exerciseDataSource = new ExerciseDataSource(this);
        exercises = exerciseDataSource.getAllExercises();

        bodyParts = bodyPartDataSource.getAllBodyParts();

        for (int i = 0; i< bodyParts.size(); i++)
        {
            if (bodyParts.get(i).getBodySection().toString().equals(name)){
                long idbodyparts= bodyParts.get(i).getPartOfBodyID();

                for (int y = 0; i< exercises.size(); y++){

                    if (exercises.get(y).getBodyPart() == idbodyparts){
                        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                                .setMessage("There are still Exercises in this Section pls delete them first?")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() { //Hardcoded
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                        break;
                    }
                }


                    exist = true;
                    bodyPartDataSource.deleteBodyPart(bodyParts.get(i).getPartOfBodyID());
                    new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                            .setMessage("Are you sure you want to delete thid bodypart?")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() { //Hardcoded
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).setNegativeButton("no", null).show(); //hardcoded
                }


        }
        if(exist==false){

            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                    .setMessage("This bodysection don't exist. Do you wanna continue?")
                    .setPositiveButton("No", new DialogInterface.OnClickListener() { //Hardcoded
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNegativeButton("Yes", null).show(); //hardcoded

        }

    }


}
