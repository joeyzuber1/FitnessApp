package com.hevs.gym.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hevs.gym.fitnessapp.db.adabter.BodyPartDataSource;
import com.hevs.gym.fitnessapp.db.adabter.PlanDataSource;
import com.hevs.gym.fitnessapp.db.objects.BodyPart;

import java.util.ArrayList;
import java.util.List;


public class ExcersisesCatActivity extends AppCompatActivity {

    private ArrayList<Button> buttonList;
    private long idUser;
    private boolean isMyPlan;
    private List<BodyPart> bodyParts;
    private List<Long> idParts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excersises_cat);

        generateButtons();
    }

    //Generate the Main Button GridLayout
    private void generateButtons() {
        buttonList = new ArrayList<Button>();
        idParts = new ArrayList<Long>();
        idParts.add(-1l);

        ((TextView) findViewById(R.id.titelCatExcersis)).setText(getIntent().getStringExtra("Titel"));
        Bundle extras = getIntent().getExtras();

        isMyPlan = extras.getBoolean("isMyPlan");
        idUser = extras.getLong("idUser");

        String[] buttonsReceived;

        BodyPartDataSource bodyPartDataSource = new BodyPartDataSource(this);
        if (isMyPlan) {
            PlanDataSource planDataSource = new PlanDataSource(this);
            long planID = planDataSource.getPlanFromUserID(idUser).get(0).getPlanID();
            bodyParts = bodyPartDataSource.getAllBodyPartsByPlanID(planID);
        } else
        {
            bodyParts = bodyPartDataSource.getAllBodyParts();
        }

        buttonsReceived = new String[bodyParts.size()];
        for (int i = 0; i< buttonsReceived.length; i++)
        {
            buttonsReceived[i] = bodyParts.get(i).getBodySection();
            idParts.add(bodyParts.get(i).getPartOfBodyID());
        }

        String[] buttons = new String[buttonsReceived.length+1];
        buttons[0] = "All Exercises"; //Hardcoded
        for (int i = 1; i<buttons.length; i++)
        {
            buttons[i] = buttonsReceived[i-1];
        }

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
            final int index = i;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExcersisesCatActivity.this.onClick(index);
                }
            });
            ll.addView(b);
            buttonList.add(b);
        }
    }

    //OnClickListener
    public void onClick(int index){
        String[] stringArray = new String[0];
        String titel = getIntent().getStringExtra("Titel");
        if (index == 0)
        {
            //take all
            titel = titel + " -> "+"All";
        }
        for (int i = 1; i < buttonList.size(); i++){
           if (i == index)
           {
               //Take the right things
               titel = titel + " -> "+buttonList.get(i).getText();
           }
        }

        if (index == 0) {
            showExercises(titel, -1);
        }else
        {
            showExercises(titel, idParts.get(index));
        }
    }

    //show one Exercis
    public void showExercises(String titel, long idEx){
        Intent inten = new Intent(this, ExersisesActivity.class);
        inten.putExtra("Titel", titel); //better need
        inten.putExtra("isMyPlan", isMyPlan);
        inten.putExtra("idBodyPart", idEx);
        inten.putExtra("idUser", idUser);
        startActivity(inten);
    }

    //show all
    public void showExercisesCat(View v){
        CallMainActivitys.showExersiseCatagory(v, this);
    }

    //show personal plan
    public void showMyPlan(View v){
        CallMainActivitys.showExersisePlan(v, this);
    }

    //go back
    @Override
    public void onBackPressed() {
        if (UserInfos.getUserID() == idUser) {
            Intent intent = new Intent(this, MainMenuActivitiy.class);
            startActivity(intent);
        }else
        {
            super.onBackPressed();
        }
    }
}
