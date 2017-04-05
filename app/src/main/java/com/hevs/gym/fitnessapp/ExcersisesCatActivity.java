package com.hevs.gym.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ExcersisesCatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excersises_cat);
        ((TextView) findViewById(R.id.titelCatExcersis)).setText(getIntent().getStringExtra("Titel"));
        Bundle extras = getIntent().getExtras();
        String[] buttons = extras.getStringArray("buttonArray");

        LinearLayout ll = (LinearLayout) findViewById(R.id.mainCatEcersis);
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        for (int i = 0; i<buttons.length; i++)
        {
            Button b = new Button(this);
            b.setText(buttons[i]);
            b.setLayoutParams(lp);
            ll.addView(b);
        }
    }
}
