package com.hevs.gym.fitnessapp.db.endpointAsyncTasks;

import android.os.AsyncTask;

import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import com.example.matthias.myapplication.backend.userApi.model.User;
import com.example.matthias.myapplication.backend.userApi.UserApi;

/**
 * Created by Matthias and Joey on 09.05.2017.
 */

public class UserEndpointsAsyncTask extends AsyncTask<Void, Void, List<User>> {
    private static UserApi userApi = null;
    private static final String TAG = UserEndpointsAsyncTask.class.getName();
    private User user;
    private long idUser = -1;

    public UserEndpointsAsyncTask(){}

    public UserEndpointsAsyncTask(User user){
        this.user = user;
    }

    public UserEndpointsAsyncTask(long idUpdate, User user){
        this.user = user;
        idUser = idUpdate;
    }

    public UserEndpointsAsyncTask(long idDelete){
       idUser = idDelete;
    }



    @Override
    protected List<User> doInBackground(Void... params) {
        if(userApi == null){
            // Only do this once
            UserApi.Builder builder = new UserApi.Builder(AndroidHttp.newCompatibleTransport(),
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
            userApi = builder.build();
        }

        try{
            // Call here the wished methods on the Endpoints
            // For instance insert
            if(user != null && idUser == -1){
                userApi.insert(user).execute();
                Log.i(TAG, "insert user");
            }

            if(user == null && idUser != -1){
                userApi.remove(idUser).execute();
                Log.i(TAG, "delete user");
            }

            if(user != null && idUser != -1){
                userApi.update(idUser, user).execute();
                Log.i(TAG, "update user");
            }
            // and for instance return the list of all employees
            return userApi.list().execute().getItems();

        } catch (IOException e){
            Log.e(TAG, e.toString());
            return new ArrayList<User>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<User> result){

        if(result != null) {
            for (User user : result) {
                Log.i(TAG, "First name: " + user.getFirstname() + " Last name: "
                        + user.getLastname());
            }
        }
    }




}
