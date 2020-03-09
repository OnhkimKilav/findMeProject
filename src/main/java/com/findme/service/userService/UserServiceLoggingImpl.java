package com.findme.service.userService;

import com.findme.dao.userDAO.IUserDAOLogging;
import com.findme.exception.BadRequestException;
import com.findme.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@org.springframework.stereotype.Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserServiceLoggingImpl implements IUserServiceLogging {

    private IUserDAOLogging userDAOLogging;

    @Autowired
    public UserServiceLoggingImpl(IUserDAOLogging userDAOLogging) {
        this.userDAOLogging = userDAOLogging;
    }

    @Override
    public User logIn(String email, String password, User userSession) {
        User user = userDAOLogging.checkLogIn(email, password);
        if (user == null)
            throw new BadRequestException("You enter not correct email or password");
        if (userSession != null)
            throw new BadRequestException("Session is already create");
        return user;
    }

    @Override
    public void logOut(User user) {
        if (user == null)
            throw new BadRequestException("You are not logged in at the moment");
    }
}
