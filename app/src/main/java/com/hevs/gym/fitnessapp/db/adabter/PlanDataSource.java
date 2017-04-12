package com.hevs.gym.fitnessapp.db.adabter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hevs.gym.fitnessapp.db.FitnessContract;
import com.hevs.gym.fitnessapp.db.SQLiteHelper;
import com.hevs.gym.fitnessapp.db.objects.Plan;
import com.hevs.gym.fitnessapp.db.objects.PlanExercise;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class PlanDataSource {

    private SQLiteDatabase db;
    private Context context;

    /**
     * Constructor of Plan source
     *
     */
    public PlanDataSource(Context context) {
        SQLiteHelper sqliteHelper = SQLiteHelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
        this.context = context;
    }

    /**
     * Create a new plan and gives the id back
     *
     */
    public long createPlan(Plan plan) {
        long id;
        ContentValues values = new ContentValues();
        values.put(FitnessContract.PlanEntry.KEY_USERID, plan.getUserID());
        values.put(FitnessContract.PlanEntry.KEY_NAME, plan.getPlanName());
        id = this.db.insert(FitnessContract.PlanEntry.TABLE_PLAN, null, values);

        return id;
    }

    /**
     * get a plan by id
     *
     */
    public Plan getPlanById(long id) {
        String sql = "SELECT * FROM " + FitnessContract.PlanEntry.TABLE_PLAN +
                " WHERE " + FitnessContract.PlanEntry.KEY_PLANID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Plan plan = new Plan();
        plan.setPlanID(cursor.getInt(cursor.getColumnIndex(FitnessContract.PlanEntry.KEY_PLANID)));
        plan.setUserID(cursor.getInt(cursor.getColumnIndex(FitnessContract.PlanEntry.KEY_USERID)));
        plan.setPlanName(cursor.getString(cursor.getColumnIndex(FitnessContract.PlanEntry.KEY_NAME)));

        return plan;
    }

    /**
     * get all plans
     *
     */
    public List<Plan> getAllPlan() {
        List<Plan> plans = new ArrayList<Plan>();
        String sql = "SELECT * FROM " + FitnessContract.PlanEntry.TABLE_PLAN + " ORDER BY " + FitnessContract.PlanEntry.KEY_PLANID;

        Cursor cursor = this.db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Plan plan = new Plan();
                plan.setPlanID(cursor.getInt(cursor.getColumnIndex(FitnessContract.PlanEntry.KEY_PLANID)));
                plan.setUserID(cursor.getInt(cursor.getColumnIndex(FitnessContract.PlanEntry.KEY_USERID)));
                plan.setPlanName(cursor.getString(cursor.getColumnIndex(FitnessContract.PlanEntry.KEY_NAME)));

                plans.add(plan);
            } while (cursor.moveToNext());
        }

        return plans;
    }

    /**
     * get plans from user id
     *
     */
    public List<Plan> getPlanFromUserID(long idUser) {
        List<Plan> plans = new ArrayList<Plan>();
        String sql = "SELECT * FROM " + FitnessContract.PlanEntry.TABLE_PLAN +  " WHERE " +
                FitnessContract.PlanEntry.KEY_USERID + " = " + idUser + " ORDER BY " +
                FitnessContract.PlanEntry.KEY_PLANID;

        Cursor cursor = this.db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Plan plan = new Plan();
                plan.setPlanID(cursor.getInt(cursor.getColumnIndex(FitnessContract.PlanEntry.KEY_PLANID)));
                plan.setUserID(cursor.getInt(cursor.getColumnIndex(FitnessContract.PlanEntry.KEY_USERID)));
                plan.setPlanName(cursor.getString(cursor.getColumnIndex(FitnessContract.PlanEntry.KEY_NAME)));

                plans.add(plan);
            } while (cursor.moveToNext());
        }

        return plans;
    }

    /**
     *
     * update a planName
     */
    public int updatePlanName(Plan plan) {
        ContentValues values = new ContentValues();
        values.put(FitnessContract.PlanEntry.KEY_NAME, plan.getPlanName());

        return this.db.update(FitnessContract.PlanEntry.TABLE_PLAN, values, FitnessContract.PlanEntry.KEY_PLANID + " = ?",
                new String[]{String.valueOf(plan.getPlanID())});
    }

    /**
     * delete a plan
     *
     */
    public void deletePlan(long id) {
        PlanExerciseDataSource plds= new PlanExerciseDataSource(context);

        List<PlanExercise> planExercises = plds.getAllPlanExercise();

        for(PlanExercise planExercise : planExercises){
            if (planExercise.getPlanID() == id)
            plds.deletePlanExercise(planExercise.getPlanExerciseID());
        }

        this.db.delete(FitnessContract.PlanEntry.TABLE_PLAN, FitnessContract.PlanEntry.KEY_PLANID + " = ?",
                new String[]{String.valueOf(id)});

    }
}
