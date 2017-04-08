package com.hevs.gym.fitnessapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyGroupsActivity extends AppCompatActivity {

    private int idUser;
    private ArrayList<Button> buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_groups);

        buttonList = new ArrayList<Button>();

        idUser = UserInfos.getUserID();

        if (idUser >= 0) {
            generateButtons();
        }

    }

    //Generate the Main Button GridLayout
    private void generateButtons() {
        String fromDB[] = getResources().getStringArray(R.array.group_array); //Später DB

        String buttons[] = new String[fromDB.length + 2];
        buttons[0] = getString(R.string.mygroups_all);

        for (int i = 1; i < buttons.length - 1; i++) {
            buttons[i] = fromDB[i - 1];
        }
        buttons[buttons.length - 1] =  getString(R.string.mygroups_join);

        LinearLayout ll = (LinearLayout) findViewById(R.id.mainGroups);
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
                    MyGroupsActivity.this.onClick(v, index);
                }
            });
            ll.addView(b);
            buttonList.add(b);
        }


    }

    //on click listener
    public void onClick(View v, int index) {
        if (index != buttonList.size() - 1) {
            Intent intent = new Intent(this, GroupUserActivity.class);
            intent.putExtra("idUser", 1); //SPäter DB
            intent.putExtra("idGroup", 1); //später DB
            startActivity(intent);
        } else {
            //Join a new Group
            inputString = "";
            inputAlertJoin();
        }
    }

    //show all
    public void showExercisesCat(View v) {
        CallMainActivitys.showExersiseCatagory(v, this);
    }

    //show my plan
    public void showMyPlan(View v) {
        CallMainActivitys.showExersisePlan(v, this);
    }

    private String inputString = "";

    //allert for join group
    private void inputAlertJoin() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.dialog_q_join));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton(getResources().getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inputString = input.getText().toString();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}
