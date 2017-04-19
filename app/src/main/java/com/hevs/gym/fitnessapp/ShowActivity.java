package com.hevs.gym.fitnessapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hevs.gym.fitnessapp.db.adabter.ExerciseDataSource;
import com.hevs.gym.fitnessapp.db.adabter.PlanDataSource;
import com.hevs.gym.fitnessapp.db.adabter.PlanExerciseDataSource;
import com.hevs.gym.fitnessapp.db.objects.Exercise;
import com.hevs.gym.fitnessapp.db.objects.Plan;
import com.hevs.gym.fitnessapp.db.objects.PlanExercise;
import com.hevs.gym.fitnessapp.db.objects.User;

import java.util.List;

public class ShowActivity extends AppCompatActivity {

    private long exID;
    private boolean isInMyPlan;
    private Menu menu;
    private boolean update = false;
    private  ExerciseDataSource exerciseDataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
         exerciseDataSource = new ExerciseDataSource(this);


        exID = getIntent().getLongExtra("exID", -1);

        if (exID >= 0)
        {
            setTitelAndDescription();
        }

        isInMyPlan = false;
        PlanDataSource planDataSource = new PlanDataSource(this);
        UserInfos.setPlanID(planDataSource.getPlanFromUserID(UserInfos.getUserID()).get(0).getPlanID());
        long planID = UserInfos.getPlanID();
        PlanExerciseDataSource planExerciseDataSource = new PlanExerciseDataSource(this);
        List<PlanExercise> planExercises = planExerciseDataSource.getAllPlanExercise();
        for (PlanExercise pe : planExercises)
        {
            if (pe.getPlanID() == planID && pe.getExerciseID() == exID)
            {
                isInMyPlan = true;
            }
        }
    }

    /**
     * Change the titel and description base on the Exercise
     *
     */
    private void setTitelAndDescription(){
        ExerciseDataSource exerciseDataSource = new ExerciseDataSource(this);
        Exercise ex = exerciseDataSource.getExerciseById(exID);

        TextView titel = (TextView) findViewById(R.id.titelExercise);
        titel.setText(ex.getExerciseName());
        TextView description = (TextView) findViewById(R.id.descriptionExercise);
        description.setText(ex.getExerciseDescription());
    }

    /**
     * show all categories from all exercises
     *
     */
    public void showExercisesCat(View v){
        CallMainActivitys.showExersiseCatagory(v, this);
    }


    /**
     * show all categroies from my plan
     *
     */
    public void showMyPlan(View v){
        CallMainActivitys.showExersisePlan(v, this);
    }


    /**
     * top right the menu button
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.menu_exercise, menu);
        this.menu = menu;
        updateMenuItem();
        return true;
    }

    /**
     * On click listener for the top right menu
     *
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
       if(UserInfos.isIsAdmin()==true) {
           if (id == R.id.menu_addDelete) {
               PlanExerciseDataSource planExerciseDataSource = new PlanExerciseDataSource(this);
               long planID = UserInfos.getPlanID();
               if (isInMyPlan) {
                   List<PlanExercise> planExercises = planExerciseDataSource.getAllPlanExercise();//Verbessern nicht alles
                   for (PlanExercise pe : planExercises) {
                       if (pe.getPlanID() == planID && pe.getExerciseID() == exID) {
                           planExerciseDataSource.deletePlanExercise(pe.getPlanExerciseID());
                       }
                   }
               } else {
                   PlanExercise pe = new PlanExercise();
                   pe.setExerciseID(exID);
                   pe.setPlanID(planID);
                   planExerciseDataSource.createPlanExercise(pe);
               }
               isInMyPlan = !isInMyPlan;
               updateMenuItem();
               return true;
           }
           if (id == R.id.menu_change) {
               update=true;
               Intent intent = new Intent(this, CreatExcersisActivity.class);
               intent.putExtra("exersiseID", exID);
               intent.putExtra("update", update);
               startActivity(intent);
               return true;
           }
           if (id == R.id.menu_delete) {
               //delete
               exerciseDataSource.deleteExercise(exID);
               /* Intent intent = new Intent(this, ExcersisesCatActivity.class);
               startActivity(intent); */
               finish();

                   new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.dialog_t_exit))
                           .setMessage("you just deleted this Exercise")
                           .setPositiveButton("ok", new DialogInterface.OnClickListener() { //Hardcoded
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   finish();
                                   System.exit(0);
                               }
                           });
               }

               return true;
           }
       else{
           if (id == R.id.menu_addDelete) {
               PlanExerciseDataSource planExerciseDataSource = new PlanExerciseDataSource(this);
               long planID = UserInfos.getPlanID();
               if (isInMyPlan) {
                   List<PlanExercise> planExercises = planExerciseDataSource.getAllPlanExercise();//Verbessern nicht alles
                   for (PlanExercise pe : planExercises) {
                       if (pe.getPlanID() == planID && pe.getExerciseID() == exID) {
                           planExerciseDataSource.deletePlanExercise(pe.getPlanExerciseID());
                       }
                   }
               } else {
                   PlanExercise pe = new PlanExercise();
                   pe.setExerciseID(exID);
                   pe.setPlanID(planID);
                   planExerciseDataSource.createPlanExercise(pe);
               }
               isInMyPlan = !isInMyPlan;
               updateMenuItem();
               return true;
           }
       }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Change the menu buttons
     *
     */
    private void updateMenuItem(){
        MenuItem addDel = menu.findItem(R.id.menu_addDelete);
        if (isInMyPlan){
            addDel.setTitle(getResources().getString(R.string.menu_deletP));
        }else
        {
            addDel.setTitle(getResources().getString(R.string.menu_add));
        }

        if (UserInfos.isIsAdmin())
        {
           menu.setGroupVisible(R.id.menuAdminGroup, true);
        }
    }
}
