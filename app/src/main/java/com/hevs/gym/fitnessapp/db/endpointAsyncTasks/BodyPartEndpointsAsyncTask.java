package com.hevs.gym.fitnessapp.db.endpointAsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.matthias.myapplication.backend.bodyPartApi.BodyPartApi;
import com.example.matthias.myapplication.backend.bodyPartApi.model.BodyPart;
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

public class BodyPartEndpointsAsyncTask extends AsyncTask<Void, Void, List<BodyPart>> {
    private static BodyPartApi bodyPartApi = null;
    private static final String TAG = BodyPartEndpointsAsyncTask.class.getName();
    private BodyPart bodyPart;
    private long idBodyPart = -1;

    public BodyPartEndpointsAsyncTask(){}

    public BodyPartEndpointsAsyncTask(BodyPart bodyPart){
        this.bodyPart = bodyPart;
    }

    public BodyPartEndpointsAsyncTask(long idUpdate, BodyPart bodyPart){
        this.bodyPart = bodyPart;
        idBodyPart = idUpdate;
    }

    public BodyPartEndpointsAsyncTask(long idDelete){
        idBodyPart = idDelete;
    }

    @Override
    protected List<BodyPart> doInBackground(Void... params) {
        if(bodyPartApi == null){
            // Only do this once
            BodyPartApi.Builder builder = new BodyPartApi.Builder(AndroidHttp.newCompatibleTransport(),
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
            bodyPartApi = builder.build();
        }

        try{
            // Call here the wished methods on the Endpoints
            // For instance insert
            if(bodyPart != null && idBodyPart == -1){
                bodyPartApi.insert(bodyPart).execute();
                Log.i(TAG, "insert bodyPart");
            }

            if(bodyPart == null && idBodyPart != -1){
                bodyPartApi.remove(idBodyPart).execute();
                Log.i(TAG, "delete bodyPart");
            }

            if(bodyPart != null && idBodyPart != -1){
                bodyPartApi.update(idBodyPart, bodyPart).execute();
                Log.i(TAG, "update bodyPart");
            }
            // and for instance return the list of all employees
            return bodyPartApi.list().execute().getItems();


        } catch (IOException e){
            Log.e(TAG, e.toString());
            return new ArrayList<BodyPart>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<BodyPart> result){

        if(result != null) {
            for (BodyPart bodyPart : result) {
                Log.i(TAG, "BodyPart: " + bodyPart.getBodySection());
            }
        }
    }

}
