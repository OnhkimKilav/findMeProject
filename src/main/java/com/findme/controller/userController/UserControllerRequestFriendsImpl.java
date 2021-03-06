package com.findme.controller.userController;

import com.findme.exception.BadRequestException;
import com.findme.exception.Validate;
import com.findme.model.User;
import com.findme.service.IServiceCRAD;
import com.findme.service.userService.IUserServiceRequestFriends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class UserControllerRequestFriendsImpl implements IUserControllerRequestFriends {

    private IServiceCRAD<User> serviceCRAD;
    private IUserServiceRequestFriends userServiceRequestFriends;

    @Autowired
    public UserControllerRequestFriendsImpl(IServiceCRAD<User> serviceCRAD, IUserServiceRequestFriends userServiceRequestFriends) {
        this.serviceCRAD = serviceCRAD;
        this.userServiceRequestFriends = userServiceRequestFriends;
    }

    //http://localhost:8080/getOutcomeRequest?userId=141
    @Override
    @RequestMapping(path = "/getOutcomeRequest", method = RequestMethod.GET)
    public String getOutcomeRequest(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        ArrayList<User> users;
        try {
            Validate.checkLogIn(session);
            users = userServiceRequestFriends.getOutcomeRequests(session, request.getParameter("userId"));
        } catch (BadRequestException bre) {
            model.addAttribute("exception", bre.getMessage());
            return "profileException";
        } catch (Exception e) {
            model.addAttribute("exception", e.getMessage());
            return "profileException";
        }
        model.addAttribute("user", serviceCRAD.findById(Long.valueOf(request.getParameter("userId"))));
        model.addAttribute("usersOutcome", users);
        return "profile";
    }

    @Override
    @RequestMapping(path = "/getIncomeRequest", method = RequestMethod.GET)
    public String getIncomeRequest(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        ArrayList<User> users;
        try {
            Validate.checkLogIn(session);
            users = userServiceRequestFriends.getIncomeRequests(session, request.getParameter("userId"));
        } catch (BadRequestException bre) {
            model.addAttribute("exception", bre.getMessage());
            return "profileException";
        } catch (Exception e) {
            model.addAttribute("exception", e.getMessage());
            return "profileException";
        }
        model.addAttribute("user", serviceCRAD.findById(Long.valueOf(request.getParameter("userId"))));
        model.addAttribute("usersIncome", users);
        return "profile";
    }
}
