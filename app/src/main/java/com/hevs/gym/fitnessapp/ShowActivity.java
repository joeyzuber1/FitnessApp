package com.hevs.gym.fitnessapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    private int exID;
    private boolean isInMyPlan;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        exID = getIntent().getIntExtra("exID", -1);

        if (exID >= 0)
        {
            setTitelAndDescription();
        }
        isInMyPlan = true; //später DB
    }

    //set Titel and description
    private void setTitelAndDescription(){
        TextView titel = (TextView) findViewById(R.id.titelExercise);
        titel.setText("Exercise"+exID); //DB
        TextView description = (TextView) findViewById(R.id.descriptionExercise);
        description.setText(R.string.example_description);
    }

    //show all
    public void showExercisesCat(View v){
        CallMainActivitys.showExersiseCatagory(v, this);
    }


    //show personal plan
    public void showMyPlan(View v){
        CallMainActivitys.showExersisePlan(v, this);
    }


    //create option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.menu_exercise, menu);
        this.menu = menu;
        updateMenuItem();
        return true;
    }

    //on click listener option menu
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_change) {
            //change


            return true;
        }
        if (id == R.id.menu_delete) {
            //delete
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.dialog_t_delete))
                    .setMessage(getResources().getString(R.string.dialog_q_delete))
                    .setPositiveButton(getResources().getString(R.string.dialog_yes), new DialogInterface.OnClickListener() { //Hardcoded
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    }).setNegativeButton(getResources().getString(R.string.dialog_no), null).show();
            return true;
        }
        if (id == R.id.menu_addDelete) {
            //add
            //Do something in DB
            isInMyPlan = ! isInMyPlan;
            updateMenuItem();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //update menu items
    private void updateMenuItem(){
        MenuItem addDel = menu.findItem(R.id.menu_addDelete);
        if (isInMyPlan){
            addDel.setTitle(getResources().getString(R.string.menu_deletP));
        }else
        {
            addDel.setTitle(getResources().getString(R.string.menu_add));
        }

        if (!UserInfos.isIsAdmin())
        {
           menu.setGroupVisible(R.id.menuAdminGroup, false);
        }
    }
}
