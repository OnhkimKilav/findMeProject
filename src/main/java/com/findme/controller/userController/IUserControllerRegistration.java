package com.findme.controller.userController;

import com.findme.model.User;
import org.springframework.http.ResponseEntity;

public interface IUserControllerRegistration {
    ResponseEntity<String> registerUser(User user);
}
