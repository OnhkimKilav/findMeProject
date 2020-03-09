package com.findme.service.userService;

import com.findme.model.User;

public interface IUserServiceLogging {
    User logIn(String email, String password, User userSession);

    void logOut(User user);
}
