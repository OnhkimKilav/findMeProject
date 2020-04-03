package com.findme.service.userService;

import com.findme.RelationshipStatus;
import com.findme.check.checkUser;
import com.findme.dao.userDAO.IUserDAORelationship;
import com.findme.exception.BadRequestException;
import com.findme.model.User;
import com.findme.service.userService.CheckDeleteRelationship.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
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

    /**
     *
     *
     * @param session
     * @param userIdFrom
     * @param userIdTo
     * @param status
     */

    @Override
    public void updateRelationship(HttpSession session, String userIdFrom, String userIdTo, String status) {
        String statusRelationship = userDAORelationship.getStatusRelationship(userIdFrom, userIdTo);

        checkUser.bothUserIsSessionUser(session, userIdFrom, userIdTo);
        checkUser.nullStatusRelationship(statusRelationship);

        /**
         * This check need for request processing from sender to recipient for update relationship.
         * In the first checking we check so that user will be sender and not will be recipient.
         * And in the second check we check status relationship, it should be equal status " REQUEST_SENDED "
         */
        if ((String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom))
                && !(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdTo))) {
            if (!(statusRelationship.equals(String.valueOf(RelationshipStatus.REQUEST_SENDED))))
                throw new BadRequestException("You cannot update this relationship. You did not receive a request from this user or request already accepted");
        }
        /**
         * This check need for request processing from recipient  to sender for update relationship.
         * In the first checking we check so that user will be recipient  and not will be sender.
         * And in the second check we check status relationship, it should be not equal status " NOT_FRIENDS "
         */
        else if (!(String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdFrom))
                && (String.valueOf(((User) session.getAttribute("user")).getId()).equals(userIdTo))) {
            if (((statusRelationship.equals(String.valueOf(RelationshipStatus.NOT_FRIENDS)))))
                throw new BadRequestException("You cannot update this relationship. You did not receive a request from this user");
        }
        userDAORelationship.updateRelationship(userIdFrom, userIdTo, status);
    }

    @Autowired
    @Qualifier("relationshipTime")
    private IDeleteRelationship relationshipTime;

    @Autowired
    @Qualifier("maxCountFriends")
    private IDeleteRelationship maxCountFriends;

    @Autowired
    @Qualifier("maxCountOutgoingRequests")
    private IDeleteRelationship maxCountOutgoingRequests;

    @Autowired
    @Qualifier("othersChecks")
    private IDeleteRelationship othersChecks;

    /**
     * Method implements a relationship deleting.
     * It's using  patterns chain of responsibility.
     *
     * @param userIdFrom The variable means id user who sended request
     * @param userIdTo   The variable means if user who recipiented request
     */
    @Override
    public void deleteRelationship(HttpSession session, String userIdFrom, String userIdTo) {
        othersChecks.setNext(relationshipTime).setNext(maxCountFriends).setNext(maxCountOutgoingRequests);
        othersChecks.delete(session, userIdFrom, userIdTo);

        userDAORelationship.deleteRelationship(userIdFrom, userIdTo);
    }


}
