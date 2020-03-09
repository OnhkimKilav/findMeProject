package com.findme.service.userService;

import com.findme.model.User;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public interface IUserServiceRequestFriends {
    ArrayList<User> getIncomeRequests(HttpSession session, String userId);

    ArrayList<User> getOutcomeRequests(HttpSession session, String userId);
}
