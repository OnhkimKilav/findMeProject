package com.findme.service.userService;

import com.findme.dao.userDAO.IUserDAOLogging;
import com.findme.exception.BadRequestException;
import com.findme.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * This class is a functional description for logging in and out of a session.
 */

@org.springframework.stereotype.Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserServiceLoggingImpl implements IUserServiceLogging {

    private IUserDAOLogging userDAOLogging;

    @Autowired
    public UserServiceLoggingImpl(IUserDAOLogging userDAOLogging) {
        this.userDAOLogging = userDAOLogging;
    }

    /**
     * This method implements login verification.
     * Firstly, you need to get the user from the database, which is now in session.
     * Secondly, we check the user for NULL and the session for creation,
     * if the session is already created or the user does not exist, then we return an exception.
     * If everything is in order, then the User object is returned.
     *
     * @param email       - individual value by which the user will log in
     * @param password    - individual value by which the user will log in
     * @param userSession - user who at now logged in at the session
     * @return User object which will log in
     */

    @Override
    public User logIn(String email, String password, User userSession) {
        User user = userDAOLogging.checkLogIn(email, password);

        if (user == null)
            throw new BadRequestException("You enter not correct email or password");
        if (userSession != null)
            throw new BadRequestException("Session is already create");
        return user;
    }

    /**
     * This method checking so that at log out user from session it's not null, if that true then return an exception.
     *
     * @param user - user value from session
     */

    @Override
    public void logOut(User user) {
        if (user == null)
            throw new BadRequestException("You are not logged in at the moment");
    }
}
