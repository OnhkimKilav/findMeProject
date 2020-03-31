package com.findme.service.userService.CheckDeleteRelationship;

public interface IDeleteRelationship {
    IDeleteRelationship setNext(IDeleteRelationship next);

    void deleteRelationship(String userIdFrom, String userIdTo);
}
