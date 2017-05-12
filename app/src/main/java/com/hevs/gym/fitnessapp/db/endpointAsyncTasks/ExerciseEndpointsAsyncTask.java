
package com.hevs.gym.fitnessapp.db.endpointAsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.matthias.myapplication.backend.exerciseApi.ExerciseApi;
import com.example.matthias.myapplication.backend.exerciseApi.model.Exercise;
import com.example.matthias.myapplication.backend.planExerciseApi.model.PlanExercise;
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

public class ExerciseEndpointsAsyncTask extends AsyncTask<Void, Void, List<Exercise>> {
    private static ExerciseApi exerciseApi = null;
    private static final String TAG = ExerciseEndpointsAsyncTask.class.getName();
    private Exercise exercise;
    private long idExercise = -1;

    public ExerciseEndpointsAsyncTask(){}

    public ExerciseEndpointsAsyncTask(Exercise exercise){
        this.exercise = exercise;
    }

    public ExerciseEndpointsAsyncTask(long idUpdate, Exercise exercise){
        this.exercise = exercise;
        idExercise = idUpdate;
    }

    public ExerciseEndpointsAsyncTask(long idDelete){
        idExercise = idDelete;
    }

    @Override
    protected List<Exercise> doInBackground(Void... params) {
        if(exerciseApi == null){
            // Only do this once
            ExerciseApi.Builder builder = new ExerciseApi.Builder(AndroidHttp.newCompatibleTransport(),
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
            exerciseApi = builder.build();
        }

        try{
            // Call here the wished methods on the Endpoints
            // For instance insert
            if(exercise != null && idExercise == -1){
                exerciseApi.insert(exercise).execute();
                Log.i(TAG, "insert exercise");
            }

            if(exercise == null && idExercise != -1){
                exerciseApi.remove(idExercise).execute();
                Log.i(TAG, "delete exercise");
            }

            if(exercise != null && idExercise != -1){
                exerciseApi.update(idExercise, exercise).execute();
                Log.i(TAG, "update exercise");
            }
            // and for instance return the list of all employees
            return exerciseApi.list().execute().getItems();

        } catch (IOException e){
            Log.e(TAG, e.toString());
            return new ArrayList<Exercise>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<Exercise> result){

        if(result != null) {
            for (Exercise exercise : result) {
                Log.i(TAG, "Exercisename" + exercise.getExerciseName());
            }
        }
    }

}
