package com.findme.service.userService.CheckDeleteRelationship;

import com.findme.RelationshipStatus;
import com.findme.check.checkUser;
import com.findme.dao.userDAO.IUserDAORelationship;
import com.findme.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component(value = "othersChecks")
public class OthersChecks implements IDeleteRelationship {
    private IDeleteRelationship next;
    private IUserDAORelationship userDAORelationship;

    @Autowired
    public OthersChecks(IUserDAORelationship userDAORelationship) {
        this.userDAORelationship = userDAORelationship;
    }

    @Override
    public IDeleteRelationship setNext(IDeleteRelationship next) {
        this.next = next;
        return next;
    }

    //1. Если залогиненный пользователь получатель или отпровитель я могу удалить +
    //2. Если мы являемся друзьями +

    @Override
    public void delete(HttpSession session, String userIdFrom, String userIdTo) {
        checkUser.bothUsersNull(userIdFrom, userIdTo);
        checkUser.bothUserIsSessionUser(session, userIdFrom, userIdTo);
        if(!(userDAORelationship.getStatusRelationship(userIdFrom, userIdTo).equals(String.valueOf(RelationshipStatus.FRIENDS)))){
            throw new BadRequestException("You cannot delete this user. You are not a friends");
        }
        checkNull(session, userIdFrom, userIdTo);
    }

    private void checkNull(HttpSession session, String userIdFrom, String userIdTo){
        if(next == null)
            return;
        next.delete(session, userIdFrom, userIdTo);
    }
}
