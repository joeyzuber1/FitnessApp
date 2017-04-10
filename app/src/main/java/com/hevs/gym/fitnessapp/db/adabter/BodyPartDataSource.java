package com.hevs.gym.fitnessapp.db.adabter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hevs.gym.fitnessapp.db.FitnessContract;
import com.hevs.gym.fitnessapp.db.SQLiteHelper;
import com.hevs.gym.fitnessapp.db.objects.BodyPart;
import com.hevs.gym.fitnessapp.db.objects.Exercise;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class BodyPartDataSource {
    private SQLiteDatabase db;
    private Context context;

    /**
     *
     *
     */
    public BodyPartDataSource(Context context) {
        SQLiteHelper sqliteHelper = SQLiteHelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
        this.context = context;
    }

    /**
     *
     *
     */
    public long createBodyPart(BodyPart bodypart) {
        long id;
        ContentValues values = new ContentValues();
        values.put(FitnessContract.BodyPartEntry.KEY_BODYSECTION, bodypart.getBodySection());
        id = this.db.insert(FitnessContract.BodyPartEntry.TABLE_BODYPART, null, values);

        return id;
    }

    /**
     *
     *
     */
    public BodyPart getBodyPartById(long id) {
        String sql = "SELECT * FROM " + FitnessContract.BodyPartEntry.TABLE_BODYPART +
                " WHERE " + FitnessContract.BodyPartEntry.KEY_PARTOFBODYID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        BodyPart bodyPart = new BodyPart();
        bodyPart.setPartOfBodyID(cursor.getInt(cursor.getColumnIndex(FitnessContract.BodyPartEntry.KEY_PARTOFBODYID)));
        bodyPart.setBodySection(cursor.getString(cursor.getColumnIndex(FitnessContract.BodyPartEntry.KEY_BODYSECTION)));


        return bodyPart;
    }

    /**
     *
     *
     */
    public List<BodyPart> getAllBodyParts() {
        List<BodyPart> bodyparts = new ArrayList<BodyPart>();
        String sql = "SELECT * FROM " + FitnessContract.BodyPartEntry.TABLE_BODYPART + " ORDER BY " + FitnessContract.BodyPartEntry.KEY_PARTOFBODYID;

        Cursor cursor = this.db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                BodyPart bodyPart = new BodyPart();
                bodyPart.setPartOfBodyID(cursor.getInt(cursor.getColumnIndex(FitnessContract.BodyPartEntry.KEY_PARTOFBODYID)));
                bodyPart.setBodySection(cursor.getString(cursor.getColumnIndex(FitnessContract.BodyPartEntry.KEY_BODYSECTION)));

                bodyparts.add(bodyPart);
            } while (cursor.moveToNext());
        }

        return bodyparts;
    }

    /**
     *
     *
     */
    public int updateBodyPart(BodyPart bodyPart) {
        ContentValues values = new ContentValues();
        values.put(FitnessContract.BodyPartEntry.KEY_BODYSECTION, bodyPart.getBodySection());

        return this.db.update(FitnessContract.BodyPartEntry.TABLE_BODYPART, values, FitnessContract.BodyPartEntry.KEY_PARTOFBODYID + " = ?",
                new String[]{String.valueOf(bodyPart.getPartOfBodyID())});
    }

    /**
     *
     *
     */
    public boolean deleteBodyPart(long id) {
      //Exercises müssen Angepasst werden oder geprüft werden ob vorhanden wenn ja nicht möglich
        ExerciseDataSource exsds = new ExerciseDataSource(context);
        List<Exercise> exs = exsds.getAllExercises();

        for(Exercise ex : exs){
            if (ex.getBodyPart() == id)
                return false;
        }

        this.db.delete(FitnessContract.BodyPartEntry.TABLE_BODYPART, FitnessContract.BodyPartEntry.KEY_PARTOFBODYID + " = ?",
                new String[]{String.valueOf(id)});
        return true;

    }
}