package com.hevs.gym.fitnessapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hevs.gym.fitnessapp.db.adabter.BodyPartDataSource;
import com.hevs.gym.fitnessapp.db.adabter.UserDataSource;
import com.hevs.gym.fitnessapp.db.objects.BodyPart;
import com.hevs.gym.fitnessapp.db.objects.User;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    /**
     * Infos about the database
     *
     */
    private static final String DATABASE_NAME = "Fitness2.db";
    private static final int DATABASE_VERSION = 1;
    private static SQLiteHelper instance;
    private Context context;


    /**
     * use a singelton
     * Allways only one Database
     */
    private SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
        this.context = context;
    }

    /**
     * Get the instanxce or make a new one
     *
     */
    public static SQLiteHelper getInstance(Context context){
        if(instance == null){
                instance = new SQLiteHelper(context.getApplicationContext());
                //Enable foreign key support
                instance.db.execSQL("PRAGMA foreign_keys = ON;");
                   }

        return instance;
    }

    /**
     * create the tables
     *
     */
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

    /**
     * if something change drop the tables
     * create the new tables
     */
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

    /**
     * fill some standart Data  so you can login and have some body parts
     *
     */
    public static void fillStandartData(Context context)
    {
        //User Admin
        UserDataSource uds = new UserDataSource(context);
        User admin = new User();
        admin.setAdministrator(true);
        admin.setMale(true);
        admin.setFirstname("Administrator");
        admin.setLastname("Administrator");
        admin.setNamelogin("admin");
        admin.setPassword("admin");
        uds.createUser(admin);

        //BodyParts
        BodyPart bpArm = new BodyPart();
        bpArm.setBodySection("Arm");
        BodyPart bpLeg = new BodyPart();
        bpLeg.setBodySection("Leg");
        BodyPart bpBack = new BodyPart();
        bpBack.setBodySection("Back");
        BodyPartDataSource bpds = new BodyPartDataSource(context);
        bpds.createBodyPart(bpLeg);
        bpds.createBodyPart(bpArm);
        bpds.createBodyPart(bpBack);
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


