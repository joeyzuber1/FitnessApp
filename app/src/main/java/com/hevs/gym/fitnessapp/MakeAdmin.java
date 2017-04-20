package com.hevs.gym.fitnessapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.hevs.gym.fitnessapp.db.adabter.ExerciseDataSource;
import com.hevs.gym.fitnessapp.db.adabter.PlanExerciseDataSource;
import com.hevs.gym.fitnessapp.db.objects.Exercise;
import com.hevs.gym.fitnessapp.db.objects.Plan;
import com.hevs.gym.fitnessapp.db.objects.PlanExercise;
import com.hevs.gym.fitnessapp.db.objects.User;
import com.hevs.gym.fitnessapp.db.adabter.UserDataSource;


import java.util.ArrayList;
import java.util.List;

public class MakeAdmin extends AppCompatActivity {

    private long iduser;
    private boolean update = false;
    private UserDataSource userDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeadmin);
        userDataSource = new UserDataSource(this);
    }


    /**
     * A Admin can make a User to an Admin
     *
     */
    public void TrasportAdmin(View v){

        showUserAdminsdialog();
    }

    /**
     * A Admin can remove a Admin
     *
     */
    public void removeAdmin(View v){

        showUserNotAdminDialog();
    }
    /**
     * Admin can delete a User
     *
     */

    public void DeleteUser(View w) {
        List<User> userList = userDataSource.getAllUsers();
        List<User> userListDelete = new ArrayList<User>();
        for (User u : userList)
        {
            if (!u.getNamelogin().equals("admin")) //alle ausser hauptadmin
            {
                userListDelete.add(u);
            }
        }
        showUserListDialog("Which user should be delete?", userListDelete, 3);//Hardcoded
    }

    /**
     * Admin can update a user
     *
     */

    public void UpdateUser(View w){
        List<User> userList = userDataSource.getAllUsers();
        showUserListDialog("Which user should be updated?", userList, 2);//Hardcoded
    }

    private void showUserAdminsdialog()
    {
        List<User> userList = userDataSource.getAllUsers();
        List<User> userListAdmins = new ArrayList<User>();
        for (User u : userList)
        {
            if (!u.isAdministrator())
            {
                userListAdmins.add(u);
            }
        }
        showUserListDialog("Which user should be admin?", userListAdmins, 0);//Hardcoded
    }

    private void showUserNotAdminDialog()
    {
        List<User> userList = userDataSource.getAllUsers();
        List<User> userListNotLongerAdmins = new ArrayList<User>();
        for (User u : userList)
        {
            if (u.isAdministrator() && !u.getNamelogin().equals("admin")) //alle ausser hauptadmin
            {
                userListNotLongerAdmins.add(u);
            }
        }
        showUserListDialog("Which user should not be admin?", userListNotLongerAdmins, 1);//Hardcoded
    }

    private void showUserListDialog(String titel, final List<User> usersList, final int action) { //action 0 addAdmin 1 removeAdmin 2 update User 3 removeUser
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle(titel);//Hardcoded

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);

        for (User u : usersList)
        {
            arrayAdapter.add(u.getFirstname()+" "+u.getLastname());
        }

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                User selectedUser = usersList.get(which);
                switch (action)
                {
                    case 0:
                        selectedUser.setAdministrator(true);
                        userDataSource.updateUser(selectedUser);
                        break;
                    case 1:
                        selectedUser.setAdministrator(false);
                        userDataSource.updateUser(selectedUser);
                        break;
                    case 2:
                        iduser = selectedUser.getUserID();
                        Intent intent = new Intent(MakeAdmin.this, RegisterActivity.class);
                        intent.putExtra("userID", iduser);
                        startActivity(intent);
                        break;
                    case 3:
                        userDataSource.deleteUser(selectedUser.getUserID());
                        break;
                    default:
                        break;
                }

                if (action != 2) {
                    String strName = arrayAdapter.getItem(which);
                    AlertDialog.Builder builderInner = new AlertDialog.Builder(MakeAdmin.this);
                    builderInner.setMessage(strName);
                    builderInner.setTitle("Your Selected User is"); //Hardcoded
                    builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int index) {
                            dialog.dismiss();
                        }
                    });
                    builderInner.show();
                }
            }
        });
        builderSingle.show();
    }
}
