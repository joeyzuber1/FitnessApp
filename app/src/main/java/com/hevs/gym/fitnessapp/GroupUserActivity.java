package com.hevs.gym.fitnessapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hevs.gym.fitnessapp.db.adabter.GroupUsersDataSource;
import com.hevs.gym.fitnessapp.db.adabter.UserDataSource;
import com.hevs.gym.fitnessapp.db.objects.GroupUser;
import com.hevs.gym.fitnessapp.db.objects.User;

import java.util.ArrayList;
import java.util.List;

public class GroupUserActivity extends AppCompatActivity {

    private long idGroup;
    private ArrayList<Button> buttonList;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_user);
        buttonList = new ArrayList<Button>();

        idGroup = getIntent().getLongExtra("idGroup", -1);

        if (idGroup >= 0) {
            generateButtons();
        }

        TextView titelView = (TextView) findViewById(R.id.titelGroupUsers);
        titelView.setText("Group" + idGroup);
    }

    /**
     * generate the buttons based on the group id but without the logged user
     *
     */
    private void generateButtons() {
        UserDataSource userDataSource = new UserDataSource(this);
        users = userDataSource.getAllUsersFromGroupID(idGroup);
        String fromDB[] = new String[users.size()];
         for (int i = 0; i <fromDB.length; i++)
         {
             fromDB[i] = users.get(i).getFirstname()+" "+users.get(i).getLastname();
         }

        String buttons[] = new String[fromDB.length];

        int indexB = 0;
        int indexDB = 0;
        int userIDDElete = -1;
        User user = userDataSource.getUserById(UserInfos.getUserID());
        for (User u : users){
            if (user.getUserID() != u.getUserID()) {
                buttons[indexB] = fromDB[indexDB];
                indexB++;
            }else
            {
                userIDDElete = indexDB;
            }
            indexDB++;
        }
        users.remove(userIDDElete);
        buttons[buttons.length-1] = getResources().getString(R.string.dialog_t_leaveGrp);

        LinearLayout ll = (LinearLayout) findViewById(R.id.mainGroupUsers);
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
                    GroupUserActivity.this.onClick(v, index);
                }
            });
            ll.addView(b);
            buttonList.add(b);
        }


    }

    /**
     * this will run ig the user click on a button or on leave croup
     *
     */
    public void onClick(View v, int index) {
        if (index != buttonList.size()-1) {
            UserDataSource userDataSource = new UserDataSource(this);
            CallMainActivitys.showExersisePlans(v, this, users.get(index).getUserID(), false);
        }
        else
        {
            final GroupUsersDataSource groupUsersDataSource = new GroupUsersDataSource(this);

            //leave the group
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.dialog_t_leaveGrp))
                    .setMessage(getResources().getString(R.string.dialog_q_leaveGrp))
                    .setPositiveButton(getResources().getString(R.string.dialog_yes), new DialogInterface.OnClickListener() { //Hardcoded
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            GroupUser gu = groupUsersDataSource.gettGrouUserFromUserIDGroupID(idGroup, UserInfos.getUserID());
                            groupUsersDataSource.deleteGroupUsers(gu.getGroupUserID());
                            finish();
                        }
                    }).setNegativeButton(getResources().getString(R.string.dialog_no), null).show();
        }
    }

    /**
     * Show all categories from all exercises
     *
     */
    public void showExercisesCat(View v){
        CallMainActivitys.showExersiseCatagory(v, this);
    }

    /**
     * Show all categories from my plan
     *
     */
    public void showMyPlan(View v){
        CallMainActivitys.showExersisePlans(v, this, UserInfos.getUserID(), true);
    }
}
