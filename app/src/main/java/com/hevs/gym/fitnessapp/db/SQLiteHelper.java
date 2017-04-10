package com.hevs.gym.fitnessapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    //Infos about database
    private static final String DATABASE_NAME = "Fitness.db";
    private static final int DATABASE_VERSION = 1;
    private static SQLiteHelper instance;


    //use a singleton
    //we want always just one instance of the database
    private SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    public static SQLiteHelper getInstance(Context context){
        if(instance == null){
            instance = new SQLiteHelper(context.getApplicationContext());
            //Enable foreign key support
            instance.db.execSQL("PRAGMA foreign_keys = ON;");
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FitnessContract.UserEntry.CREATE_TABLE_USER);
        db.execSQL(FitnessContract.GroupEntry.CREATE_TABLE_GROUP);
        db.execSQL(FitnessContract.GroupUsersEntry.CREATE_TABLE_GROUPUSER);
        db.execSQL(FitnessContract.BodyPartEntry.CREATE_TABLE_BODYPART);
        db.execSQL(FitnessContract.ExerciseEntry.CREATE_TABLE_EXERCISE);
        db.execSQL(FitnessContract.PlanEntry.CREATE_TABLE_PLAN);
        db.execSQL(FitnessContract.PlanExerciseEntry.CREATE_TABLE_PLANEXERCISE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop old tables
        db.execSQL("DROP TABLE IF EXISTS " + FitnessContract.PlanExerciseEntry.TABLE_PLANEXERCISE);
        db.execSQL("DROP TABLE IF EXISTS " + FitnessContract.PlanEntry.TABLE_PLAN);
        db.execSQL("DROP TABLE IF EXISTS " + FitnessContract.ExerciseEntry.TABLE_EXERCISE);
        db.execSQL("DROP TABLE IF EXISTS " + FitnessContract.BodyPartEntry.TABLE_BODYPART);
        db.execSQL("DROP TABLE IF EXISTS " + FitnessContract.GroupUsersEntry.TABLE_GROUPUSER);
        db.execSQL("DROP TABLE IF EXISTS " + FitnessContract.GroupEntry.TABLE_GROUP);
        db.execSQL("DROP TABLE IF EXISTS " + FitnessContract.UserEntry.TABLE_USER);
        //create new tables
        onCreate(db);
    }



//
//	/**
//	 * get datetime as a string
//	 * */
//	private String getDateTime() {
//		SimpleDateFormat dateFormat = new SimpleDateFormat(
//				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//		Date date = new Date();
//		return dateFormat.format(date);
//	}

}


