package com.findme.service.userService.CheckDeleteRelationship;

import javax.servlet.http.HttpSession;

public interface IUserDeleteRelationshipCheck {
    void setNext(IUserDeleteRelationshipCheck next);

    void deleteRelationship(HttpSession session, String userIdFrom, String userIdTo);
}
