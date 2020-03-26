package com.findme.service.userService.CheckDeleteRelationship;

import javax.servlet.http.HttpSession;

public abstract class UserDeleteRelationshipCheck {
    private UserDeleteRelationshipCheck next;

    public UserDeleteRelationshipCheck linkWith(UserDeleteRelationshipCheck next) {
        this.next = next;
        return next;
    }

    public abstract boolean deleteRelationship(HttpSession session, String userIdFrom, String userIdTo);

    protected boolean checkNext(HttpSession session, String userIdFrom, String userIdTo){
        if(next == null)
            return true;

        return next.deleteRelationship(session, userIdFrom, userIdTo);
    }

}
