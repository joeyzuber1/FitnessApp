package com.hevs.gym.fitnessapp.db.adabter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hevs.gym.fitnessapp.db.FitnessContract;
import com.hevs.gym.fitnessapp.db.SQLiteHelper;
import com.hevs.gym.fitnessapp.db.objects.GroupUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class GroupUsersDataSource {


    private SQLiteDatabase db;
    private Context context;

    /**
     *
     *
     */
    public GroupUsersDataSource(Context context) {
        SQLiteHelper sqliteHelper = SQLiteHelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
        this.context = context;
    }

    /**
     *
     *
     */
    public long createGrouUser(GroupUser groupUser) {
        long id;
        ContentValues values = new ContentValues();
        values.put(FitnessContract.GroupUsersEntry.KEY_GroupID, groupUser.getGroupID());
        values.put(FitnessContract.GroupUsersEntry.KEY_USERID, groupUser.getUserID());
        id = this.db.insert(FitnessContract.GroupUsersEntry.TABLE_GROUPUSER, null, values);

        return id;
    }

    /**
     *
     *
     */
    public List<GroupUser> getAllGroupUser() {
        List<GroupUser> groupUsers = new ArrayList<GroupUser>();
        String sql = "SELECT * FROM " + FitnessContract.GroupUsersEntry.TABLE_GROUPUSER + " ORDER BY " + FitnessContract.GroupUsersEntry.KEY_GROUPUSERID;

        Cursor cursor = this.db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                GroupUser planExercise = new GroupUser();
                planExercise.setGroupID(cursor.getInt(cursor.getColumnIndex(FitnessContract.GroupUsersEntry.KEY_GroupID)));
                planExercise.setGroupUserID(cursor.getInt(cursor.getColumnIndex(FitnessContract.GroupUsersEntry.KEY_GROUPUSERID)));
                planExercise.setUserID(cursor.getInt(cursor.getColumnIndex(FitnessContract.GroupUsersEntry.KEY_USERID)));

                groupUsers.add(planExercise);
            } while (cursor.moveToNext());
        }

        return groupUsers;
    }

    /**
     *
     *
     */
    public void deleteGroupUsers(long id) {
        this.db.delete(FitnessContract.GroupUsersEntry.TABLE_GROUPUSER, FitnessContract.GroupUsersEntry.KEY_GROUPUSERID + " = ?",
                new String[]{String.valueOf(id)});

    }
}



