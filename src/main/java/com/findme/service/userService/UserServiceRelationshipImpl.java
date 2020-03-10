package com.findme.service.userService;

import com.findme.RelationshipStatus;
import com.findme.dao.userDAO.IUserDAORelationship;
import com.findme.exception.BadRequestException;
import com.findme.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import javax.servlet.http.HttpSession;

@org.springframework.stereotype.Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserServiceRelationshipImpl implements IUserServiceRelationship {

    private IUserDAORelationship userDAORelationship;

    @Autowired
    public UserServiceRelationshipImpl(IUserDAORelationship userDAORelationship) {
        this.userDAORelationship = userDAORelationship;
    }

    @Override
    public void addRelationship(HttpSession session, String userIdFrom, String userIdTo) {
        if (userIdFrom.equals(userIdTo))
            throw new BadRequestException("You cannot add yourself as a friend");
        else if (!(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom)))
            throw new BadRequestException("To add friends under this user you need to log in");
        else if (userDAORelationship.getExistenceRelationship(userIdFrom, userIdTo) == 1) {
            if (!(userDAORelationship.getStatusRelationship(userIdFrom, userIdTo).equals(String.valueOf(RelationshipStatus.NOT_FRIENDS))))
                throw new BadRequestException("Request to this user has already been sent");
            userDAORelationship.updateRelationship(userIdFrom, userIdTo, String.valueOf(RelationshipStatus.REQUEST_SENDED));
            return;
        }
        userDAORelationship.addRelationship(userIdFrom, userIdTo);
    }

    @Override
    public void updateRelationship(HttpSession session, String userIdFrom, String userIdTo, String status) {
        //Ты отпровитель но ты не имеешь статуса отвленного запроса
        //Ты получатель но ты не имеешь статуса полученного запроса

        if (!(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom))
                && !(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdTo)))
            throw new BadRequestException("You can't use this function. You need log in");

            //In order to exception was returned when user was log in behind sender
            //1. I'm sender
            //2. I'm not recipient
            //3. Status relationship in the DB should by REQUEST_SENDED
        else if ((String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom))
                && !(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdTo))) {
            if (!(userDAORelationship.getStatusRelationship(userIdFrom, userIdTo).equals(String.valueOf(RelationshipStatus.REQUEST_SENDED))))
                throw new BadRequestException("You cannot update this relationship. You did not receive a request from this user or request already accepted");
        } else if (!(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom))
                && (String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdTo))) {
            if (((userDAORelationship.getStatusRelationship(userIdFrom, userIdTo).equals(String.valueOf(RelationshipStatus.NOT_FRIENDS)))))
                throw new BadRequestException("You cannot update this relationship. You did not receive a request from this user");
        }
        userDAORelationship.updateRelationship(userIdFrom, userIdTo, status);
    }
}
