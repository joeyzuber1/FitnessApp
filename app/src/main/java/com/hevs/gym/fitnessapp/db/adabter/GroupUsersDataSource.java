package com.hevs.gym.fitnessapp.db.adabter;

import android.content.Context;

import com.example.matthias.myapplication.backend.groupUserApi.model.GroupUser;
import com.hevs.gym.fitnessapp.db.endpointAsyncTasks.GroupUserEndpointsAsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class GroupUsersDataSource {
    private Context context;

    /**
     * Constructor of GroupUser
     *
     */
    public GroupUsersDataSource(Context context) {
        this.context = context;
    }

    /**
     *
     * create a new GroupUser
     */
    public long createGroupUser(GroupUser groupUser) {
        long id;
        List<GroupUser> groupUserList = getAllGroupUser();
        if (groupUserList.size() != 0) {
            groupUser.setGroupUserID(groupUserList.get(groupUserList.size() - 1).getGroupUserID() + 1);
        }else
        {
            groupUser.setGroupUserID(1l);
        }
        new GroupUserEndpointsAsyncTask(groupUser).execute();

        return groupUser.getGroupID();
    }

    /**
     * get GroupUser by id Group and ID User
     *
     */
    public GroupUser gettGrouUserFromUserIDGroupID(long groupid, long userID) {
        List<GroupUser> groupUserList = getAllGroupUser();
        for (GroupUser gp:groupUserList) {
            if (gp.getGroupID() == groupid && gp.getUserID() == userID)
                return gp;
        }
        return null;
    }

    /**
     * get all groupusers
     *
     */
    public List<GroupUser> getAllGroupUser() {
        List<GroupUser> groupUserList = new ArrayList<>();
        try {
            groupUserList = new GroupUserEndpointsAsyncTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (groupUserList == null)
            return new ArrayList<GroupUser>();
        return groupUserList;
    }

    /**
     * get all groupusers by user id
     *
     */
    public List<GroupUser> getAllGroupUserByUseID(long userID) {
        List<GroupUser> groupUserList = getAllGroupUser();
        List<GroupUser> dGroupUserList1 = new ArrayList<>();
        for (GroupUser groupUser : groupUserList)
        {
            if (groupUser.getUserID() == userID)
                dGroupUserList1.add(groupUser);
        }
        return dGroupUserList1;
    }

    /**
     * get all group users by group id
     *
     */
    public List<GroupUser> getAllGroupUserByGroupID(long groupID) {
        List<GroupUser> groupUserList = getAllGroupUser();
        List<GroupUser> dGroupUserList1 = new ArrayList<>();
        for (GroupUser groupUser : groupUserList)
        {
            if (groupUser.getGroupID() == groupID)
                dGroupUserList1.add(groupUser);
        }
        return dGroupUserList1;
    }

    /**
     * delete a group user
     *
     */
    public void deleteGroupUsers(long id) {
       new GroupUserEndpointsAsyncTask(id).execute();
    }
}



