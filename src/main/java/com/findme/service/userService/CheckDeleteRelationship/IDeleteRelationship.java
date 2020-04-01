package com.findme.service.userService.CheckDeleteRelationship;

import javax.servlet.http.HttpSession;

public interface IDeleteRelationship {
    IDeleteRelationship setNext(IDeleteRelationship next);

    void delete(HttpSession session, String userIdFrom, String userIdTo);
}
