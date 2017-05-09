package com.example.Matthias.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "planExerciseApi",
        version = "v1",
        resource = "planExercise",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Matthias.example.com",
                ownerName = "backend.myapplication.Matthias.example.com",
                packagePath = ""
        )
)
public class PlanExerciseEndpoint {

    private static final Logger logger = Logger.getLogger(PlanExerciseEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(PlanExercise.class);
    }

    /**
     * Returns the {@link PlanExercise} with the corresponding ID.
     *
     * @param planExerciseID the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code PlanExercise} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "planExercise/{planExerciseID}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public PlanExercise get(@Named("planExerciseID") long planExerciseID) throws NotFoundException {
        logger.info("Getting PlanExercise with ID: " + planExerciseID);
        PlanExercise planExercise = ofy().load().type(PlanExercise.class).id(planExerciseID).now();
        if (planExercise == null) {
            throw new NotFoundException("Could not find PlanExercise with ID: " + planExerciseID);
        }
        return planExercise;
    }

    /**
     * Inserts a new {@code PlanExercise}.
     */
    @ApiMethod(
            name = "insert",
            path = "planExercise",
            httpMethod = ApiMethod.HttpMethod.POST)
    public PlanExercise insert(PlanExercise planExercise) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that planExercise.planExerciseID has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(planExercise).now();
        logger.info("Created PlanExercise with ID: " + planExercise.getPlanExerciseID());

        return ofy().load().entity(planExercise).now();
    }

    /**
     * Updates an existing {@code PlanExercise}.
     *
     * @param planExerciseID the ID of the entity to be updated
     * @param planExercise   the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code planExerciseID} does not correspond to an existing
     *                           {@code PlanExercise}
     */
    @ApiMethod(
            name = "update",
            path = "planExercise/{planExerciseID}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public PlanExercise update(@Named("planExerciseID") long planExerciseID, PlanExercise planExercise) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(planExerciseID);
        ofy().save().entity(planExercise).now();
        logger.info("Updated PlanExercise: " + planExercise);
        return ofy().load().entity(planExercise).now();
    }

    /**
     * Deletes the specified {@code PlanExercise}.
     *
     * @param planExerciseID the ID of the entity to delete
     * @throws NotFoundException if the {@code planExerciseID} does not correspond to an existing
     *                           {@code PlanExercise}
     */
    @ApiMethod(
            name = "remove",
            path = "planExercise/{planExerciseID}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("planExerciseID") long planExerciseID) throws NotFoundException {
        checkExists(planExerciseID);
        ofy().delete().type(PlanExercise.class).id(planExerciseID).now();
        logger.info("Deleted PlanExercise with ID: " + planExerciseID);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "planExercise",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<PlanExercise> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<PlanExercise> query = ofy().load().type(PlanExercise.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<PlanExercise> queryIterator = query.iterator();
        List<PlanExercise> planExerciseList = new ArrayList<PlanExercise>(limit);
        while (queryIterator.hasNext()) {
            planExerciseList.add(queryIterator.next());
        }
        return CollectionResponse.<PlanExercise>builder().setItems(planExerciseList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long planExerciseID) throws NotFoundException {
        try {
            ofy().load().type(PlanExercise.class).id(planExerciseID).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find PlanExercise with ID: " + planExerciseID);
        }
    }
}