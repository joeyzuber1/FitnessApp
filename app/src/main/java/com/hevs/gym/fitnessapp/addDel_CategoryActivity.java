package com.hevs.gym.fitnessapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hevs.gym.fitnessapp.db.adabter.BodyPartDataSource;
import com.hevs.gym.fitnessapp.db.adabter.ExerciseDataSource;
import com.hevs.gym.fitnessapp.db.adabter.PlanDataSource;
import com.hevs.gym.fitnessapp.db.objects.BodyPart;
import com.hevs.gym.fitnessapp.db.objects.Exercise;

import java.util.ArrayList;
import java.util.List;

public class addDel_CategoryActivity extends AppCompatActivity {

    private List<BodyPart> bodyParts;
    private List<Exercise> exercises;
    View v;
    public int counter;

    BodyPartDataSource bodyPartDataSource = new BodyPartDataSource(this);
    ExerciseDataSource exerciseDataSource = new ExerciseDataSource(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_del__category);

    }
    /**
     * Add a Category
     *
     */
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

                            }
                        }).setNegativeButton("Yes", null).show(); //hardcoded

            }
        }
        if (exist == false) {
            BodyPart bpcategory = new BodyPart();
            bpcategory.setBodySection(name);
            bodyPartDataSource.createBodyPart(bpcategory);
            CallMainActivitys.showExersiseCatagory(v, this);

        }
    }

    /**
     * Del a Category
     *
     */
    public void delCat(View v) {
        this.v =v;

        boolean exist = false;
        String name = (((EditText) findViewById(R.id.in_catname)).getText().toString());

        BodyPartDataSource bodyPartDataSource = new BodyPartDataSource(this);
        ExerciseDataSource exerciseDataSource = new ExerciseDataSource(this);
        exercises = exerciseDataSource.getAllExercises();

        bodyParts = bodyPartDataSource.getAllBodyParts();

        for (int i = 0; i< bodyParts.size(); i++,counter++)
        {
            if (bodyParts.get(i).getBodySection().toString().equals(name)){
                long idbodyparts= bodyParts.get(i).getPartOfBodyID();

                //Matthias info(Das wäre die überprüfung das es  noch exercise gibt und es deshalb nicht funktioiert geht irgend was nicht)

             /*   for (int y = 0; i< exercises.size(); y++){

                    if (exercises.get(y).getBodyPart() == idbodyparts){
                        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                                .setMessage("There are still Exercises in this Section pls delete them first?")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() { //Hardcoded
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        CallMainActivitys.showExersiseCatagory(v, this);
                    }
                }
                */

                    exist = true;
                bodyPartDataSource.deleteBodyPart(bodyParts.get(i).getPartOfBodyID());
                CallMainActivitys.showExersiseCatagory(addDel_CategoryActivity.this.v, addDel_CategoryActivity.this);

                //problem ist es nimmt den counter nicht
                /*    new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                            .setMessage("Are you sure you want to delete thid bodypart?")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() { //Hardcoded
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Es gibt falschen counter
                                    addDel_CategoryActivity.this.bodyPartDataSource.deleteBodyPart(bodyParts.get(addDel_CategoryActivity.this.counter).getPartOfBodyID());
                                    CallMainActivitys.showExersiseCatagory(addDel_CategoryActivity.this.v, addDel_CategoryActivity.this);
                                }
                            }).setNegativeButton("no", null).show(); //hardcoded
                            */
                }
        }
      /*  counter=0;
      */

        if(exist==false){

            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                    .setMessage("This bodysection don't exist. Do you wanna continue?")
                    .setPositiveButton("No", new DialogInterface.OnClickListener() { //Hardcoded
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CallMainActivitys.showExersiseCatagory(addDel_CategoryActivity.this.v, addDel_CategoryActivity.this);
                        }
                    }).setNegativeButton("Yes", null).show(); //hardcoded
        }

    }


}
