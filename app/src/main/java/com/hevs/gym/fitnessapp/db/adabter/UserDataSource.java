package com.hevs.gym.fitnessapp.db.adabter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hevs.gym.fitnessapp.db.FitnessContract;
import com.hevs.gym.fitnessapp.db.SQLiteHelper;
import com.hevs.gym.fitnessapp.db.objects.GroupUser;
import com.hevs.gym.fitnessapp.db.objects.Plan;
import com.hevs.gym.fitnessapp.db.objects.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class UserDataSource {
    private SQLiteDatabase db;
    private Context context;

    /**
     *
     * Constructor of Userdata
     */
    public UserDataSource(Context context) {
        SQLiteHelper sqliteHelper = SQLiteHelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
        this.context = context;
    }

    /**
     * create a new user and give the id back
     *
     */
    public long createUser(User user) {
        long id;
        ContentValues values = new ContentValues();
        values.put(FitnessContract.UserEntry.KEY_NAMELOGIN, user.getNamelogin());
        values.put(FitnessContract.UserEntry.KEY_PASSWORD, user.getPassword());
        values.put(FitnessContract.UserEntry.KEY_FIRSTNAME, user.getFirstname());
        values.put(FitnessContract.UserEntry.KEY_LASTNAME, user.getLastname());
        if (user.isAdministrator()) {
            values.put(FitnessContract.UserEntry.KEY_ADMINISTRATOR, 1);
        } else {
            values.put(FitnessContract.UserEntry.KEY_ADMINISTRATOR,0 );
        }
        if (user.isMale()) {
            values.put(FitnessContract.UserEntry.KEY_ISMALE, 1);
        }else
        {
            values.put(FitnessContract.UserEntry.KEY_ISMALE, 0);
        }


        id = this.db.insert(FitnessContract.UserEntry.TABLE_USER, null, values);

        PlanDataSource pds = new PlanDataSource(context);
        Plan p = new Plan();
        p.setPlanName("MyPlan");
        p.setUserID(id);
        pds.createPlan(p);

        return id;
    }

    /**
     * get a user by id
     *
     */
    public User getUserById(long id) {
        String sql = "SELECT * FROM " + FitnessContract.UserEntry.TABLE_USER +
                " WHERE " + FitnessContract.UserEntry.KEY_USERID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        User user = new User();
        user.setUserID(cursor.getInt(cursor.getColumnIndex(FitnessContract.UserEntry.KEY_USERID)));
        user.setNamelogin(cursor.getString(cursor.getColumnIndex(FitnessContract.UserEntry.KEY_NAMELOGIN)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(FitnessContract.UserEntry.KEY_PASSWORD)));
        user.setFirstname(cursor.getString(cursor.getColumnIndex(FitnessContract.UserEntry.KEY_FIRSTNAME)));
        user.setLastname(cursor.getString(cursor.getColumnIndex(FitnessContract.UserEntry.KEY_LASTNAME)));
        if (cursor.getInt(cursor.getColumnIndex(FitnessContract.UserEntry.KEY_ADMINISTRATOR))== 0)
        {
            user.setAdministrator(false);
        }else
        {
            user.setAdministrator(true);
        }
        if (cursor.getInt(cursor.getColumnIndex(FitnessContract.UserEntry.KEY_ISMALE))== 0)
        {
            user.setMale(false);
        }else
        {
            user.setMale(true);
        }

        return user;
    }


    /**
     * get all users by id
     *
     */
    public List<User> getAllUsers() {
        List<User> persons = new ArrayList<User>();
        String sql = "SELECT * FROM " + FitnessContract.UserEntry.TABLE_USER + " ORDER BY " + FitnessContract.UserEntry.KEY_USERID;

        Cursor cursor = this.db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUserID(cursor.getInt(cursor.getColumnIndex(FitnessContract.UserEntry.KEY_USERID)));
                user.setNamelogin(cursor.getString(cursor.getColumnIndex(FitnessContract.UserEntry.KEY_NAMELOGIN)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(FitnessContract.UserEntry.KEY_PASSWORD)));
                user.setFirstname(cursor.getString(cursor.getColumnIndex(FitnessContract.UserEntry.KEY_FIRSTNAME)));
                user.setLastname(cursor.getString(cursor.getColumnIndex(FitnessContract.UserEntry.KEY_LASTNAME)));
                if (cursor.getInt(cursor.getColumnIndex(FitnessContract.UserEntry.KEY_ADMINISTRATOR))== 0)
                {
                    user.setAdministrator(false);
                }else
                {
                    user.setAdministrator(true);
                }
                if (cursor.getInt(cursor.getColumnIndex(FitnessContract.UserEntry.KEY_ISMALE))== 0)
                {
                    user.setMale(false);
                }else
                {
                    user.setMale(true);
                }


                persons.add(user);
            } while (cursor.moveToNext());
        }

        if (persons.size() == 0)
        {
            SQLiteHelper.fillStandartData(context);
            return getAllUsers();
        }

        return persons;
    }

    /**
     * get all users from a group
     *
     */
    public List<User> getAllUsersFromGroupID(long id) {
        GroupUsersDataSource groupUsersDataSource = new GroupUsersDataSource(context);
        List<GroupUser> groupUsers = groupUsersDataSource.getAllGroupUserByGroupID(id);
        List<User> users = new ArrayList<User>();
        for (GroupUser gu : groupUsers)
        {
            users.add(getUserById(gu.getUserID()));
        }
        return users;
    }

    /**
     *
     * update a user
     */
    public int updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put(FitnessContract.UserEntry.KEY_NAMELOGIN, user.getNamelogin());
        values.put(FitnessContract.UserEntry.KEY_PASSWORD, user.getPassword());
        values.put(FitnessContract.UserEntry.KEY_FIRSTNAME, user.getFirstname());
        values.put(FitnessContract.UserEntry.KEY_LASTNAME, user.getLastname());
        if (user.isAdministrator()) {
            values.put(FitnessContract.UserEntry.KEY_ADMINISTRATOR, 1);
        } else {
            values.put(FitnessContract.UserEntry.KEY_ADMINISTRATOR,0 );
        }
        if (user.isMale()) {
            values.put(FitnessContract.UserEntry.KEY_ISMALE, 1);
        }else
        {
            values.put(FitnessContract.UserEntry.KEY_ISMALE, 0);
        }

        return this.db.update(FitnessContract.UserEntry.TABLE_USER, values, FitnessContract.UserEntry.KEY_USERID + " = ?",
                new String[]{String.valueOf(user.getUserID())});
    }

    /**
     * delete a user
     *
     */
    public void deleteUser(long id) {
        GroupUsersDataSource guds = new GroupUsersDataSource(context);
        List<GroupUser> groupUsers = guds.getAllGroupUser();

        for (GroupUser groupUser : groupUsers) {
            if (groupUser.getUserID() == id)
                guds.deleteGroupUsers(groupUser.getGroupUserID());
        }

        PlanDataSource plds = new PlanDataSource(context);
        List<Plan> planList = plds.getPlanFromUserID(id);

        for (Plan plan : planList) {
            if (plan.getUserID() == id)
                plds.deletePlan(plan.getPlanID());
        }

        //delete the person
        this.db.delete(FitnessContract.UserEntry.TABLE_USER, FitnessContract.UserEntry.KEY_USERID + " = ?",
                new String[]{String.valueOf(id)});

    }

}
