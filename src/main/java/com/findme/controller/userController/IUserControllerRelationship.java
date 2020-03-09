package com.findme.controller.userController;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface IUserControllerRelationship {
    ResponseEntity<String> addRelationship(HttpServletRequest request);

    ResponseEntity<String> updateRelationship(HttpServletRequest request);
}
