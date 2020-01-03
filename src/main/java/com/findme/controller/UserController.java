package com.findme.controller;

import com.findme.model.User;
import com.findme.service.UserService;
import com.google.gson.Gson;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Controller
public class UserController {
    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(Model model, @PathVariable String userId) {
        User user = service.findById(Long.valueOf(userId));
        if (user == null) {
            model.addAttribute("exception", new ObjectNotFoundException("Object with this id " + userId + " not found"));
            return "profileException";
        } else {
            model.addAttribute("user", user);
            return "profile";
        }
    }

    @RequestMapping(path = "/register-user", method = RequestMethod.POST)
    public ResponseEntity<String> registerUser(@ModelAttribute User user) {
        try {
            if (service.findByPhone(user.getPhone()) != 0)
                return new ResponseEntity<>("This phone number is already registered", HttpStatus.NOT_FOUND);
            else if (service.findByEmail(user.getEmail()) != 0) {
                return new ResponseEntity<>("This email is already registered", HttpStatus.NOT_FOUND);
            } else service.save(user);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/saveUser", produces = "text/plain")
    public ResponseEntity<String> saveUser(HttpServletRequest req) throws IOException {
        User user = null;
        try {
            user = service.save(readValuesPostman(req));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), null, null);
        }
        return new ResponseEntity<String>("Ok : " + user.toString(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findUser", produces = "text/plain")
    public ResponseEntity<String> findUser(HttpServletRequest req) throws IOException {
        User user = null;
        try {
            user = service.findById(Long.parseLong(req.getParameter("id")));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), null, null);
        }
        return new ResponseEntity<String>("Ok : " + user.toString(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteUser", produces = "text/plain")
    public ResponseEntity<String> deleteUser(HttpServletRequest req) {
        try {
            service.delete(Long.parseLong(req.getParameter("id")));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), null, null);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateUser", produces = "text/plain")
    public ResponseEntity<String> updateUser(HttpServletRequest req) throws IOException {
        try {
            service.update(readValuesPostman(req));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), null, null);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }

    private User readValuesPostman(HttpServletRequest req) throws IOException {
        User user;
        try (BufferedReader reader = req.getReader()) {
            Gson gson = new Gson();
            user = gson.fromJson(reader, User.class);
        }
        return user;
    }
}
