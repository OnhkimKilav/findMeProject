package com.findme.service;

import com.findme.RelationshipStatus;
import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.Validate;
import com.findme.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;

@org.springframework.stereotype.Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements Service<User> {
    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User logIn(String email, String password, User userSession) {
        User user = userDAO.checkLogIn(email, password);
        if (user == null)
            throw new BadRequestException("You enter not correct email or password");
        if (userSession != null)
            throw new BadRequestException("Session is already create");
        return user;
    }

    public void logOut(User user) {
        if (user == null)
            throw new BadRequestException("You are not logged in at the moment");
    }

    @Override
    public User save(User user) throws RuntimeException {
        if (userDAO.userByPhone(user.getPhone()) != 0)
            throw new BadRequestException("This phone is already use");
        if (userDAO.userByPhone(user.getEmail()) != 0)
            throw new BadRequestException("This email is already use");
        if (user.getDateRegistered() == null)
            user.setDateRegistered(new Date());
        if (user.getDateLastActive() == null)
            user.setDateLastActive(new Date());

        return userDAO.save(user);
    }

    @Override
    public void delete(Long id) {
        userDAO.delete(id);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public User findById(Long id) {
        return userDAO.findById(id);
    }

    public void addRelationship(HttpSession session, String userIdFrom, String userIdTo) {
        Validate.checkLogIn(session);
        if (userIdFrom.equals(userIdTo))
            throw new BadRequestException("You cannot add yourself as a friend");
        else if (!(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom)))
            throw new BadRequestException("To add friends under this user you need to log in");
        userDAO.addRelationship(userIdFrom, userIdTo);
    }

    public void updateRelationship(HttpSession session, String userIdFrom, String userIdTo, String status) {
        //Не залогинен
        //Ты отпровитель но ты не имеешь статуса отвленного запроса
        //Ты получатель но ты не имеешь статуса полученного запроса

        Validate.checkLogIn(session);

        if (!(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom))
                && !(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdTo)))
            throw new BadRequestException("You can't use this function. You need log in");

            //In order to exception was returned when user was log in behind sender
            //1. I'm sender
            //2. I'm not recipient
            //3. Status relationship in the DB should by REQUEST_SENDED
            //4. Status of URL should by NOT_FRIEND
        else if ((String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom))
                && !(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdTo))
                && (!(userDAO.getStatusRelationship(userIdFrom, userIdTo).equals(String.valueOf(RelationshipStatus.REQUEST_SENDED)))
                || !(status.equals(String.valueOf(RelationshipStatus.NOT_FRIENDS))))) {
            throw new BadRequestException("You cannot update this relationship. You did not receive a request from this user or request already accepted");
        } else if (!(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom))
                && (String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdTo))
                && ((userDAO.getStatusRelationship(userIdFrom, userIdTo).equals(String.valueOf(RelationshipStatus.NOT_FRIENDS)))))
            throw new BadRequestException("You cannot update this relationship. You did not receive a request from this user");

        userDAO.updateRelationship(userIdFrom, userIdTo, status);
    }

    public ArrayList<User> getIncomeRequests(HttpSession session, String userId) {
        Validate.checkLogIn(session);
        if (!(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userId)))
            throw new BadRequestException("You can't use this function. You need log in");
        return userDAO.getIncomeRequests(userId);
    }

    public ArrayList<User> getOutcomeRequests(HttpSession session, String userId) {
        Validate.checkLogIn(session);
        if (!(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userId)))
            throw new BadRequestException("You can't use this function. You need log in");
        return userDAO.getOutcomeRequests(userId);
    }
}
