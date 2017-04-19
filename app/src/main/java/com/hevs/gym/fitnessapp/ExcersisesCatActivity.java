package com.hevs.gym.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private long idPlan;
    private long idUser;
    private List<BodyPart> bodyParts;
    private List<Long> idParts;
    private Menu menu;
    private String titel;
    private boolean isFromRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excersises_cat);
        idPlan = getIntent().getLongExtra("planID", -1);
        idUser = getIntent().getLongExtra("idUser", -1);
        isFromRandom = getIntent().getBooleanExtra("isFromRandom", false);
        generateButtons();
    }

    /**
     *
     *Generate the Main Button GridLayout based on the plan and the user
     */
    private void generateButtons() {
        BodyPartDataSource bodyPartDataSource = new BodyPartDataSource(this);
        PlanDataSource planDataSource = new PlanDataSource(this);
        buttonList = new ArrayList<Button>();
        idParts = new ArrayList<Long>();
        idParts.add(-1l);

        titel = getIntent().getStringExtra("Titel");
        if (idPlan != -1)
        {
            titel = titel+"->"+planDataSource.getPlanById(idPlan).getPlanName();
        }else
        {
            titel = titel+"->All Exercises"; //Hardcoded
        }
        ((TextView) findViewById(R.id.titelCatExcersis)).setText(titel);

        String[] buttonsReceived;

        if (idPlan != -1) {

            bodyParts = bodyPartDataSource.getAllBodyPartsByPlanID(idPlan);
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
            b.setTransformationMethod(null);
            b.setText(buttons[i]);
            b.setLayoutParams(lp);
            final int index = i;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExcersisesCatActivity.this.onClick(v, index);
                }
            });
            ll.addView(b);
            buttonList.add(b);
        }
    }

    /**
     * if you click on a Body part Categorie this code will run
     *
     */
    public void onClick(View v, int index){
        String[] stringArray = new String[0];

        if (index == 0) {
            showExercises(v, -1);
        }else
        {
            showExercises(v, idParts.get(index));
        }
    }

    /**
     * show the Exercises from one id body part
     *
     */
    public void showExercises(View v, long idBodyPart){

        CallMainActivitys.showExersisesFromPlan(v, this, idPlan, idBodyPart,idUser, titel);
//        Intent inten = new Intent(this, ExersisesActivity.class);
//        inten.putExtra("Titel", titel); //better need
//        inten.putExtra("isMyPlan", isMyPlan);
//        inten.putExtra("idBodyPart", idBodyPart);
//        inten.putExtra("idUser", idUser);
//        startActivity(inten);
    }

    /**
     * show all exercises categries
     *
     */
    public void showExercisesCat(View v){
        CallMainActivitys.showExersiseCatagory(v, this);
    }

    /**
     * show my plan exercises categries
     *
     */
    public void showMyPlan(View v){
        CallMainActivitys.showExersisePlans(v, this, UserInfos.getUserID(), true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (UserInfos.isIsAdmin()) {
            if (UserInfos.isIsAdmin()) {
                MenuInflater inflator = getMenuInflater();
                inflator.inflate(R.menu.menu_addcat, menu);
                this.menu = menu;
            }
        }
        return true;
    }

    /**
     * On click listener for the top right menu
     *
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_addcat) {
            //change
            Intent intent = new Intent(this, addDel_CategoryActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.menu_createEx) {
            //change
            Intent intent = new Intent(this, CreatExcersisActivity.class);
            startActivity(intent);
            return true;
        }

   /* if (id == R.id.menu_addExtoPlan) {

               return true;
           } */

        return super.onOptionsItemSelected(item);
    }

    //go back
    @Override
    public void onBackPressed() {
        if (isFromRandom)
        {
            Intent intent = new Intent(this, MainMenuActivitiy.class);
            startActivity(intent);
        }else
        {
            super.onBackPressed();
        }
    }
}
