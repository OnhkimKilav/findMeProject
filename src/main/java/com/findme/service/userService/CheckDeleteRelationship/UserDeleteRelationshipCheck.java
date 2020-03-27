package com.findme.service.userService.CheckDeleteRelationship;


import com.findme.dao.userDAO.IUserDAODeleteRelationshipCheck;
import com.findme.dao.userDAO.UserDAODeleteRelationshipCheckImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

public abstract class UserDeleteRelationshipCheck {
    @Autowired
    protected IUserDAODeleteRelationshipCheck iUserDAODeleteRelationshipCheck;

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
