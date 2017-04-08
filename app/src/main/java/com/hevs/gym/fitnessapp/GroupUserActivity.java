package com.hevs.gym.fitnessapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class GroupUserActivity extends AppCompatActivity {

    private int idGroup;
    private int idUser;
    private ArrayList<Button> buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_user);
        buttonList = new ArrayList<Button>();

        idGroup = getIntent().getIntExtra("idGroup", -1);
        idUser = getIntent().getIntExtra("idGroup", -1);

        if (idGroup >= 0) {
            generateButtons();
        }

        TextView titelView = (TextView) findViewById(R.id.titelGroupUsers);
        titelView.setText("Group" + idGroup);
    }

    //Generate the Main Button GridLayout
    private void generateButtons() {
        String fromDB[] = getResources().getStringArray(R.array.user_array);

        String buttons[] = new String[fromDB.length+1]; //später -1

        for (int i =0; i < fromDB.length; i++) {
            //Prüfen ob eigener in DB !!!!!
            buttons[i] = fromDB[i];
        }

        buttons[buttons.length-1] = getResources().getString(R.string.dialog_t_leaveGrp);

        LinearLayout ll = (LinearLayout) findViewById(R.id.mainGroupUsers);
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        for (int i = 0; i < buttons.length; i++) {
            Button b = new Button(this);
            b.setText(buttons[i]);
            b.setLayoutParams(lp);
            final int index = i;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GroupUserActivity.this.onClick(v, index);
                }
            });
            ll.addView(b);
            buttonList.add(b);
        }


    }

    //onclicklistneer
    public void onClick(View v, int index) {
        if (index != buttonList.size()-1) {
            CallMainActivitys.showExersisePlanFromUser(v, this, index); //später userID
        }
        else
        {
            //leave the group
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.dialog_t_leaveGrp))
                    .setMessage(getResources().getString(R.string.dialog_q_leaveGrp))
                    .setPositiveButton(getResources().getString(R.string.dialog_yes), new DialogInterface.OnClickListener() { //Hardcoded
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    }).setNegativeButton(getResources().getString(R.string.dialog_no), null).show();
        }
    }

    //show all
    public void showExercisesCat(View v){
        CallMainActivitys.showExersiseCatagory(v, this);
    }

    //show personal plan
    public void showMyPlan(View v){
        CallMainActivitys.showExersisePlan(v, this);
    }
}
