package com.findme.service.userService;

import javax.servlet.http.HttpSession;

public interface IUserServiceRelationship {
    void addRelationship(HttpSession session, String userIdFrom, String userIdTo);

    void updateRelationship(HttpSession session, String userIdFrom, String userIdTo, String status);
}
