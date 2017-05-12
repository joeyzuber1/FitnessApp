package com.hevs.gym.fitnessapp.db.endpointAsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.matthias.myapplication.backend.groupApi.GroupApi;
import com.example.matthias.myapplication.backend.groupApi.model.Group;
import com.example.matthias.myapplication.backend.groupUserApi.model.GroupUser;
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

public class GroupEndpointsAsyncTask extends AsyncTask<Void, Void, List<Group>> {
    private static GroupApi groupApi = null;
    private static final String TAG = GroupEndpointsAsyncTask.class.getName();
    private Group group;
    private long idGroup = -1;
    public GroupEndpointsAsyncTask(){}

    public GroupEndpointsAsyncTask(Group group){
        this.group = group;
    }

    public GroupEndpointsAsyncTask(long idUpdate, Group group){
        this.group = group;
        idGroup = idUpdate;
    }

    public GroupEndpointsAsyncTask(long idDelete){
        idGroup = idDelete;
    }

    @Override
    protected List<Group> doInBackground(Void... params) {
        if(groupApi == null){
            // Only do this once
            GroupApi.Builder builder = new GroupApi.Builder(AndroidHttp.newCompatibleTransport(),
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
            groupApi = builder.build();
        }

        try{
            // Call here the wished methods on the Endpoints
            // For instance insert
            if(group != null && idGroup == -1){
                groupApi.insert(group).execute();
                Log.i(TAG, "insert group");
            }

            if(group == null && idGroup != -1){
                groupApi.remove(idGroup).execute();
                Log.i(TAG, "delete group");
            }

            if(group != null && idGroup != -1){
                groupApi.update(idGroup, group).execute();
                Log.i(TAG, "update group");
            }
            // and for instance return the list of all employees
            return groupApi.list().execute().getItems();


        } catch (IOException e){
            Log.e(TAG, e.toString());
            return new ArrayList<Group>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<Group> result){

        if(result != null) {
            for (Group group : result) {
                Log.i(TAG, "Groupname: " + group.getGroupname());
            }
        }
    }

}
