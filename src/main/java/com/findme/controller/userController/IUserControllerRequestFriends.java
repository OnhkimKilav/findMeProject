package com.findme.controller.userController;

import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

public interface IUserControllerRequestFriends {
    String getOutcomeRequest(Model model, HttpServletRequest request);

    String getIncomeRequest(Model model, HttpServletRequest request);
}
