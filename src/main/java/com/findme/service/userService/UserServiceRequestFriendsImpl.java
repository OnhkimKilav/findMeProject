package com.findme.service.userService;

import com.findme.check.checkUser;
import com.findme.dao.userDAO.IUserDAORequestFriends;
import com.findme.exception.BadRequestException;
import com.findme.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * This class implements a service for checking requests for receiving lists of outgoing
 * and incoming requests as friends.
 */

@org.springframework.stereotype.Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserServiceRequestFriendsImpl implements IUserServiceRequestFriends {
    private IUserDAORequestFriends userDAORequestFriends;

    @Autowired
    public UserServiceRequestFriendsImpl(IUserDAORequestFriends userDAORequestFriends) {
        this.userDAORequestFriends = userDAORequestFriends;
    }

    /**
     * This method implements a check for receiving a list of friend requests received
     * If the test passes, the values are passed to the DAO.
     *
     * @param session - user who at now logged in at the session.
     * @param userId  - ID of the user who received the request to a friend.
     * @return list of users from whom we received a friend request.
     */

    @Override
    public ArrayList<User> getIncomeRequests(HttpSession session, String userId) {
        checkUser.userIsSessionUser(session, userId);

        return userDAORequestFriends.getIncomeRequests(userId);
    }

    /**
     * This method implements a check for receiving a list of sent request as friends.
     * If the test passes, the values are passed to the DAO.
     *
     * @param session - user who at now logged in at the session.
     * @param userId  - ID of the user who sent the request to a friend.
     * @return list of users to whom we sent a friend request.
     */

    @Override
    public ArrayList<User> getOutcomeRequests(HttpSession session, String userId) {
        checkUser.userIsSessionUser(session, userId);

        return userDAORequestFriends.getOutcomeRequests(userId);
    }
}
