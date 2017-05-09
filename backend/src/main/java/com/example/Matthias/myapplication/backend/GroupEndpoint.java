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
        name = "groupApi",
        version = "v1",
        resource = "group",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Matthias.example.com",
                ownerName = "backend.myapplication.Matthias.example.com",
                packagePath = ""
        )
)
public class GroupEndpoint {

    private static final Logger logger = Logger.getLogger(GroupEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Group.class);
    }

    /**
     * Returns the {@link Group} with the corresponding ID.
     *
     * @param groupID the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Group} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "group/{groupID}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Group get(@Named("groupID") long groupID) throws NotFoundException {
        logger.info("Getting Group with ID: " + groupID);
        Group group = ofy().load().type(Group.class).id(groupID).now();
        if (group == null) {
            throw new NotFoundException("Could not find Group with ID: " + groupID);
        }
        return group;
    }

    /**
     * Inserts a new {@code Group}.
     */
    @ApiMethod(
            name = "insert",
            path = "group",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Group insert(Group group) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that group.groupID has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(group).now();
        logger.info("Created Group with ID: " + group.getGroupID());

        return ofy().load().entity(group).now();
    }

    /**
     * Updates an existing {@code Group}.
     *
     * @param groupID the ID of the entity to be updated
     * @param group   the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code groupID} does not correspond to an existing
     *                           {@code Group}
     */
    @ApiMethod(
            name = "update",
            path = "group/{groupID}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Group update(@Named("groupID") long groupID, Group group) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(groupID);
        ofy().save().entity(group).now();
        logger.info("Updated Group: " + group);
        return ofy().load().entity(group).now();
    }

    /**
     * Deletes the specified {@code Group}.
     *
     * @param groupID the ID of the entity to delete
     * @throws NotFoundException if the {@code groupID} does not correspond to an existing
     *                           {@code Group}
     */
    @ApiMethod(
            name = "remove",
            path = "group/{groupID}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("groupID") long groupID) throws NotFoundException {
        checkExists(groupID);
        ofy().delete().type(Group.class).id(groupID).now();
        logger.info("Deleted Group with ID: " + groupID);
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
            path = "group",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Group> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Group> query = ofy().load().type(Group.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Group> queryIterator = query.iterator();
        List<Group> groupList = new ArrayList<Group>(limit);
        while (queryIterator.hasNext()) {
            groupList.add(queryIterator.next());
        }
        return CollectionResponse.<Group>builder().setItems(groupList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long groupID) throws NotFoundException {
        try {
            ofy().load().type(Group.class).id(groupID).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Group with ID: " + groupID);
        }
    }
}