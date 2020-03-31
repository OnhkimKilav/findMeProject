package com.findme.service.userService.CheckDeleteRelationship;

import com.findme.dao.userDAO.IUserDAODeleteRelationship;
import com.findme.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "maxCountOutgoingRequests")
public class MaxCountOutgoingRequests implements IDeleteRelationship {
    private IDeleteRelationship next;
    private IUserDAODeleteRelationship iUserDAODeleteRelationship;

    @Autowired
    public MaxCountOutgoingRequests(IUserDAODeleteRelationship iUserDAODeleteRelationship) {
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

        if (iUserDAODeleteRelationship.maxCountOutgoingRequest(userIdFrom, userIdTo) > 10) {
            throw new BadRequestException("Sorry. You have more than 100 friends.");
        }else {
            System.out.println("Next check");
            checkNull(userIdTo, userIdFrom);
        }
    }

    private void checkNull(String userIdFrom, String userIdTo){
        if(next == null)
            return;
        next.deleteRelationship(userIdFrom, userIdTo);
    }
}
