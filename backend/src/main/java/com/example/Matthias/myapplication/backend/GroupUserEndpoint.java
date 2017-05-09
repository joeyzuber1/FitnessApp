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
        name = "groupUserApi",
        version = "v1",
        resource = "groupUser",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Matthias.example.com",
                ownerName = "backend.myapplication.Matthias.example.com",
                packagePath = ""
        )
)
public class GroupUserEndpoint {

    private static final Logger logger = Logger.getLogger(GroupUserEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(GroupUser.class);
    }

    /**
     * Returns the {@link GroupUser} with the corresponding ID.
     *
     * @param groupUserID the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code GroupUser} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "groupUser/{groupUserID}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public GroupUser get(@Named("groupUserID") long groupUserID) throws NotFoundException {
        logger.info("Getting GroupUser with ID: " + groupUserID);
        GroupUser groupUser = ofy().load().type(GroupUser.class).id(groupUserID).now();
        if (groupUser == null) {
            throw new NotFoundException("Could not find GroupUser with ID: " + groupUserID);
        }
        return groupUser;
    }

    /**
     * Inserts a new {@code GroupUser}.
     */
    @ApiMethod(
            name = "insert",
            path = "groupUser",
            httpMethod = ApiMethod.HttpMethod.POST)
    public GroupUser insert(GroupUser groupUser) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that groupUser.groupUserID has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(groupUser).now();
        logger.info("Created GroupUser with ID: " + groupUser.getGroupUserID());

        return ofy().load().entity(groupUser).now();
    }

    /**
     * Updates an existing {@code GroupUser}.
     *
     * @param groupUserID the ID of the entity to be updated
     * @param groupUser   the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code groupUserID} does not correspond to an existing
     *                           {@code GroupUser}
     */
    @ApiMethod(
            name = "update",
            path = "groupUser/{groupUserID}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public GroupUser update(@Named("groupUserID") long groupUserID, GroupUser groupUser) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(groupUserID);
        ofy().save().entity(groupUser).now();
        logger.info("Updated GroupUser: " + groupUser);
        return ofy().load().entity(groupUser).now();
    }

    /**
     * Deletes the specified {@code GroupUser}.
     *
     * @param groupUserID the ID of the entity to delete
     * @throws NotFoundException if the {@code groupUserID} does not correspond to an existing
     *                           {@code GroupUser}
     */
    @ApiMethod(
            name = "remove",
            path = "groupUser/{groupUserID}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("groupUserID") long groupUserID) throws NotFoundException {
        checkExists(groupUserID);
        ofy().delete().type(GroupUser.class).id(groupUserID).now();
        logger.info("Deleted GroupUser with ID: " + groupUserID);
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
            path = "groupUser",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<GroupUser> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<GroupUser> query = ofy().load().type(GroupUser.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<GroupUser> queryIterator = query.iterator();
        List<GroupUser> groupUserList = new ArrayList<GroupUser>(limit);
        while (queryIterator.hasNext()) {
            groupUserList.add(queryIterator.next());
        }
        return CollectionResponse.<GroupUser>builder().setItems(groupUserList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long groupUserID) throws NotFoundException {
        try {
            ofy().load().type(GroupUser.class).id(groupUserID).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find GroupUser with ID: " + groupUserID);
        }
    }
}