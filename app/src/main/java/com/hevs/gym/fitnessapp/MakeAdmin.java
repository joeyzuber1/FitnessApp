package com.hevs.gym.fitnessapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;

import com.example.matthias.myapplication.backend.userApi.model.User;
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
        //Preferences
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        setTheme(SettingInfos.getResource(sharedPrefs.getString("pref_lang", "18")));
        setTheme(SettingInfos.getResourceColor(sharedPrefs.getString("pref_color", "black")));
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
        showUserListDialog(getResources().getString(R.string.dialog_userdelete), userListDelete, 3);//Hardcoded
    }

    /**
     * Admin can update a user
     *
     */

    public void UpdateUser(View w){
        List<User> userList = userDataSource.getAllUsers();
        showUserListDialog(getResources().getString(R.string.dialog_userupdate), userList, 2);//Hardcoded
    }

    /**
     * Dialogbox with the List of Admins
     *
     */

    private void showUserAdminsdialog()
    {
        List<User> userList = userDataSource.getAllUsers();
        List<User> userListAdmins = new ArrayList<User>();
        for (User u : userList)
        {
            if (!u.getAdministrator())
            {
                userListAdmins.add(u);
            }
        }
        showUserListDialog(getResources().getString(R.string.dialog_useryesadmin), userListAdmins, 0);//Hardcoded
    }
    /**
     * Dialogbox with the List of Users
     *
     */
    private void showUserNotAdminDialog()
    {
        List<User> userList = userDataSource.getAllUsers();
        List<User> userListNotLongerAdmins = new ArrayList<User>();
        for (User u : userList)
        {
            if (u.getAdministrator() && !u.getNamelogin().equals("admin"))
            {
                userListNotLongerAdmins.add(u);
            }
        }
        showUserListDialog(getResources().getString(R.string.dialog_usernoadmin), userListNotLongerAdmins, 1);//Hardcoded
    }

    /**
     * Dialogbox with the List of Users
     *
     */
    private void showUserListDialog(String titel, final List<User> usersList, final int action) { //action 0 addAdmin 1 removeAdmin 2 update User 3 removeUser
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle(titel);//Hardcoded

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);

        for (User u : usersList)
        {
            arrayAdapter.add(u.getFirstname()+" "+u.getLastname());
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
                    builderInner.setTitle(getResources().getString(R.string.dialog_selecteduser)); //Hardcoded
                    builderInner.setPositiveButton(getResources().getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
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
