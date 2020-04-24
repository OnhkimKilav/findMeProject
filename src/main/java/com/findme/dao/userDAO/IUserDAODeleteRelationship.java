package com.findme.dao.userDAO;

import java.util.Date;

public interface IUserDAODeleteRelationship {
    Date relationshipTime(String userIdFrom, String userIdTo);

    int maxCountOutgoingRequest(String userIdFrom);

    int maxCountFriends(String userIdFrom, String userIdTo);
}
