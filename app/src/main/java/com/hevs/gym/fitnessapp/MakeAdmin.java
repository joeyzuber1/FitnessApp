package com.hevs.gym.fitnessapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import com.hevs.gym.fitnessapp.db.objects.User;
import com.hevs.gym.fitnessapp.db.adabter.UserDataSource;


import java.util.List;

public class MakeAdmin extends AppCompatActivity {

    private  UserDataSource userDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeadmin);
    }

    public void TrasportAdmin(View v){

        String firstname = (((EditText) findViewById(R.id.in_fname)).getText().toString());
        String lastname = (((EditText) findViewById(R.id.in_lname)).getText().toString());
        String username = (((EditText) findViewById(R.id.in_username)).getText().toString());

        List<User> users = userDataSource.getAllUsers();

        for (int i = 0; i<users.size(); i++)
        {
            if (firstname.equals(users.get(i).getFirstname()) && lastname.equals(users.get(i).getLastname()) && username.equals(users.get(i).getNamelogin())){
                users.get(i).setAdministrator(true);
                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.dialog_t_exit))
                        .setMessage(firstname +" "+ lastname + "has been transportet to an Admin")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() { //Hardcoded
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                System.exit(0);
                            }
                        }); //hardcoded
                Intent intent = new Intent(this, MainMenuActivitiy.class);
                startActivity(intent);
                 }
                 else{
                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                        .setMessage("You didn't fill in all Fields. Do you wanna continue with Registration?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() { //Hardcoded
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                System.exit(0);
                            }
                        }).setNegativeButton("Yes", null).show(); //hardcoded
            }
        }

    }


    public void DeleteUser(View w){

        String username = ((EditText) findViewById(R.id.in_username)).getText().toString();
        String firstname = ((EditText) findViewById(R.id.in_fname)).getText().toString();
        String lastname = ((EditText) findViewById(R.id.in_lname)).getText().toString();

        List<User> users = userDataSource.getAllUsers();

        for (int i = 0; i<users.size(); i++)
        {
            if (firstname.equals(users.get(i).getFirstname()) && lastname.equals(users.get(i).getLastname()) && username.equals(users.get(i).getNamelogin())){
                users.remove(i);
                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.dialog_t_exit))
                        .setMessage(firstname +" "+ lastname + "has been deleted")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() { //Hardcoded
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                System.exit(0);
                            }
                        }); //hardcoded
                Intent intent = new Intent(this, MainMenuActivitiy.class);
                startActivity(intent);
            }

            else{
                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.dialog_t_exit))
                        .setMessage("This Person doesn't exist in our Database. Do you wanna return in this Task")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() { //Hardcoded
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                System.exit(0);
                            }
                        }).setNegativeButton("Yes", null).show(); //hardcoded
            }


        }

    }

    public void UpdateUser(View w){

        String username = ((EditText) findViewById(R.id.in_username)).getText().toString();
        String firstname = ((EditText) findViewById(R.id.in_fname)).getText().toString();
        String lastname = ((EditText) findViewById(R.id.in_lname)).getText().toString();

        List<User> users = userDataSource.getAllUsers();

        for (int i = 0; i<users.size(); i++)
        {
            if (firstname.equals(users.get(i).getFirstname()) && lastname.equals(users.get(i).getLastname()) && username.equals(users.get(i).getNamelogin())){
                users.remove(i);
                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.dialog_t_exit))
                        .setMessage(firstname +" "+ lastname + "has been deleted")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() { //Hardcoded
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                System.exit(0);
                            }
                        }); //hardcoded
                Intent intent = new Intent(this, MainMenuActivitiy.class);
                startActivity(intent);
            }

            else{
                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.dialog_t_exit))
                        .setMessage("This Person doesn't exist in our Database. Do you wanna return in this Task")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() { //Hardcoded
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                System.exit(0);
                            }
                        }).setNegativeButton("Yes", null).show(); //hardcoded
            }


        }

    }
}
