package com.hevs.gym.fitnessapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hevs.gym.fitnessapp.db.adabter.PlanDataSource;
import com.hevs.gym.fitnessapp.db.adabter.UserDataSource;
import com.hevs.gym.fitnessapp.db.objects.User;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private UserDataSource userDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        setTheme(SettingInfos.getResource(sharedPrefs.getString("pref_lang", "18")));
        setTheme(SettingInfos.getResourceColor(sharedPrefs.getString("pref_color", "black")));
        setContentView(R.layout.activity_register);
        userDataSource = new UserDataSource(this);

        long iduser = getIntent().getLongExtra("userID", -1);
        Button register = (Button) findViewById(R.id.register);
        Button updateRegister = (Button) findViewById(R.id.updateRegister);
        register.setVisibility(View.VISIBLE);
        updateRegister.setVisibility(View.INVISIBLE);
        /**
         * 2 View for the registration
         *
         */
        if (iduser != -1) {

            User user = new UserDataSource(this).getUserById(iduser);
            register.setVisibility(View.INVISIBLE);
            updateRegister.setVisibility(View.VISIBLE);
            EditText username = (EditText) findViewById(R.id.in_username);
            username.setText(user.getNamelogin());
            username.setClickable(false);
            username.setKeyListener(null);
            ((EditText) findViewById(R.id.in_fname)).setText(user.getFirstname());
            ((EditText) findViewById(R.id.in_lname)).setText(user.getLastname());
            ((EditText) findViewById(R.id.in_pw1)).setText(user.getPassword());
            ((EditText) findViewById(R.id.in_pw2)).setText(user.getPassword());
        }
    }
    /**
     * this will happen when the user click on the registery button
     */
    public void register(View v) {
        Intent intent = new Intent(this, MainActivity.class);

        User user = new User();
        user.setNamelogin(((EditText) findViewById(R.id.in_username)).getText().toString());
        user.setFirstname(((EditText) findViewById(R.id.in_fname)).getText().toString());
        user.setLastname(((EditText) findViewById(R.id.in_lname)).getText().toString());
        user.setPassword(((EditText) findViewById(R.id.in_pw1)).getText().toString());
        user.setAdministrator(false);

        if (existsAlready()==false && allFilled())
        {
            if (passWordMatch()) {
                UserDataSource uds = new UserDataSource(this);
                uds.createUser(user);
                startActivity(intent);
            }
        }
    }

    /**
     * button who is comming from updating a User
     */
    public void update(View v) {

        long iduser = getIntent().getLongExtra("userID", -1);
        if (iduser != -1) {
            if (allFilled()) {
                if (passWordMatch()) {
                    User user = userDataSource.getUserById(iduser);
                    user.setNamelogin(((EditText) findViewById(R.id.in_username)).getText().toString());
                    user.setFirstname(((EditText) findViewById(R.id.in_fname)).getText().toString());
                    user.setLastname(((EditText) findViewById(R.id.in_lname)).getText().toString());
                    user.setPassword(((EditText) findViewById(R.id.in_pw1)).getText().toString());
                    userDataSource.updateUser(user);
                    finish();
                }
            }
        }

    }
    /**
     * Checks if a Person already exist
     */
    private boolean existsAlready() {

        String firstname = ((EditText) findViewById(R.id.in_fname)).getText().toString();
        String lastname = ((EditText) findViewById(R.id.in_lname)).getText().toString();
        String loginname = ((EditText) findViewById(R.id.in_username)).getText().toString();

        List<User> users = userDataSource.getAllUsers();
        boolean isTrue = false;
        for (int i = 0; i < users.size(); i++) {
            if (loginname.equals(users.get(i).getNamelogin()) && firstname.equals(users.get(i).getFirstname()) && lastname.equals(users.get(i).getLastname())) {
                isTrue = true;
                break;
            }
        }
        if (isTrue == false) {

        } else {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.dialog_warning))
                    .setMessage(getResources().getString(R.string.dialog_usersexist))
                    .setNegativeButton(getResources().getString(R.string.dialog_ok), null).show();
            ((EditText) findViewById(R.id.in_username)).getText().clear();
            ((EditText) findViewById(R.id.in_fname)).getText().clear();
            ((EditText) findViewById(R.id.in_lname)).getText().clear();
            ((EditText) findViewById(R.id.in_pw1)).getText().clear();
            ((EditText) findViewById(R.id.in_pw2)).getText().clear();
        }

        return isTrue;
    }
    /**
     * Checks if a Person filled in all fields
     */
    private boolean allFilled()
    {
        if (((EditText) findViewById(R.id.in_username)).getText().toString().length() > 0 && ((EditText) findViewById(R.id.in_fname)).getText().toString().length() > 0
                && ((EditText) findViewById(R.id.in_lname)).getText().toString().length() > 0 && ((EditText) findViewById(R.id.in_pw1)).getText().toString().length() > 0)
        {
            return true;
        }else
        {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.dialog_warning))
                    .setMessage(getResources().getString(R.string.dialog_continuewithregistration))
                    .setPositiveButton(getResources().getString(R.string.dialog_no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RegisterActivity.super.onBackPressed();
                        }
                    }).setNegativeButton(getResources().getString(R.string.dialog_yes), null).show();
            return false;
        }
    }

    /**
     * Checks if the two passwords matches
     */

    private boolean passWordMatch()
    {
        if (((EditText) findViewById(R.id.in_pw1)).getText().toString().equals(((EditText) findViewById(R.id.in_pw2)).getText().toString()))
        {
            return true;
        }else
        {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.dialog_warning))
                    .setMessage(getResources().getString(R.string.dialog_pwdidntmatch))//Hardcoded
                    .setNegativeButton(getResources().getString(R.string.dialog_ok), null).show(); //hardcoded
            ((EditText) findViewById(R.id.in_pw1)).getText().clear();
            ((EditText) findViewById(R.id.in_pw2)).getText().clear();
            return false;
        }
    }
}
