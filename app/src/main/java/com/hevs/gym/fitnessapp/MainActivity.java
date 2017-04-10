package com.hevs.gym.fitnessapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hevs.gym.fitnessapp.db.adabter.UserDataSource;
import com.hevs.gym.fitnessapp.db.objects.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //login
    public void  logIn(View v){
        Intent intent = new Intent(this, MainMenuActivitiy.class);

        String username = ((EditText) findViewById(R.id.login_username)).getText().toString();
        String pw = ((EditText) findViewById(R.id.login_passwd)).getText().toString();

        UserDataSource ud = new UserDataSource(this);
        List<User> users = ud.getAllUsers();

        boolean isTrue = false;

        for (int i = 0; i<users.size(); i++)
        {
            if (username.equals(users.get(i).getNamelogin()) && pw.equals(users.get(i).getPassword()))
            {
                isTrue =true;
                break;
            }
        }

        UserInfos.setUserID(1);
        if (isTrue) {
            startActivity(intent);
        }else
        {

        }
    }

    //register
    public void register(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    //go back
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
