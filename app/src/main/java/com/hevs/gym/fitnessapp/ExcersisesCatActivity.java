package com.hevs.gym.fitnessapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hevs.gym.fitnessapp.db.adabter.BodyPartDataSource;
import com.hevs.gym.fitnessapp.db.adabter.ExerciseDataSource;
import com.hevs.gym.fitnessapp.db.adabter.GroupDataSource;
import com.hevs.gym.fitnessapp.db.adabter.GroupUsersDataSource;
import com.hevs.gym.fitnessapp.db.adabter.PlanDataSource;
import com.hevs.gym.fitnessapp.db.adabter.PlanExerciseDataSource;
import com.hevs.gym.fitnessapp.db.objects.BodyPart;
import com.hevs.gym.fitnessapp.db.objects.Exercise;
import com.hevs.gym.fitnessapp.db.objects.Group;
import com.hevs.gym.fitnessapp.db.objects.GroupUser;
import com.hevs.gym.fitnessapp.db.objects.Plan;
import com.hevs.gym.fitnessapp.db.objects.PlanExercise;

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
        //Preferences
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        setTheme(SettingInfos.getResource(sharedPrefs.getString("pref_lang", "18")));
        setTheme(SettingInfos.getResourceColor(sharedPrefs.getString("pref_color", "black")));
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
            titel = titel+"All Exercises"; //Hardcoded
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
        if (UserInfos.isIsAdmin() && idUser == -1) {
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
            inputAlertAdd();
        }

        if (id == R.id.menu_delcat) {
           showCatListDialog();
        }

        return true;
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

    private void showCatListDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);

            builderSingle.setTitle("Select a catagorie which you want to delete");//Hardcoded


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        final List<BodyPart> bodyPartList = new BodyPartDataSource(ExcersisesCatActivity.this).getAllBodyParts();
        for (BodyPart bodyPart : bodyPartList)
        {
            arrayAdapter.add(bodyPart.getBodySection());
        }

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               delBodyPart(bodyPartList.get(which));
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(ExcersisesCatActivity.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Bodypart is"); //Hardcoded
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int index) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

    private void delBodyPart(BodyPart bodyPart)
    {
        if ((new ExerciseDataSource(this).getAllExercisesFromBodyPartID(bodyPart.getPartOfBodyID()).size())==0) //Nur wenn keine Ex drin sind
        {
            new BodyPartDataSource(this).deleteBodyPart(bodyPart.getPartOfBodyID());
            if(((LinearLayout) findViewById(R.id.mainCatEcersis)).getChildCount() > 0)
                ((LinearLayout) findViewById(R.id.mainCatEcersis)).removeAllViews();
            generateButtons();
        }else
        {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                    .setMessage("There are Excersises from this bodypart pls delete them first!")//Hardcoded
                    .setNegativeButton("OK", null).show(); //hardcoded
        }
    }

    private String inputString = "";
    //allert for add a categorie
    private void inputAlertAdd() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert the Bodypart you want to add");//Hardcoded

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(getResources().getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inputString = input.getText().toString();
                if (!inputString.equals(""))
                    addBodyPart();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void addBodyPart()
    {
       List<BodyPart> bodyParts = new BodyPartDataSource(this).getAllBodyParts();
        boolean isAlready = false;
        for (BodyPart bp : bodyParts)
        {
            if (bp.getBodySection().equals(inputString)) {
                isAlready = true;
                break;
            }
        }
        if (isAlready)
        {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Warning")
                    .setMessage("This Bodypart exists already!")//Hardcoded
                    .setNegativeButton("OK", null).show(); //hardcoded
        }
        else
        {
            BodyPart bodyPart = new BodyPart();
            bodyPart.setBodySection(inputString);
            new BodyPartDataSource(this).createBodyPart(bodyPart);

            if(((LinearLayout) findViewById(R.id.mainCatEcersis)).getChildCount() > 0)
                ((LinearLayout) findViewById(R.id.mainCatEcersis)).removeAllViews();
            generateButtons();
        }
    }
}
