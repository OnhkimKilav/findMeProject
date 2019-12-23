package com.findme.controller;

import com.findme.model.User;
import com.findme.service.Service;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Controller
public class UserController {
    private Service service;

    @Autowired
    public UserController(Service service) {
        this.service = service;
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(Model model, @PathVariable String userId){
        User user = (User)service.findById(User.class, Long.valueOf(userId));
        model.addAttribute("user", user);
        return "profile";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/saveUser", produces = "text/plain")
    public ResponseEntity<String> saveUser(HttpServletRequest req) throws IOException {
        User user = null;
        try {
            user = (User) service.save(readValuesPostman(req));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), null, null);
        }
        return new ResponseEntity<String>("Ok : " + user.toString(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findUser", produces = "text/plain")
    public ResponseEntity<String> findUser(HttpServletRequest req) throws IOException {
        User user = null;
        try {
            user = (User) service.findById(User.class, Long.parseLong(req.getParameter("id")));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), null, null);
        }
        return new ResponseEntity<String>("Ok : " + user.toString(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteUser", produces = "text/plain")
    public ResponseEntity<String> deleteUser(HttpServletRequest req) {
        try {
            service.delete(User.class, Long.parseLong(req.getParameter("id")));
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
