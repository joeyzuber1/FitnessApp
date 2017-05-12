package com.hevs.gym.fitnessapp.db.endpointAsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.matthias.myapplication.backend.planApi.PlanApi;
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

public class PlanEndpointsAsyncTask extends AsyncTask<Void, Void, List<Plan>> {
    private static PlanApi planApi = null;
    private static final String TAG = PlanEndpointsAsyncTask.class.getName();
    private Plan plan;
    private long idPlan = -1;
    public PlanEndpointsAsyncTask(){}

    public PlanEndpointsAsyncTask(Plan plan){
        this.plan = plan;
    }


    public PlanEndpointsAsyncTask(long idUpdate, Plan plan){
        this.plan = plan;
        idPlan = idUpdate;
    }

    public PlanEndpointsAsyncTask(long idDelete){
        idPlan = idDelete;
    }

    @Override
    protected List<Plan> doInBackground(Void... params) {
        if(planApi == null){
            // Only do this once
            PlanApi.Builder builder = new PlanApi.Builder(AndroidHttp.newCompatibleTransport(),
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
            planApi = builder.build();
        }

        try{
            // Call here the wished methods on the Endpoints
            // For instance insert
            if(plan != null && idPlan == -1){
                planApi.insert(plan).execute();
                Log.i(TAG, "insert plan");
            }

            if(plan == null && idPlan != -1){
                planApi.remove(idPlan).execute();
                Log.i(TAG, "delete plan");
            }

            if(plan != null && idPlan != -1){
                planApi.update(idPlan, plan).execute();
                Log.i(TAG, "update plan");
            }
            // and for instance return the list of all employees
            return planApi.list().execute().getItems();

        } catch (IOException e){
            Log.e(TAG, e.toString());
            return new ArrayList<Plan>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<Plan> result){

        if(result != null) {
            for (Plan plan : result) {
                Log.i(TAG, "Planname: " + plan.getPlanName());
            }
        }
    }

}
