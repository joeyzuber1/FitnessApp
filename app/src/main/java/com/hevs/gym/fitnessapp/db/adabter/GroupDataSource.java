package com.hevs.gym.fitnessapp.db.adabter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hevs.gym.fitnessapp.db.FitnessContract;
import com.hevs.gym.fitnessapp.db.SQLiteHelper;
import com.hevs.gym.fitnessapp.db.objects.Group;
import com.hevs.gym.fitnessapp.db.objects.GroupUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class GroupDataSource {
    private SQLiteDatabase db;
    private Context context;

    /**
     *
     * Constructor of the group data sources
     */
    public GroupDataSource(Context context) {
        SQLiteHelper sqliteHelper = SQLiteHelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
        this.context = context;
    }

    /**
     * insert a group and give the id back
     *
     */
    public long createGroup(Group group) {
        long id;
        ContentValues values = new ContentValues();
        values.put(FitnessContract.GroupEntry.KEY_GROUPNAME, group.getGroupname());
        id = this.db.insert(FitnessContract.GroupEntry.TABLE_GROUP, null, values);

        return (id);
    }

    /**
     * get a group by id
     *
     */
    public Group getGroupById(long id) {
        String sql = "SELECT * FROM " + FitnessContract.GroupEntry.TABLE_GROUP +
                " WHERE " + FitnessContract.GroupEntry.KEY_GROUPID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Group group = new Group();
        group.setGroupID(cursor.getInt(cursor.getColumnIndex(FitnessContract.GroupEntry.KEY_GROUPID)));
        group.setGroupname(cursor.getString(cursor.getColumnIndex(FitnessContract.GroupEntry.KEY_GROUPNAME)));


        return group;
    }

    /**
     * find a group by group name
     *
     */
    public long findGroupByName(String name) {
        List<Group> groups = getAllGroup();
        for (Group p : groups)
        {
            if (p.getGroupname().equals(name))
            {
                return p.getGroupID();
            }
        }
        return  -1;
    }


    /**
     * get all groups
     *
     */
    public List<Group> getAllGroup() {
        List<Group> groups = new ArrayList<Group>();
        String sql = "SELECT * FROM " + FitnessContract.GroupEntry.TABLE_GROUP + " ORDER BY " + FitnessContract.GroupEntry.KEY_GROUPID;

        Cursor cursor = this.db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Group group = new Group();
                group.setGroupID(cursor.getInt(cursor.getColumnIndex(FitnessContract.GroupEntry.KEY_GROUPID)));
                group.setGroupname(cursor.getString(cursor.getColumnIndex(FitnessContract.GroupEntry.KEY_GROUPNAME)));

                groups.add(group);
            } while (cursor.moveToNext());
        }

        return groups;
    }


    /**
     * get all groups from one user
     *
     */
    public List<Group> getAllGroupByUserID(long id) {
        GroupUsersDataSource groupUsersDataSource = new GroupUsersDataSource(context);
        List<GroupUser> groupUsers = groupUsersDataSource.getAllGroupUserByUseID(id);
        List<Group> allGrous = getAllGroup();

        List<Group> sortetGroup = new ArrayList<Group>();
        for (GroupUser gp: groupUsers) {
            sortetGroup.add(getGroupById(gp.getGroupID()));
        }
        return sortetGroup;
    }

    /**
     * update a group
     *
     */
    public int updateGroup(Group group) {
        ContentValues values = new ContentValues();
        values.put(FitnessContract.GroupEntry.KEY_GROUPNAME, group.getGroupname());

        return this.db.update(FitnessContract.GroupEntry.TABLE_GROUP, values, FitnessContract.GroupEntry.KEY_GROUPID + " = ?",
                new String[]{String.valueOf(group.getGroupID())});
    }

    /**
     * delete a group
     *
     */
    public void deleteGroup(long id) {
        GroupUsersDataSource guds = new GroupUsersDataSource(context);
        List<GroupUser> groupUsers = guds.getAllGroupUser();

        for(GroupUser groupUser : groupUsers){
            if (groupUser.getGroupID() == id)
                guds.deleteGroupUsers(groupUser.getGroupUserID());
        }

        this.db.delete(FitnessContract.GroupEntry.TABLE_GROUP, FitnessContract.GroupEntry.KEY_GROUPID + " = ?",
                new String[]{String.valueOf(id)});

    }
}