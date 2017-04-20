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

import com.hevs.gym.fitnessapp.db.adabter.GroupDataSource;
import com.hevs.gym.fitnessapp.db.adabter.GroupUsersDataSource;
import com.hevs.gym.fitnessapp.db.objects.Group;
import com.hevs.gym.fitnessapp.db.objects.GroupUser;

import java.util.ArrayList;
import java.util.List;

public class MyGroupsActivity extends AppCompatActivity {

    private long idUser;
    private ArrayList<Button> buttonList;
    private List<Group> groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_groups);
        idUser = UserInfos.getUserID();

        if (idUser >= 0) {
            generateButtons();
        }
    }

    /**
     * generate the buttons bases on the user groups
     *
     */
    private void generateButtons() {
        buttonList = new ArrayList<Button>();
        GroupDataSource groupDataSource = new GroupDataSource(this);
        groups = groupDataSource.getAllGroupByUserID(idUser);
        String fromDB[] = new String[groups.size()];
        for (int i = 0; i < fromDB.length; i++) {
            fromDB[i] = groups.get(i).getGroupname();
        }

        String buttons[] = new String[fromDB.length + 1];

        for (int i = 0; i < buttons.length - 1; i++) {
            buttons[i] = fromDB[i];
        }
        buttons[buttons.length - 1] = getString(R.string.mygroups_join);

        LinearLayout ll = (LinearLayout) findViewById(R.id.mainGroups);
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        for (int i = 0; i < buttons.length; i++) {
            Button b = new Button(this);
            b.setTransformationMethod(null);
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

    /**
     * this will happen when the user click on a button
     *
     */
    public void onClick(View v, int index) {
        if (index != buttonList.size() - 1) {
            Intent intent = new Intent(this, GroupUserActivity.class);
            intent.putExtra("idGroup", groups.get(index).getGroupID());
            startActivity(intent);
        } else {
            //Join a new Group
            inputAlertJoin();
        }
    }

    private void joinGroup() {
        GroupDataSource groupDataSource = new GroupDataSource(this);
        long idGroup = groupDataSource.findGroupByName(inputString);
        if (idGroup < 0) {
            Group g = new Group();
            g.setGroupname(inputString);
            groupDataSource.createGroup(g);
            idGroup = groupDataSource.findGroupByName(inputString);
        }
        GroupUsersDataSource groupUsersDataSource = new GroupUsersDataSource(this);
        List<GroupUser> groupUserList = groupUsersDataSource.getAllGroupUserByGroupID(idGroup);
        boolean isIn = false;
        for (GroupUser gp : groupUserList)
        {
            if (gp.getUserID() == idUser)
            {
                isIn = true;
            }
        }
        if (isIn == false) {
            GroupUser gu = new GroupUser();
            gu.setGroupID(idGroup);
            gu.setUserID(idUser);
            groupUsersDataSource.createGroupUser(gu);
        }else
        {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                    .setMessage("You are already in Group") //Hardcoded
                    .setNegativeButton("OK", null).show(); //hardcoded
        }

        if (((LinearLayout) findViewById(R.id.mainGroups)).getChildCount() > 0)
            ((LinearLayout) findViewById(R.id.mainGroups)).removeAllViews();

        generateButtons();

    }

    //show all
    public void showExercisesCat(View v) {
        CallMainActivitys.showExersiseCatagory(v, this);
    }

    //show my plan
    public void showMyPlan(View v) {
        CallMainActivitys.showExersisePlans(v, this, UserInfos.getUserID(), true);
    }

    private String inputString = "";

    //allert for join group
    private void inputAlertJoin() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.dialog_q_join));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(getResources().getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inputString = input.getText().toString();
                joinGroup();
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

    @Override
    protected void onResume() {
        if(((LinearLayout) findViewById(R.id.mainGroups)).getChildCount() > 0)
            ((LinearLayout) findViewById(R.id.mainGroups)).removeAllViews();

        generateButtons();
        super.onResume();
    }
}
