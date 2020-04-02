package com.findme.dao.userDAO;

public interface IUserDAORelationship {
    void addRelationship(String userIdFrom, String userIdTo);

    void updateRelationship(String userIdFrom, String userIdTo, String status);

    void deleteRelationship(String userIdFrom, String userIdTo);

    String getStatusRelationship(String userIdFrom, String userIdTo);
}
