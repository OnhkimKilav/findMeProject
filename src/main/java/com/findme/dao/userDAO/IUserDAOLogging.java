package com.findme.dao.userDAO;

import com.findme.model.User;

public interface IUserDAOLogging {
    User checkLogIn(String email, String password);
}
