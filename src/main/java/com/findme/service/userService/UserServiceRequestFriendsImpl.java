package com.findme.service.userService;

import com.findme.dao.userDAO.IUserDAORequestFriends;
import com.findme.exception.BadRequestException;
import com.findme.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@org.springframework.stereotype.Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserServiceRequestFriendsImpl implements IUserServiceRequestFriends {
    private IUserDAORequestFriends userDAORequestFriends;

    @Autowired
    public UserServiceRequestFriendsImpl(IUserDAORequestFriends userDAORequestFriends) {
        this.userDAORequestFriends = userDAORequestFriends;
    }

    @Override
    public ArrayList<User> getIncomeRequests(HttpSession session, String userId) {
        if (!(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userId)))
            throw new BadRequestException("You can't use this function. You need log in");
        return userDAORequestFriends.getIncomeRequests(userId);
    }

    @Override
    public ArrayList<User> getOutcomeRequests(HttpSession session, String userId) {
        if (!(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userId)))
            throw new BadRequestException("You can't use this function. You need log in");
        return userDAORequestFriends.getOutcomeRequests(userId);
    }
}
