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
        name = "bodyPartApi",
        version = "v1",
        resource = "bodyPart",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Matthias.example.com",
                ownerName = "backend.myapplication.Matthias.example.com",
                packagePath = ""
        )
)
public class BodyPartEndpoint {

    private static final Logger logger = Logger.getLogger(BodyPartEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(BodyPart.class);
    }

    /**
     * Returns the {@link BodyPart} with the corresponding ID.
     *
     * @param partOfBodyID the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code BodyPart} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "bodyPart/{partOfBodyID}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public BodyPart get(@Named("partOfBodyID") long partOfBodyID) throws NotFoundException {
        logger.info("Getting BodyPart with ID: " + partOfBodyID);
        BodyPart bodyPart = ofy().load().type(BodyPart.class).id(partOfBodyID).now();
        if (bodyPart == null) {
            throw new NotFoundException("Could not find BodyPart with ID: " + partOfBodyID);
        }
        return bodyPart;
    }

    /**
     * Inserts a new {@code BodyPart}.
     */
    @ApiMethod(
            name = "insert",
            path = "bodyPart",
            httpMethod = ApiMethod.HttpMethod.POST)
    public BodyPart insert(BodyPart bodyPart) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that bodyPart.partOfBodyID has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(bodyPart).now();
        logger.info("Created BodyPart with ID: " + bodyPart.getPartOfBodyID());

        return ofy().load().entity(bodyPart).now();
    }

    /**
     * Updates an existing {@code BodyPart}.
     *
     * @param partOfBodyID the ID of the entity to be updated
     * @param bodyPart     the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code partOfBodyID} does not correspond to an existing
     *                           {@code BodyPart}
     */
    @ApiMethod(
            name = "update",
            path = "bodyPart/{partOfBodyID}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public BodyPart update(@Named("partOfBodyID") long partOfBodyID, BodyPart bodyPart) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(partOfBodyID);
        ofy().save().entity(bodyPart).now();
        logger.info("Updated BodyPart: " + bodyPart);
        return ofy().load().entity(bodyPart).now();
    }

    /**
     * Deletes the specified {@code BodyPart}.
     *
     * @param partOfBodyID the ID of the entity to delete
     * @throws NotFoundException if the {@code partOfBodyID} does not correspond to an existing
     *                           {@code BodyPart}
     */
    @ApiMethod(
            name = "remove",
            path = "bodyPart/{partOfBodyID}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("partOfBodyID") long partOfBodyID) throws NotFoundException {
        checkExists(partOfBodyID);
        ofy().delete().type(BodyPart.class).id(partOfBodyID).now();
        logger.info("Deleted BodyPart with ID: " + partOfBodyID);
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
            path = "bodyPart",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<BodyPart> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<BodyPart> query = ofy().load().type(BodyPart.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<BodyPart> queryIterator = query.iterator();
        List<BodyPart> bodyPartList = new ArrayList<BodyPart>(limit);
        while (queryIterator.hasNext()) {
            bodyPartList.add(queryIterator.next());
        }
        return CollectionResponse.<BodyPart>builder().setItems(bodyPartList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long partOfBodyID) throws NotFoundException {
        try {
            ofy().load().type(BodyPart.class).id(partOfBodyID).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find BodyPart with ID: " + partOfBodyID);
        }
    }
}