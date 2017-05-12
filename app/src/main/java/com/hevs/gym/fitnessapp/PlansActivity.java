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

import com.example.matthias.myapplication.backend.planApi.model.Plan;
import com.hevs.gym.fitnessapp.db.adabter.PlanDataSource;
import com.hevs.gym.fitnessapp.db.adabter.UserDataSource;

import java.util.ArrayList;
import java.util.List;

public class PlansActivity extends AppCompatActivity {

    private long idUser;
    private ArrayList<Button> buttonList;
    private List<Plan> plans;
    private boolean isFromRandom;
    String titel;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Preferences
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        setTheme(SettingInfos.getResource(sharedPrefs.getString("pref_lang", "18")));
        setTheme(SettingInfos.getResourceColor(sharedPrefs.getString("pref_color", "black")));
        setContentView(R.layout.activity_plans);
        idUser = getIntent().getLongExtra("idUser", -1);
        isFromRandom = getIntent().getBooleanExtra("isFromRandom", false);
        buttonList = new ArrayList<>();

        if (idUser != -1) {
            UserDataSource userDataSource = new UserDataSource(this);
            TextView textView = (TextView) findViewById(R.id.titel_plan);
            titel =userDataSource.getUserById(idUser).getFirstname();
            textView.setText(titel + " Plans");
            generateButtons();
        }else
        {
            titel ="";
        }
    }


    /**
     * generate the buttons based on the userID
     *
     */
    private void generateButtons() {
        buttonList = new ArrayList<Button>();
        PlanDataSource planDataSource = new PlanDataSource(this);
        plans = planDataSource.getPlanFromUserID(idUser);
        String fromDB[] = new String[plans.size()];
        for (int i = 0; i < fromDB.length; i++)
        {
            fromDB[i] = plans.get(i).getPlanName();
        }

        LinearLayout ll = (LinearLayout) findViewById(R.id.mainPlans);
        if (ll.getChildCount() > 0)
            ll.removeAllViews();
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        for (int i = 0; i < fromDB.length; i++) {
            Button b = new Button(this);
            b.setTransformationMethod(null);
            b.setText(fromDB[i]);
            b.setLayoutParams(lp);
            final int index = i;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlansActivity.this.onClick(v, index);
                }
            });
            ll.addView(b);
            buttonList.add(b);
        }


    }

    /**
     * this will run ig the user click on a button or on leave croup
     *
     */
    public void onClick(View v, int index) {
        CallMainActivitys.showExersiseCategoriesFromPlan(v,this,plans.get(index).getPlanID(),idUser, titel, false);
    }

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

    /**
     * top right the menu button
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (idUser == UserInfos.getUserID()) {
            MenuInflater inflator = getMenuInflater();
            inflator.inflate(R.menu.menu_plan, menu);
            this.menu = menu;
            return true;
        }
        return true;
    }

    /**
     * On click listener for the top right menu
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_addPlam) {
            addPlan();
        }
        if (id == R.id.menu_deletePlan) {
            deletePlan();
        }
        return true;
    }

    private String inputString = "";
    private void addPlan(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.dialog_p_insert));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(getResources().getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inputString = input.getText().toString();
               Plan p = new Plan();
                p.setPlanName(inputString);
                p.setUserID(idUser);
                PlanDataSource planDataSource  =new PlanDataSource(PlansActivity.this);
                planDataSource.createPlan(p);
                generateButtons();
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

    private void deletePlan(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle(getResources().getString(R.string.dialog_plantodelete));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        final List<Plan> planList = plans;

        for (Plan p : planList)
        {
            arrayAdapter.add(p.getPlanName());
        }

        builderSingle.setNegativeButton(getResources().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                PlanDataSource planDataSource = new PlanDataSource(PlansActivity.this);
                planDataSource.deletePlan(planList.get(which).getPlanID());
                generateButtons();
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(PlansActivity.this);
                builderInner.setMessage(strName);
                builderInner.setTitle(getResources().getString(R.string.dialog_selectedPlan)); //Hardcoded
                builderInner.setPositiveButton(getResources().getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
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

    //show all
    public void showExercisesCat(View v) {
        CallMainActivitys.showExersiseCatagory(v, this);
    }

    //show my plan
    public void showMyPlan(View v) {
        CallMainActivitys.showExersisePlans(v, this, UserInfos.getUserID(), true);
    }

}
