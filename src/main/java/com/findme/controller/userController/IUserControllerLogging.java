package com.findme.controller.userController;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface IUserControllerLogging {
    ResponseEntity<String> logIn(HttpSession session, HttpServletRequest request);

    ResponseEntity<String> logOut(HttpSession session);
}
