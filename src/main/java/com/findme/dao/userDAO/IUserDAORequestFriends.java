package com.findme.dao.userDAO;

import com.findme.model.User;

import java.util.ArrayList;

public interface IUserDAORequestFriends {
    ArrayList<User> getIncomeRequests(String userId);

    ArrayList<User> getOutcomeRequests(String userId);
}
