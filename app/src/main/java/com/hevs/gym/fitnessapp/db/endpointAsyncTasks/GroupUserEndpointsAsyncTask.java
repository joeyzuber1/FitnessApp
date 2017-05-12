package com.hevs.gym.fitnessapp.db.endpointAsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.matthias.myapplication.backend.groupUserApi.GroupUserApi;
import com.example.matthias.myapplication.backend.groupUserApi.model.GroupUser;
import com.example.matthias.myapplication.backend.planApi.model.Plan;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthias and Joey on 09.05.2017.
 */

public class GroupUserEndpointsAsyncTask extends AsyncTask<Void, Void, List<GroupUser>> {
    private static GroupUserApi groupUserApi = null;
    private static final String TAG = GroupUserEndpointsAsyncTask.class.getName();
    private GroupUser groupUser;
    private long idGroupUser = -1;

    public GroupUserEndpointsAsyncTask(){}

    public GroupUserEndpointsAsyncTask(GroupUser groupUser){
        this.groupUser = groupUser;
    }

    public GroupUserEndpointsAsyncTask(long idUpdate, GroupUser groupUser){
        this.groupUser = groupUser;
        idGroupUser = idUpdate;
    }

    public GroupUserEndpointsAsyncTask(long idDelete){
        idGroupUser = idDelete;
    }

    @Override
    protected List<GroupUser> doInBackground(Void... params) {
        if(groupUserApi == null){
            // Only do this once
            GroupUserApi.Builder builder = new GroupUserApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    // if you deploy on the cloud backend, use your app name
                    // such as https://<your-app-id>.appspot.com
                    .setRootUrl("https://fitnessapp2-167316.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            groupUserApi = builder.build();
        }

        try{
            // Call here the wished methods on the Endpoints
            // For instance insert
            if(groupUser != null && idGroupUser == -1){
                groupUserApi.insert(groupUser).execute();
                Log.i(TAG, "insert groupUser");
            }

            if(groupUser == null && idGroupUser != -1){
                groupUserApi.remove(idGroupUser).execute();
                Log.i(TAG, "delete groupUser");
            }

            if(groupUser != null && idGroupUser != -1){
                groupUserApi.update(idGroupUser, groupUser).execute();
                Log.i(TAG, "update groupUser");
            }
            // and for instance return the list of all employees
            return groupUserApi.list().execute().getItems();

        } catch (IOException e){
            Log.e(TAG, e.toString());
            return new ArrayList<GroupUser>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<GroupUser> result){

        if(result != null) {
            for (GroupUser groupUser : result) {
                Log.i(TAG, "UserID: " + groupUser.getUserID() + " GroupID: "
                        + groupUser.getGroupID());
            }
        }
    }

}
