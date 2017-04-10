package com.hevs.gym.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hevs.gym.fitnessapp.db.adabter.UserDataSource;
import com.hevs.gym.fitnessapp.db.objects.User;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    //register
    public void register(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);

        User user = new User();
        user.setNamelogin("test");
        user.setPassword("test");
        user.setFirstname("Matthias");
        user.setLastname("Zuber");
        user.setMale(true);
        user.setAdministrator(true);
        UserDataSource ud = new UserDataSource(this);
        ud.createPerson(user);


        startActivity(intent);
    }
}
