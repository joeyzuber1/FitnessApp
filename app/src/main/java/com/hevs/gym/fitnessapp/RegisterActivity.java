package com.hevs.gym.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hevs.gym.fitnessapp.db.adabter.UserDataSource;
import com.hevs.gym.fitnessapp.db.objects.User;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

        if (true) //alles gut
        {
            UserDataSource uds = new UserDataSource(this);
            uds.createUser(user);
        }

        startActivity(intent);
    }
}
