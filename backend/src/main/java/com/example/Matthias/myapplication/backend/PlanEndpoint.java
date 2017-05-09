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
        name = "planApi",
        version = "v1",
        resource = "plan",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Matthias.example.com",
                ownerName = "backend.myapplication.Matthias.example.com",
                packagePath = ""
        )
)
public class PlanEndpoint {

    private static final Logger logger = Logger.getLogger(PlanEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Plan.class);
    }

    /**
     * Returns the {@link Plan} with the corresponding ID.
     *
     * @param planID the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Plan} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "plan/{planID}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Plan get(@Named("planID") long planID) throws NotFoundException {
        logger.info("Getting Plan with ID: " + planID);
        Plan plan = ofy().load().type(Plan.class).id(planID).now();
        if (plan == null) {
            throw new NotFoundException("Could not find Plan with ID: " + planID);
        }
        return plan;
    }

    /**
     * Inserts a new {@code Plan}.
     */
    @ApiMethod(
            name = "insert",
            path = "plan",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Plan insert(Plan plan) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that plan.planID has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(plan).now();
        logger.info("Created Plan with ID: " + plan.getPlanID());

        return ofy().load().entity(plan).now();
    }

    /**
     * Updates an existing {@code Plan}.
     *
     * @param planID the ID of the entity to be updated
     * @param plan   the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code planID} does not correspond to an existing
     *                           {@code Plan}
     */
    @ApiMethod(
            name = "update",
            path = "plan/{planID}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Plan update(@Named("planID") long planID, Plan plan) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(planID);
        ofy().save().entity(plan).now();
        logger.info("Updated Plan: " + plan);
        return ofy().load().entity(plan).now();
    }

    /**
     * Deletes the specified {@code Plan}.
     *
     * @param planID the ID of the entity to delete
     * @throws NotFoundException if the {@code planID} does not correspond to an existing
     *                           {@code Plan}
     */
    @ApiMethod(
            name = "remove",
            path = "plan/{planID}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("planID") long planID) throws NotFoundException {
        checkExists(planID);
        ofy().delete().type(Plan.class).id(planID).now();
        logger.info("Deleted Plan with ID: " + planID);
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
            path = "plan",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Plan> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Plan> query = ofy().load().type(Plan.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Plan> queryIterator = query.iterator();
        List<Plan> planList = new ArrayList<Plan>(limit);
        while (queryIterator.hasNext()) {
            planList.add(queryIterator.next());
        }
        return CollectionResponse.<Plan>builder().setItems(planList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long planID) throws NotFoundException {
        try {
            ofy().load().type(Plan.class).id(planID).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Plan with ID: " + planID);
        }
    }
}