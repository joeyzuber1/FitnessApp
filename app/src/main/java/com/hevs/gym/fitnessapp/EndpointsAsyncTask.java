package com.hevs.gym.fitnessapp;

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

public class EndpointsAsyncTask extends AsyncTask<Void, Void, List<User>> {
    private static UserApi userApi = null;
    private static final String TAG = EndpointsAsyncTask.class.getName();
    private User user;

    EndpointsAsyncTask(){}

    EndpointsAsyncTask(User user){
        this.user = user;
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
                    .setRootUrl("http://ultimate-hydra-167114.appspot.com/")
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
            if(user != null){
                userApi.insert(user).execute();
                Log.i(TAG, "insert employee");
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
            for (User employee : result) {
                Log.i(TAG, "First name: " + employee.getFirstname() + " Last name: "
                        + employee.getLastname());
            }
        }
    }

}
