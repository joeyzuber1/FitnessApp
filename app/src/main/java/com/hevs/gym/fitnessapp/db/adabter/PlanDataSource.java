package com.hevs.gym.fitnessapp.db.adabter;

import android.content.Context;

import com.example.matthias.myapplication.backend.planApi.model.Plan;
import com.example.matthias.myapplication.backend.planExerciseApi.model.PlanExercise;
import com.hevs.gym.fitnessapp.db.endpointAsyncTasks.PlanEndpointsAsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Matthias and Joey on 10.04.2017.
 */

public class PlanDataSource {
    private Context context;

    /**
     * Constructor of Plan source
     *
     */
    public PlanDataSource(Context context) {
        this.context = context;
    }

    /**
     * Create a new plan and gives the id back
     *
     */
    public long createPlan(Plan plan) {
        long id;

        List<Plan> planList = getAllPlan();
        if (planList.size() != 0) {
            plan.setPlanID(planList.get(planList.size() - 1).getPlanID() + 1);
        }else
        {
            plan.setPlanID(1l);
        }
        new PlanEndpointsAsyncTask(plan).execute();

        return plan.getPlanID();
    }

    /**
     * get a plan by id
     *
     */
    public Plan getPlanById(long id) {
        List<Plan> planListn = getAllPlan();
        for (Plan p:planListn) {
            if (p.getPlanID() == id)
                return p;
        }
        return null;
    }

    /**
     * get all plans
     *
     */
    public List<Plan> getAllPlan() {
        List<Plan> planList = new ArrayList<>();
        try {
            planList = new PlanEndpointsAsyncTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (planList == null)
            return new ArrayList<Plan>();
        return planList;
    }

    /**
     * get plans from user id
     *
     */
    public List<Plan> getPlanFromUserID(long idUser) {
        List<Plan> planList = getAllPlan();
        List<Plan> dPlanList1 = new ArrayList<>();
        for (Plan plan : planList)
        {
            if (plan.getUserID() == idUser)
                dPlanList1.add(plan);
        }
        return dPlanList1;
    }

    /**
     *
     * update a planName
     */
    public void updatePlanName(Plan plan) {
        new PlanEndpointsAsyncTask(plan.getPlanID(), plan).execute();
    }

    /**
     * delete a plan
     *
     */
    public void deletePlan(long id) {
        PlanExerciseDataSource plds= new PlanExerciseDataSource(context);

        List<PlanExercise> planExercises = plds.getAllPlanExercise();

        for(PlanExercise planExercise : planExercises){
            if (planExercise.getPlanID() == id)
            plds.deletePlanExercise(planExercise.getPlanExerciseID());
        }

       new PlanEndpointsAsyncTask(id).execute();

    }
}
