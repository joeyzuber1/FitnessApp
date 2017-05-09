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
        name = "exerciseApi",
        version = "v1",
        resource = "exercise",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Matthias.example.com",
                ownerName = "backend.myapplication.Matthias.example.com",
                packagePath = ""
        )
)
public class ExerciseEndpoint {

    private static final Logger logger = Logger.getLogger(ExerciseEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Exercise.class);
    }

    /**
     * Returns the {@link Exercise} with the corresponding ID.
     *
     * @param exerciseID the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Exercise} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "exercise/{exerciseID}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Exercise get(@Named("exerciseID") long exerciseID) throws NotFoundException {
        logger.info("Getting Exercise with ID: " + exerciseID);
        Exercise exercise = ofy().load().type(Exercise.class).id(exerciseID).now();
        if (exercise == null) {
            throw new NotFoundException("Could not find Exercise with ID: " + exerciseID);
        }
        return exercise;
    }

    /**
     * Inserts a new {@code Exercise}.
     */
    @ApiMethod(
            name = "insert",
            path = "exercise",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Exercise insert(Exercise exercise) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that exercise.exerciseID has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(exercise).now();
        logger.info("Created Exercise with ID: " + exercise.getExerciseID());

        return ofy().load().entity(exercise).now();
    }

    /**
     * Updates an existing {@code Exercise}.
     *
     * @param exerciseID the ID of the entity to be updated
     * @param exercise   the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code exerciseID} does not correspond to an existing
     *                           {@code Exercise}
     */
    @ApiMethod(
            name = "update",
            path = "exercise/{exerciseID}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Exercise update(@Named("exerciseID") long exerciseID, Exercise exercise) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(exerciseID);
        ofy().save().entity(exercise).now();
        logger.info("Updated Exercise: " + exercise);
        return ofy().load().entity(exercise).now();
    }

    /**
     * Deletes the specified {@code Exercise}.
     *
     * @param exerciseID the ID of the entity to delete
     * @throws NotFoundException if the {@code exerciseID} does not correspond to an existing
     *                           {@code Exercise}
     */
    @ApiMethod(
            name = "remove",
            path = "exercise/{exerciseID}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("exerciseID") long exerciseID) throws NotFoundException {
        checkExists(exerciseID);
        ofy().delete().type(Exercise.class).id(exerciseID).now();
        logger.info("Deleted Exercise with ID: " + exerciseID);
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
            path = "exercise",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Exercise> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Exercise> query = ofy().load().type(Exercise.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Exercise> queryIterator = query.iterator();
        List<Exercise> exerciseList = new ArrayList<Exercise>(limit);
        while (queryIterator.hasNext()) {
            exerciseList.add(queryIterator.next());
        }
        return CollectionResponse.<Exercise>builder().setItems(exerciseList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long exerciseID) throws NotFoundException {
        try {
            ofy().load().type(Exercise.class).id(exerciseID).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Exercise with ID: " + exerciseID);
        }
    }
}