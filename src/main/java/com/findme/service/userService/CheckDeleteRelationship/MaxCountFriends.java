package com.findme.service.userService.CheckDeleteRelationship;

import com.findme.dao.userDAO.IUserDAODeleteRelationship;
import com.findme.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "maxCountFriends")
public class MaxCountFriends implements IDeleteRelationship {
    private IDeleteRelationship next;
    private IUserDAODeleteRelationship iUserDAODeleteRelationship;

    @Autowired
    public MaxCountFriends(IUserDAODeleteRelationship iUserDAODeleteRelationship) {
        this.iUserDAODeleteRelationship = iUserDAODeleteRelationship;
    }

    @Override
    public IDeleteRelationship setNext(IDeleteRelationship next) {
        this.next = next;
        return next;
    }

    @Override
    public void deleteRelationship(String userIdFrom, String userIdTo) {
        System.out.println("Method deleteRalationship from class MaxCountFriends is done");

        if (iUserDAODeleteRelationship.maxCountFriends(userIdFrom, userIdTo) > 100) {
            throw new BadRequestException("Sorry. You have more than 100 friends.");
        } else {
            System.out.println("Next check");
            checkNull(userIdFrom, userIdTo);
        }
    }

    private void checkNull(String userIdFrom, String userIdTo){
        if(next == null)
            return;
        next.deleteRelationship(userIdFrom, userIdTo);
    }
}
