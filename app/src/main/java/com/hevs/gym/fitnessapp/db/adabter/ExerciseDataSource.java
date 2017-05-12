package com.hevs.gym.fitnessapp.db.adabter;

import android.content.Context;

import com.example.matthias.myapplication.backend.exerciseApi.model.Exercise;
import com.example.matthias.myapplication.backend.planExerciseApi.model.PlanExercise;
import com.hevs.gym.fitnessapp.db.endpointAsyncTasks.ExerciseEndpointsAsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class ExerciseDataSource {
    private Context context;

    /**
     * Constructor exercises
     *
     */
    public ExerciseDataSource(Context context) {
        this.context = context;
    }

    /**
     *
     *  create a exercise and gives the id back
     */
    public long createExercise(Exercise exercise) {
        long id;

        List<Exercise> exerciseList = getAllExercises();
        if (exerciseList.size() != 0) {
            exercise.setExerciseID(exerciseList.get(exerciseList.size() - 1).getExerciseID() + 1);
        }else
        {
            exercise.setExerciseID(1l);
        }
        new ExerciseEndpointsAsyncTask(exercise).execute();

        return exercise.getExerciseID();
    }

    /**
     * get one exercise by id
     *
     */
    public Exercise getExerciseById(long id) {
        List<Exercise> exercises = getAllExercises();
        for (Exercise e:exercises) {
            if (e.getExerciseID() == id)
                return e;
        }
        return null;
    }

    /**
     * get all exercises from a plan
     *
     */
    public List<Exercise> getExerciseByPlanID(long id) {

        PlanExerciseDataSource planExerciseDataSource = new PlanExerciseDataSource(context);
        List<PlanExercise> planExercises = planExerciseDataSource.getAllPlanExerciseByPlanID(id);

        List<Exercise> exercises = new ArrayList<Exercise>();

        for (PlanExercise pe:planExercises) {
            exercises.add(getExerciseById(pe.getExerciseID()));
        }

        return exercises;
    }

    /**
     *  gett al exercises by a planid and body part id
     *
     */
    public List<Exercise> getExerciseByPlanIDAndBodyPartID(long id, long bodyPartID) {

        PlanExerciseDataSource planExerciseDataSource = new PlanExerciseDataSource(context);
        List<PlanExercise> planExercises = planExerciseDataSource.getAllPlanExerciseByPlanID(id);

        List<Exercise> exercises = new ArrayList<Exercise>();

        for (PlanExercise pe:planExercises) {
            Exercise ex = getExerciseById(pe.getExerciseID());
            if (ex.getBodyPart() == bodyPartID) {
                exercises.add(ex);
            }
        }

        return exercises;
    }



    /**
     * get all exercises
     *
     */
    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<>();
        try {
            exercises = new ExerciseEndpointsAsyncTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (exercises == null)
            return new ArrayList<Exercise>();
        return exercises;
    }

    /**
     * get all Exercises from one body part id
     *
     */
    public List<Exercise> getAllExercisesFromBodyPartID(long BodyPartID) {
        List<Exercise> exercises = getAllExercises();
        List<Exercise> dEx = new ArrayList<>();
        for (Exercise ex : exercises)
        {
            if (ex.getBodyPart() == BodyPartID)
                dEx.add(ex);
        }
        return dEx;
    }

    /**
     *  update a exercises
     *
     */
    public void updateExercises(Exercise exercise) {
        new ExerciseEndpointsAsyncTask(exercise.getExerciseID(), exercise).execute();
    }

    /**
     * delete a exercises
     *
     */
    public void deleteExercise(long id) {
        PlanExerciseDataSource plds= new PlanExerciseDataSource(context);

        List<PlanExercise> planExercises = plds.getAllPlanExercise();

        for(PlanExercise planExercise : planExercises){
            if (planExercise.getExerciseID() == id)
            plds.deletePlanExercise(planExercise.getPlanExerciseID());
        }


        new ExerciseEndpointsAsyncTask(id).execute();

    }
}

