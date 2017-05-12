package com.hevs.gym.fitnessapp.db.adabter;

import android.content.Context;

import com.example.matthias.myapplication.backend.planExerciseApi.model.PlanExercise;
import com.hevs.gym.fitnessapp.db.endpointAsyncTasks.PlanExerciseEndpointsAsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class PlanExerciseDataSource {
    private Context context;

    /**
     * Constructor of PlanEx
     *
     */
    public PlanExerciseDataSource(Context context) {
        this.context = context;
    }

    /**
     * create a new PlanEx and gives the id back
     *
     */
    public long createPlanExercise(PlanExercise planExercise) {
        long id;
        List<PlanExercise> planList = getAllPlanExercise();
        if (planList.size() != 0) {
            planExercise.setPlanExerciseID(planList.get(planList.size() - 1).getPlanExerciseID() + 1);
        }else
        {
            planExercise.setPlanExerciseID(1l);
        }
        new PlanExerciseEndpointsAsyncTask(planExercise).execute();

        return planExercise.getExerciseID();
    }

    /**
     * get all Plan exs
     *
     */
    public List<PlanExercise> getAllPlanExercise() {
        List<PlanExercise> planExerciseList = new ArrayList<>();
        try {
            planExerciseList = new PlanExerciseEndpointsAsyncTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (planExerciseList == null)
            return new ArrayList<PlanExercise>();
        return planExerciseList;
    }

    /**
     * give all plan ex by planid
     *
     */
    public List<PlanExercise> getAllPlanExerciseByPlanID(long id) {
        List<PlanExercise> planExerciseList = getAllPlanExercise();
        List<PlanExercise> dPlanExerciseList1 = new ArrayList<>();
        for (PlanExercise planExercise : planExerciseList)
        {
            if (planExercise.getPlanID() == id)
                dPlanExerciseList1.add(planExercise);
        }
        return dPlanExerciseList1;
    }

    /**
     * delete a planEx
     *
     */
    public void deletePlanExercise(long id) {
        new PlanExerciseEndpointsAsyncTask(id).execute();
    }
}

