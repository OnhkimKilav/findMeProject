package com.findme.service.userService;

import com.findme.RelationshipStatus;
import com.findme.check.checkUser;
import com.findme.dao.userDAO.IUserDAORelationship;
import com.findme.exception.BadRequestException;
import com.findme.model.User;
import com.findme.service.userService.CheckDeleteRelationship.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;

/**
 * A class is a functional description for the relationship between two or one users and the implementation of business
 * logic for them.
 */

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserServiceRelationshipImpl implements IUserServiceRelationship {
    private IUserDAORelationship userDAORelationship;

    @Autowired
    public UserServiceRelationshipImpl(IUserDAORelationship userDAORelationship) {
        this.userDAORelationship = userDAORelationship;
    }

    /**
     * This method contains business logic for adding relationship between two users.
     *
     * @param session - user who at now logged in at the session
     * @param userIdFrom - user who sender
     * @param userIdTo - user who recipients
     */

    @Override
    public void addRelationship(HttpSession session, String userIdFrom, String userIdTo) {
        String status = userDAORelationship.getStatusRelationship(userIdFrom, userIdTo);

        checkUser.userIsYourself(userIdFrom, userIdTo);
        checkUser.userIsSessionUser(session, userIdFrom);

        /**
         * Firstly, we need to check that our status is not null, if it is null, we create relationships.
         * Secondly, we check that the status has the value "NOT_FRIENDS", in this case we assign the value "FRIENDS"
         * to the state that already exists in the database. Otherwise return an exception.
         */

        if (status == null)
            userDAORelationship.addRelationship(userIdFrom, userIdTo);
        else if (status.equals(String.valueOf(RelationshipStatus.NOT_FRIENDS))) {
            userDAORelationship.updateRelationship(userIdFrom, userIdTo, String.valueOf(RelationshipStatus.REQUEST_SENDED));
            return;
        } else throw new BadRequestException("Request to this user has already been sent");

    }

    /**
     * This method contains business logic for updating the state of a relationship between two users.
     * If all checks are passed, then all values are transferred to DAO.
     *
     * @param session - user who at now logged in at the session
     * @param userIdFrom - user who sender
     * @param userIdTo - user who recipients
     * @param status - status to which the status will be updated
     */

    @Override
    public void updateRelationship(HttpSession session, String userIdFrom, String userIdTo, String status) {
        String statusRelationship = userDAORelationship.getStatusRelationship(userIdFrom, userIdTo);

        checkUser.bothUserIsSessionUser(session, userIdFrom, userIdTo);
        checkUser.nullStatusRelationship(statusRelationship);

        /**
         * This check need for request processing from sender to recipient for update relationship.
         * In the first checking we check so that user will be sender and not will be recipient.
         * And in the second check we check status relationship, it should be equal status " REQUEST_SENDED "
         */

        if ((String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom))
                && !(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdTo))) {
            if (!(statusRelationship.equals(String.valueOf(RelationshipStatus.REQUEST_SENDED))))
                throw new BadRequestException("You cannot update this relationship. You did not receive a request from " +
                        "this user or request already accepted");
        }

        /**
         * This check need for request processing from recipient  to sender for update relationship.
         * In the first checking we check so that user will be recipient  and not will be sender.
         * And in the second check we check status relationship, it should be not equal status " NOT_FRIENDS "
         */

        else if (!(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom))
                && (String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdTo))) {
            if (((statusRelationship.equals(String.valueOf(RelationshipStatus.NOT_FRIENDS)))))
                throw new BadRequestException("You cannot update this relationship. You did not receive a request from this user");
        }

        userDAORelationship.updateRelationship(userIdFrom, userIdTo, status);
    }

    @Autowired
    @Qualifier("relationshipTime")
    private IDeleteRelationship relationshipTime;

    @Autowired
    @Qualifier("maxCountFriends")
    private IDeleteRelationship maxCountFriends;

    @Autowired
    @Qualifier("maxCountOutgoingRequests")
    private IDeleteRelationship maxCountOutgoingRequests;

    @Autowired
    @Qualifier("othersChecks")
    private IDeleteRelationship othersChecks;

    /**
     * Method implements a relationship deleting.
     * It's using  patterns chain of responsibility.
     *
     * @param userIdFrom The variable means id user who sended request
     * @param userIdTo   The variable means if user who recipiented request
     */

    @Override
    public void deleteRelationship(HttpSession session, String userIdFrom, String userIdTo) {
        othersChecks.setNext(relationshipTime).setNext(maxCountFriends).setNext(maxCountOutgoingRequests);
        othersChecks.delete(session, userIdFrom, userIdTo);

        userDAORelationship.deleteRelationship(userIdFrom, userIdTo);
    }


}
