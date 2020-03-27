package com.findme.service.userService;

import com.findme.RelationshipStatus;
import com.findme.dao.userDAO.IUserDAORelationship;
import com.findme.exception.BadRequestException;
import com.findme.model.User;
import com.findme.service.userService.CheckDeleteRelationship.*;
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

    //1.Если введенные айди одинаковые (ошибка) +
    //2.Если не залогиненный под нужным айди юзер пытается добавить в друзья (Ошибка) +
    //3.Если связь уже существует и статус его не(NOT_FRIENDS) (ошибка)
    //4.Если связь уже существует и статус его (NOT_FRIENDS) (меняется на (REQUEST_SENDED))
    //5.Если связи нету в базе и все проверки проходят (создается связь между айдишниками)

    @Override
    public void addRelationship(HttpSession session, String userIdFrom, String userIdTo) {
        String status = userDAORelationship.getStatusRelationship(userIdFrom, userIdTo);

        if (userIdFrom.equals(userIdTo))
            throw new BadRequestException("You cannot add yourself as a friend");
        else if (!(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom)))
            throw new BadRequestException("To add friends under this user you need to log in");
        if (status == null)
            userDAORelationship.addRelationship(userIdFrom, userIdTo);
        else if (status.equals(String.valueOf(RelationshipStatus.NOT_FRIENDS))) {
            userDAORelationship.updateRelationship(userIdFrom, userIdTo, String.valueOf(RelationshipStatus.REQUEST_SENDED));
            return;
        } else throw new BadRequestException("Request to this user has already been sent");

    }

    //Ты отпровитель но ты не имеешь статуса отвленного запроса
    //Ты получатель но ты не имеешь статуса полученного запроса

    //In order to exception was returned when user was log in behind sender
    //1. I'm sender
    //2. I'm not recipient
    //3. Status relationship in the DB should by REQUEST_SENDED

    @Override
    public void updateRelationship(HttpSession session, String userIdFrom, String userIdTo, String status) {
        String statusRelationship = userDAORelationship.getStatusRelationship(userIdFrom, userIdTo);

        if (!(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom))
                && !(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdTo)))
            throw new BadRequestException("You can't use this function. You need log in");
        else if (statusRelationship == null) {
            throw new BadRequestException("You cannot update relationship which does not exist");
        } else if ((String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom))
                && !(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdTo))) {
            if (!(statusRelationship.equals(String.valueOf(RelationshipStatus.REQUEST_SENDED))))
                throw new BadRequestException("You cannot update this relationship. You did not receive a request from this user or request already accepted");
        } else if (!(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom))
                && (String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdTo))) {
            if (((statusRelationship.equals(String.valueOf(RelationshipStatus.NOT_FRIENDS)))))
                throw new BadRequestException("You cannot update this relationship. You did not receive a request from this user");
        }
        userDAORelationship.updateRelationship(userIdFrom, userIdTo, status);
    }

    /**
     * Method implements a relationship deleting.
     * It's using  patterns chain of responsibility.
     *
     * @param session
     * @param userIdFrom The variable means id user who sended request
     * @param userIdTo   The variable means if user who recipiented request
     * @return
     */

    @Override
    public void deleteRelationship(HttpSession session, String userIdFrom, String userIdTo) {
        /*UserDeleteRelationshipCheck deleteRelationshipCheck = new RelationshipTime();
        //deleteRelationshipCheck.linkWith(new MaxCountFriends()).linkWith(new MaxCountOutgoingRequests());*/
        IUserDeleteRelationshipCheck relationshipTime = new RelationshipTime();
        IUserDeleteRelationshipCheck maxCountFriends = new MaxCountFriends();
        IUserDeleteRelationshipCheck maxCountOutgoingRequests = new MaxCountOutgoingRequests();
        relationshipTime.setNext(maxCountFriends);
        maxCountFriends.setNext(maxCountOutgoingRequests);

        relationshipTime.deleteRelationship(session, userIdFrom, userIdTo);


    }


}
