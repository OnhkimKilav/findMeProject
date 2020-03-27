package com.findme.service.userService.CheckDeleteRelationship;

import com.findme.dao.userDAO.UserDAODeleteRelationshipCheckImpl;

import javax.servlet.http.HttpSession;

/*public class MaxCountOutgoingRequests extends UserDeleteRelationshipCheck{

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
}*/

public class MaxCountOutgoingRequests implements IUserDeleteRelationshipCheck{
    private IUserDeleteRelationshipCheck next;

    @Override
    public void setNext(IUserDeleteRelationshipCheck next) {
        this.next = next;
    }

    @Override
    public void deleteRelationship(HttpSession session, String userIdFrom, String userIdTo) {
        System.out.println("Method deleteRalationship from class MaxCountOutgoingRequests is done");
        System.out.println("Next check");
        next.deleteRelationship(session, userIdFrom, userIdTo);
    }
}
