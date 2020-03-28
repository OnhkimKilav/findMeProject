package com.findme.service.userService.CheckDeleteRelationship;

import javax.servlet.http.HttpSession;

public class MaxCountOutgoingRequests implements IDeleteRelationship {
    private IDeleteRelationship next;

    @Override
    public void setNext(IDeleteRelationship next) {
        this.next = next;
    }

    @Override
    public void deleteRelationship(HttpSession session, String userIdFrom, String userIdTo) {
        System.out.println("Method deleteRalationship from class MaxCountOutgoingRequests is done");
        System.out.println("Next check");
        next.deleteRelationship(session, userIdFrom, userIdTo);
    }
}
