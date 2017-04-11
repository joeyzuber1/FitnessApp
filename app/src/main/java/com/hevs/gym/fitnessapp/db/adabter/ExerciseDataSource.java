package com.hevs.gym.fitnessapp.db.adabter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hevs.gym.fitnessapp.db.FitnessContract;
import com.hevs.gym.fitnessapp.db.SQLiteHelper;
import com.hevs.gym.fitnessapp.db.objects.Exercise;
import com.hevs.gym.fitnessapp.db.objects.PlanExercise;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class ExerciseDataSource {

    private SQLiteDatabase db;
    private Context context;

    /**
     *
     *
     */
    public ExerciseDataSource(Context context) {
        SQLiteHelper sqliteHelper = SQLiteHelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
        this.context = context;
    }

    /**
     *
     *
     */
    public long createExercise(Exercise exercise) {
        long id;
        ContentValues values = new ContentValues();
        values.put(FitnessContract.ExerciseEntry.KEY_PARTOFBODYID, exercise.getBodyPart());
        values.put(FitnessContract.ExerciseEntry.KEY_DESCRITPION, exercise.getExerciseDescription());
        values.put(FitnessContract.ExerciseEntry.KEY_NAME, exercise.getExerciseName());
        id = this.db.insert(FitnessContract.BodyPartEntry.TABLE_BODYPART, null, values);

        return id;
    }

    /**
     *
     *
     */
    public Exercise getExerciseById(long id) {
        String sql = "SELECT * FROM " + FitnessContract.ExerciseEntry.TABLE_EXERCISE +
                " WHERE " + FitnessContract.ExerciseEntry.KEY_EXERCISEID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Exercise exercise = new Exercise();
        exercise.setExerciseID(cursor.getInt(cursor.getColumnIndex(FitnessContract.ExerciseEntry.KEY_EXERCISEID)));
        exercise.setBodyPart(cursor.getInt(cursor.getColumnIndex(FitnessContract.ExerciseEntry.KEY_PARTOFBODYID)));
        exercise.setExerciseDescription(cursor.getString(cursor.getColumnIndex(FitnessContract.ExerciseEntry.KEY_DESCRITPION)));
        exercise.setExerciseName(cursor.getString(cursor.getColumnIndex(FitnessContract.ExerciseEntry.KEY_NAME)));


        return exercise;
    }

    /**
     *
     *
     */
    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<Exercise>();
        String sql = "SELECT * FROM " + FitnessContract.ExerciseEntry.TABLE_EXERCISE + " ORDER BY " + FitnessContract.ExerciseEntry.KEY_EXERCISEID;

        Cursor cursor = this.db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise();
                exercise.setExerciseID(cursor.getInt(cursor.getColumnIndex(FitnessContract.ExerciseEntry.KEY_EXERCISEID)));
                exercise.setBodyPart(cursor.getInt(cursor.getColumnIndex(FitnessContract.ExerciseEntry.KEY_PARTOFBODYID)));
                exercise.setExerciseDescription(cursor.getString(cursor.getColumnIndex(FitnessContract.ExerciseEntry.KEY_DESCRITPION)));
                exercise.setExerciseName(cursor.getString(cursor.getColumnIndex(FitnessContract.ExerciseEntry.KEY_NAME)));

                exercises.add(exercise);
            } while (cursor.moveToNext());
        }

        return exercises;
    }

    /**
     *
     *
     */
    public List<Exercise> getAllExercisesFromBodyPartID(long BodyPartID) {
        List<Exercise> exercises = new ArrayList<Exercise>();
        String sql = "SELECT * FROM " + FitnessContract.ExerciseEntry.TABLE_EXERCISE + " WHERE "
                + FitnessContract.ExerciseEntry.KEY_PARTOFBODYID + " = " + BodyPartID + " ORDER BY "
                + FitnessContract.ExerciseEntry.KEY_EXERCISEID;

        Cursor cursor = this.db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise();
                exercise.setExerciseID(cursor.getInt(cursor.getColumnIndex(FitnessContract.ExerciseEntry.KEY_EXERCISEID)));
                exercise.setBodyPart(cursor.getInt(cursor.getColumnIndex(FitnessContract.ExerciseEntry.KEY_PARTOFBODYID)));
                exercise.setExerciseDescription(cursor.getString(cursor.getColumnIndex(FitnessContract.ExerciseEntry.KEY_DESCRITPION)));
                exercise.setExerciseName(cursor.getString(cursor.getColumnIndex(FitnessContract.ExerciseEntry.KEY_NAME)));

                exercises.add(exercise);
            } while (cursor.moveToNext());
        }

        return exercises;
    }

    /**
     *
     *
     */
    public int updateExercises(Exercise exercise) {
        ContentValues values = new ContentValues();
        values.put(FitnessContract.ExerciseEntry.KEY_PARTOFBODYID, exercise.getBodyPart());
        values.put(FitnessContract.ExerciseEntry.KEY_DESCRITPION, exercise.getExerciseDescription());
        values.put(FitnessContract.ExerciseEntry.KEY_NAME, exercise.getExerciseName());

        return this.db.update(FitnessContract.ExerciseEntry.TABLE_EXERCISE, values, FitnessContract.ExerciseEntry.KEY_EXERCISEID + " = ?",
                new String[]{String.valueOf(exercise.getExerciseID())});
    }

    /**
     *
     *
     */
    public void deleteExercise(long id) {
        PlanExerciseDataSource plds= new PlanExerciseDataSource(context);

        List<PlanExercise> planExercises = plds.getAllPlanExercise();

        for(PlanExercise planExercise : planExercises){
            if (planExercise.getExerciseID() == id)
            plds.deletePlanExercise(planExercise.getPlanExerciseID());
        }


        this.db.delete(FitnessContract.ExerciseEntry.TABLE_EXERCISE, FitnessContract.ExerciseEntry.KEY_EXERCISEID + " = ?",
                new String[]{String.valueOf(id)});

    }
}

