package com.hevs.gym.fitnessapp.db.endpointAsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.matthias.myapplication.backend.planExerciseApi.PlanExerciseApi;
import com.example.matthias.myapplication.backend.planExerciseApi.model.PlanExercise;
import com.example.matthias.myapplication.backend.userApi.model.User;
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

public class PlanExerciseEndpointsAsyncTask extends AsyncTask<Void, Void, List<PlanExercise>> {
    private static PlanExerciseApi planExerciseApi = null;
    private static final String TAG = PlanExerciseEndpointsAsyncTask.class.getName();
    private PlanExercise planExercise;
    private long idPlanExercise = -1;
    public PlanExerciseEndpointsAsyncTask(){}

    public PlanExerciseEndpointsAsyncTask(PlanExercise planExercise){
        this.planExercise = planExercise;
    }

    public PlanExerciseEndpointsAsyncTask(long idUpdate, PlanExercise planExercise){
        this.planExercise = planExercise;
        idPlanExercise = idUpdate;
    }

    public PlanExerciseEndpointsAsyncTask(long idDelete){
        idPlanExercise = idDelete;
    }

    @Override
    protected List<PlanExercise> doInBackground(Void... params) {
        if(planExerciseApi == null){
            // Only do this once
            PlanExerciseApi.Builder builder = new PlanExerciseApi.Builder(AndroidHttp.newCompatibleTransport(),
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
            planExerciseApi = builder.build();
        }

        try{
            // Call here the wished methods on the Endpoints
            // For instance insert
            if(planExercise != null && idPlanExercise == -1){
                planExerciseApi.insert(planExercise).execute();
                Log.i(TAG, "insert planExercise");
            }

            if(planExercise == null && idPlanExercise != -1){
                planExerciseApi.remove(idPlanExercise).execute();
                Log.i(TAG, "delete planExercise");
            }

            if(planExercise != null && idPlanExercise != -1){
                planExerciseApi.update(idPlanExercise, planExercise).execute();
                Log.i(TAG, "update planExercise");
            }
            // and for instance return the list of all employees
            return planExerciseApi.list().execute().getItems();

        } catch (IOException e){
            Log.e(TAG, e.toString());
            return new ArrayList<PlanExercise>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<PlanExercise> result){

        if(result != null) {
            for (PlanExercise planExercise : result) {
                Log.i(TAG, "Plan id" + planExercise.getPlanID() + " Exid: "
                        + planExercise.getExerciseID());
            }
        }
    }

}
