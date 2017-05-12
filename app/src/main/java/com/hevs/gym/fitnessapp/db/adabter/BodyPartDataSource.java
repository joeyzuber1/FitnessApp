package com.hevs.gym.fitnessapp.db.adabter;

import android.content.Context;

import com.example.matthias.myapplication.backend.bodyPartApi.model.BodyPart;
import com.example.matthias.myapplication.backend.exerciseApi.model.Exercise;
import com.hevs.gym.fitnessapp.db.endpointAsyncTasks.BodyPartEndpointsAsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class BodyPartDataSource {
    private Context context;

    /**
     *  Cunstroctor for Body Part Data Source
     *
     */
    public BodyPartDataSource(Context context) {
        this.context = context;
    }

    /**
     *
     *  Insert one Body part and give the id back
     */
    public long createBodyPart(BodyPart bodypart) {
        long id;
        List<BodyPart> bodyPartList = getAllBodyParts();
        if (bodyPartList.size() != 0) {
            bodypart.setPartOfBodyID(bodyPartList.get(bodyPartList.size() - 1).getPartOfBodyID() + 1);
        }else
        {
            bodypart.setPartOfBodyID(1l);
        }
        new BodyPartEndpointsAsyncTask(bodypart).execute();
        return bodypart.getPartOfBodyID();
    }

    /**
     *  get one Body part by id
     *
     */
    public BodyPart getBodyPartById(long id) {
        List<BodyPart> bodyPartList = getAllBodyParts();
        for (BodyPart bp:bodyPartList) {
            if (bp.getPartOfBodyID() == id)
                return bp;
        }
        return null;
    }

    /**
     *  get all body part
     *
     */
    public List<BodyPart> getAllBodyParts() {
        List<BodyPart> bodyParts = new ArrayList<>();
        try {
            bodyParts = new BodyPartEndpointsAsyncTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (bodyParts == null)
            return new ArrayList<BodyPart>();
        return bodyParts;
    }

    /**
     *  get all body parts from a plan
     *
     */
    public List<BodyPart> getAllBodyPartsByPlanID(long id) {
        List<BodyPart> bodyParts = getAllBodyParts();
        List<BodyPart> bodyParts1 = new ArrayList<>();
        ExerciseDataSource exerciseDataSource = new ExerciseDataSource(context);
        List<Exercise> exercises = exerciseDataSource.getExerciseByPlanID(id);
        for (BodyPart bodyPart : bodyParts) {
            for (Exercise ex : exercises) {
                if (ex.getBodyPart() == bodyPart.getPartOfBodyID()) {
                    bodyParts1.add(bodyPart);
                    break;
                }
            }
        }
        return bodyParts1;
    }

    /**
     * update a body part
     *
     */
    public void updateBodyPart(BodyPart bodyPart) {
        new BodyPartEndpointsAsyncTask(bodyPart.getPartOfBodyID(), bodyPart).execute();
    }

    /**
     * delete one body part
     *
     */
    public boolean deleteBodyPart(long id) {
        //Exercises müssen Angepasst werden oder geprüft werden ob vorhanden wenn ja nicht möglich
        ExerciseDataSource exsds = new ExerciseDataSource(context);
        List<Exercise> exs = exsds.getAllExercises();

        for (Exercise ex : exs) {
            if (ex.getBodyPart() == id)
                return false;
        }

       new BodyPartEndpointsAsyncTask(id).execute();
        return true;

    }
}
