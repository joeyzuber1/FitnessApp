package com.hevs.gym.fitnessapp.db.adabter;
import android.content.Context;

import com.example.matthias.myapplication.backend.groupApi.model.Group;
import com.example.matthias.myapplication.backend.groupUserApi.model.GroupUser;
import com.hevs.gym.fitnessapp.db.endpointAsyncTasks.GroupEndpointsAsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class GroupDataSource {
    private Context context;

    /**
     *
     * Constructor of the group data sources
     */
    public GroupDataSource(Context context) {
        this.context = context;
    }

    /**
     * insert a group and give the id back
     *
     */
    public long createGroup(Group group) {
        long id;

        List<Group> groupList = getAllGroup();
        if (groupList.size() != 0) {
            group.setGroupID(groupList.get(groupList.size() - 1).getGroupID() + 1);
        }else
        {
            group.setGroupID(1l);
        }
        new GroupEndpointsAsyncTask(group).execute();

        return group.getGroupID();
    }

    /**
     * get a group by id
     *
     */
    public Group getGroupById(long id) {
        List<Group> groupList = getAllGroup();
        for (Group g:groupList) {
            if (g.getGroupID() == id)
                return g;
        }
        return null;
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
        List<Group> groupList = new ArrayList<>();
        try {
            groupList = new GroupEndpointsAsyncTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (groupList == null)
            return new ArrayList<Group>();
        return groupList;
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
    public void updateGroup(Group group) {
        new GroupEndpointsAsyncTask(group.getGroupID(), group).execute();
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

      new GroupEndpointsAsyncTask(id).execute();

    }
}