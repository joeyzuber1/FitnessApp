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

public class RegisterActivity extends AppCompatActivity {

    private UserDataSource userDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userDataSource = new UserDataSource(this);
        List<User> users = userDataSource.getAllUsers();
        long iduser = getIntent().getLongExtra("userID", -1);
        for (int i = 0; i<users.size(); i++)
        {
            if (users.get(i).getUserID() == iduser )
            {
                ((EditText) findViewById(R.id.in_username)).setText(users.get(i).getNamelogin());
               ((EditText) findViewById(R.id.in_fname)).setText(users.get(i).getFirstname());
                ((EditText) findViewById(R.id.in_lname)).setText(users.get(i).getLastname());
               ((EditText) findViewById(R.id.in_pw1)).setText(users.get(i).getPassword());
                ((EditText) findViewById(R.id.in_pw2)).setText(users.get(i).getPassword());

            }

        }
    }

    /**
     * this will happen when the user click on the registery button
     *
     */
    public void register(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        //User erstellen Muss noch geprüft werden ob alles eingegebene wird
        User user = new User();
        user.setNamelogin(((EditText) findViewById(R.id.in_username)).getText().toString());
        user.setFirstname(((EditText) findViewById(R.id.in_fname)).getText().toString());
        user.setLastname(((EditText) findViewById(R.id.in_lname)).getText().toString());
        user.setPassword(((EditText) findViewById(R.id.in_pw1)).getText().toString());
        user.setAdministrator(false);

        if (((EditText) findViewById(R.id.in_username)).getText().toString().length()>0 && ((EditText) findViewById(R.id.in_fname)).getText().toString().length()>0
                && ((EditText) findViewById(R.id.in_lname)).getText().toString().length() > 0 && ((EditText) findViewById(R.id.in_pw1)).getText().toString().length()>0) //alles gut
        {
            if(((EditText) findViewById(R.id.in_pw1)).getText().toString().equals(((EditText) findViewById(R.id.in_pw2)).getText().toString())) {
                UserDataSource uds = new UserDataSource(this);
                uds.createUser(user);
                startActivity(intent);
            }
            else{
                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                        .setMessage("Your Password didn't match. Try again?")
                        .setNegativeButton("OK", null).show(); //hardcoded
                ((EditText) findViewById(R.id.in_pw1)).getText().clear();
                ((EditText) findViewById(R.id.in_pw2)).getText().clear();

            }
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
