package com.hevs.gym.fitnessapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.hevs.gym.fitnessapp.db.adabter.PlanDataSource;
import com.hevs.gym.fitnessapp.db.adabter.PlanExerciseDataSource;
import com.hevs.gym.fitnessapp.db.adabter.UserDataSource;
import com.hevs.gym.fitnessapp.db.objects.User;
import java.lang.reflect.Field;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private  UserDataSource userDataSource;
    Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDataSource = new UserDataSource(this);
        userDataSource.setupAdmin();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Preferences
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        setTheme(SettingInfos.getResource(sharedPrefs.getString("pref_lang", "18")));
        setTheme(SettingInfos.getResourceColor(sharedPrefs.getString("pref_color", "black")));
        setContentView(R.layout.activity_main);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.menu_settings, menu);
        this.menu = menu;
        return true;
    }

    /**
     * On click listener for the top right menu
     *
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings)
        {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        return true;
    }
    /**
     * Checked if user login is ok and logged in
     *
     */
    public void  logIn(View v){
        String username = ((EditText) findViewById(R.id.login_username)).getText().toString();
        String pw = ((EditText) findViewById(R.id.login_passwd)).getText().toString();
        List<User> users = userDataSource.getAllUsers();
        boolean isTrue = false;
        for (int i = 0; i<users.size(); i++)
        {
            if (username.equals(users.get(i).getNamelogin()) && pw.equals(users.get(i).getPassword()))
            {
                isTrue =true;
                UserInfos.setUserID(users.get(i).getUserID());
                UserInfos.setIsAdmin(users.get(i).isAdministrator());

                PlanDataSource planDataSource = new PlanDataSource(this);
                UserInfos.setPlanID(planDataSource.getPlanFromUserID(UserInfos.getUserID()).get(0).getPlanID());

                break;
            }
        }
        if (isTrue == true) {
            Intent intent = new Intent(this, MainMenuActivitiy.class);
            startActivity(intent);
        }else
        {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.dialog_warning))
                    .setMessage(getResources().getString(R.string.dialog_checknameandpw))
                    .setNegativeButton(getResources().getString(R.string.dialog_ok), null).show(); //hardcoded
            ((EditText) findViewById(R.id.login_username)).getText().clear();
            ((EditText) findViewById(R.id.login_passwd)).getText().clear();
        }
    }

    /**
     * open the register activity
     *
     */
    public void register(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * leave the app
     *
     */
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.dialog_t_exit))
                .setMessage(getResources().getString(R.string.dialog_q_exit))
                .setPositiveButton(getResources().getString(R.string.dialog_yes), new DialogInterface.OnClickListener() { //Hardcoded
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                }).setNegativeButton(getResources().getString(R.string.dialog_no), null).show(); //hardcoded
    }
}
