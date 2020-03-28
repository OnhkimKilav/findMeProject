package com.findme.service.userService.CheckDeleteRelationship;

import javax.servlet.http.HttpSession;

public interface IDeleteRelationship {
    void setNext(IDeleteRelationship next);

    void deleteRelationship(HttpSession session, String userIdFrom, String userIdTo);
}
