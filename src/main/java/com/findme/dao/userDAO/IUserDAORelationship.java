package com.findme.dao.userDAO;

public interface IUserDAORelationship {
    void addRelationship(String userIdFrom, String userIdTo);

    void updateRelationship(String userIdFrom, String userIdTo, String status);

    String getStatusRelationship(String userIdFrom, String userIdTo);

    Integer getExistenceRelationship(String userIdFrom, String userIdTo);
}
