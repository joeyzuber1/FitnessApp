package com.hevs.gym.fitnessapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hevs.gym.fitnessapp.db.adabter.ExerciseDataSource;
import com.hevs.gym.fitnessapp.db.adabter.PlanDataSource;
import com.hevs.gym.fitnessapp.db.adabter.PlanExerciseDataSource;
import com.hevs.gym.fitnessapp.db.objects.Exercise;
import com.hevs.gym.fitnessapp.db.objects.Plan;
import com.hevs.gym.fitnessapp.db.objects.PlanExercise;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity {

    private long exID;
    private Menu menu;
    private boolean update = false;
    private List<Plan> plans;
    private ExerciseDataSource exerciseDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Preferences
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        setTheme(SettingInfos.getResource(sharedPrefs.getString("pref_lang", "18")));
        setTheme(SettingInfos.getResourceColor(sharedPrefs.getString("pref_color", "black")));
        setContentView(R.layout.activity_show);
        exerciseDataSource = new ExerciseDataSource(this);


        exID = getIntent().getLongExtra("exID", -1);

        if (exID >= 0) {
            setTitelAndDescription();
        }

        plans = new PlanDataSource(this).getPlanFromUserID(UserInfos.getUserID());
    }

    /**
     * Change the titel and description base on the Exercise
     */
    private void setTitelAndDescription() {
        ExerciseDataSource exerciseDataSource = new ExerciseDataSource(this);
        Exercise ex = exerciseDataSource.getExerciseById(exID);

        TextView titel = (TextView) findViewById(R.id.titelExercise);
        titel.setText(ex.getExerciseName());
        TextView description = (TextView) findViewById(R.id.descriptionExercise);
        description.setText(ex.getExerciseDescription());
    }

    /**
     * show all categories from all exercises
     */
    public void showExercisesCat(View v) {
        CallMainActivitys.showExersiseCatagory(v, this);
    }


    /**
     * show all categroies from my plan
     */
    public void showMyPlan(View v) {
        CallMainActivitys.showExersisePlans(v, this, UserInfos.getUserID(), true);
    }


    /**
     * top right the menu button
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
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_addNormal) {
            showPlanListDialog(false);
        }
        if (id == R.id.menu_deleteNormal) {
            showPlanListDialog(true);
        }
        if (id == R.id.menu_change) {
            update = true;
            Intent intent = new Intent(this, CreatExcersisActivity.class);
            intent.putExtra("exersiseID", exID);
            intent.putExtra("update", update);
            intent.putExtra("idBodyPart", new ExerciseDataSource(this).getExerciseById(exID).getBodyPart());
            startActivity(intent);
            ShowActivity.this.finish();
            return true;
        }
        if (id == R.id.menu_delete) {
            //delete
            exerciseDataSource.deleteExercise(exID);
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.dialog_t_exit))
                    .setMessage(getResources().getString(R.string.dialog_deletedEx))
                    .setPositiveButton(getResources().getString(R.string.dialog_ok), new DialogInterface.OnClickListener() { //Hardcoded
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    });
        }

        return true;
    }

    /**
     * Change the menu buttons
     */
    private void updateMenuItem() {
        if (UserInfos.isIsAdmin()) {
            menu.setGroupVisible(R.id.menuAdminGroup, true);
        } else {
            menu.setGroupVisible(R.id.menuAdminGroup, false);
        }
    }

    /**
     * Show a list of Plans
     */

    private void showPlanListDialog(final boolean isDelete) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        if (isDelete) {
            builderSingle.setTitle(getResources().getString(R.string.dialog_plantodelete));
        } else {
            builderSingle.setTitle(getResources().getString(R.string.dialog_plantoadd));
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        final List<Plan> planListNotIn = new ArrayList<Plan>();
        final List<Plan> planListIn = new ArrayList<Plan>();
        Exercise ex = new ExerciseDataSource(this).getExerciseById(exID);
        Boolean isIn;
        for (Plan plan : plans) {
            List<PlanExercise> planExerciseList = new PlanExerciseDataSource(this).getAllPlanExerciseByPlanID(plan.getPlanID());
            isIn = false;
            for (PlanExercise planExercise : planExerciseList) {
                if (planExercise.getExerciseID() == exID)
                    isIn = true;
            }

            if (isIn) {
                planListIn.add(plan);
            } else {
                planListNotIn.add(plan);
            }
        }

        List<Plan> planList;
        if (isDelete) {
            planList =planListIn ;
        } else {
            planList = planListNotIn;
        }

        for (Plan p : planList)
        {
            arrayAdapter.add(p.getPlanName());
        }

        builderSingle.setNegativeButton(getResources().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isDelete){
                    delete(planListIn.get(which).getPlanID());
                }else
                {
                    add(planListNotIn.get(which).getPlanID());
                }
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(ShowActivity.this);
                builderInner.setMessage(strName);
                builderInner.setTitle(getResources().getString(R.string.dialog_selectedPlan)); //Hardcoded
                builderInner.setPositiveButton(getResources().getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int index) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

    /**
     * Add a Plan
     */

    private void add(long planID)
    {
        PlanExercise planExercise = new PlanExercise();
        planExercise.setPlanID(planID);
        planExercise.setExerciseID(exID);
        PlanExerciseDataSource planExerciseDataSource =  new PlanExerciseDataSource(this);
        planExerciseDataSource.createPlanExercise(planExercise);
    }

    /**
     * Delete a Plan
     */


    private void delete(long planID)
    {
        PlanExerciseDataSource planExerciseDataSource = new PlanExerciseDataSource(this);
        List<PlanExercise> planExerciseList = planExerciseDataSource.getAllPlanExerciseByPlanID(planID);
        long id = -1;
        for (PlanExercise planExercise:planExerciseList)
        {
            if (planExercise.getExerciseID() == exID);
                id=planExercise.getPlanExerciseID();
        }
        if (id != -1)
        new PlanExerciseDataSource(this).deletePlanExercise(id);
    }
}
