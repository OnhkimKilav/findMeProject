package com.findme.service.userService.CheckDeleteRelationship;

import javax.servlet.http.HttpSession;

public class MaxCountOutgoingRequests extends UserDeleteRelationshipCheck{
    @Override
    public boolean deleteRelationship(HttpSession session, String userIdFrom, String userIdTo) {
        System.out.println("Method deleteRalationship from class MaxCountOutgoingRequests is done");
        if(userIdTo == null){
            System.out.println("Id = null");
            return true;
        }
        System.out.println("Next check");
        return checkNext(session, userIdFrom, userIdTo);
    }
}
