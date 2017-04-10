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

public class PlanExerciseDataSource {

    private SQLiteDatabase db;
    private Context context;

    /**
     *
     *
     */
    public PlanExerciseDataSource(Context context) {
        SQLiteHelper sqliteHelper = SQLiteHelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
        this.context = context;
    }

    /**
     *
     *
     */
    public long createPlanExercise(PlanExercise planExercise) {
        long id;
        ContentValues values = new ContentValues();
        values.put(FitnessContract.PlanExerciseEntry.KEY_EXERCISEID, planExercise.getExerciseID());
        values.put(FitnessContract.PlanExerciseEntry.KEY_PLANID, planExercise.getExerciseID());
        id = this.db.insert(FitnessContract.PlanExerciseEntry.TABLE_PLANEXERCISE, null, values);

        return id;
    }

    /**
     *
     *
     */
    public List<PlanExercise> getAllPlanExercise() {
        List<PlanExercise> planExercises = new ArrayList<PlanExercise>();
        String sql = "SELECT * FROM " + FitnessContract.PlanExerciseEntry.TABLE_PLANEXERCISE + " ORDER BY " + FitnessContract.PlanExerciseEntry.KEY_PLANEXERCISEID;

        Cursor cursor = this.db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                PlanExercise planExercise = new PlanExercise();
                planExercise.setExerciseID(cursor.getInt(cursor.getColumnIndex(FitnessContract.PlanExerciseEntry.KEY_EXERCISEID)));
                planExercise.setPlanID(cursor.getInt(cursor.getColumnIndex(FitnessContract.PlanExerciseEntry.KEY_PLANID)));
                planExercise.setPlanExerciseID(cursor.getInt(cursor.getColumnIndex(FitnessContract.PlanExerciseEntry.KEY_PLANEXERCISEID)));

                planExercises.add(planExercise);
            } while (cursor.moveToNext());
        }

        return planExercises;
    }

    /**
     *
     *
     */
    public void deletePlanExercise(long id) {
        this.db.delete(FitnessContract.PlanExerciseEntry.TABLE_PLANEXERCISE, FitnessContract.PlanExerciseEntry.KEY_PLANEXERCISEID + " = ?",
                new String[]{String.valueOf(id)});

    }
}

