package com.findme.controller;

import com.findme.exception.BadRequestException;
import com.findme.model.User;
import com.findme.service.UserService;
import com.google.gson.Gson;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class UserController {
    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(path = "/getOutcomeRequest", method = RequestMethod.GET)
    public String getOutcomeRequest(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        ArrayList<User> users;
        try {
            users = service.getOutcomeRequests(session, request.getParameter("userId"));
        } catch (BadRequestException bre) {
            model.addAttribute("exception", bre.getMessage());
            return "profileException";
        } catch (Exception e) {
            model.addAttribute("exception", e.getMessage());
            return "profileException";
        }
        model.addAttribute("user", service.findById(Long.valueOf(request.getParameter("userId"))));
        model.addAttribute("usersOutcome", users);
        return "profile";
    }

    @RequestMapping(path = "/getIncomeRequest", method = RequestMethod.GET)
    public String getIncomeRequest(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        ArrayList<User> users;
        try {
            users = service.getIncomeRequests(session, request.getParameter("userId"));
        } catch (BadRequestException bre) {
            model.addAttribute("exception", bre.getMessage());
            return "profileException";
        } catch (Exception e) {
            model.addAttribute("exception", e.getMessage());
            return "profileException";
        }
        model.addAttribute("user", service.findById(Long.valueOf(request.getParameter("userId"))));
        model.addAttribute("usersIncome", users);
        return "profile";
    }

    @RequestMapping(path = "/addRelationship", method = RequestMethod.GET)
    public ResponseEntity<String> addRelationship(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        try {
            service.addRelationship(session, request.getParameter("userIdFrom"), request.getParameter("userIdTo"));
        } catch (BadRequestException bre) {
            return new ResponseEntity<String>(bre.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }

    //http://localhost:8080/updateRelationship?userIdFrom=161&userIdTo=141&status=friends

    @RequestMapping(path = "/updateRelationship", method = RequestMethod.GET)
    public ResponseEntity<String> updateRelationship(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        try {
            service.updateRelationship(session, request.getParameter("userIdFrom"), request.getParameter("userIdTo"), request.getParameter("status"));
        } catch (BadRequestException bre) {
            return new ResponseEntity<String>(bre.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }

    @RequestMapping(path = "/logIn", method = RequestMethod.GET)
    public ResponseEntity<String> logIn(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = service.logIn(request.getParameter("email"), request.getParameter("password"), (User) session.getAttribute("user"));
            session.setAttribute("user", user);
        } catch (BadRequestException bre) {
            return new ResponseEntity<String>(bre.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }

    @RequestMapping(path = "/logOut", method = RequestMethod.GET)
    public ResponseEntity<String> logOut(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        try {
            service.logOut((User) session.getAttribute("user"));
            session.invalidate();
        } catch (BadRequestException bre) {
            return new ResponseEntity<String>(bre.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
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
            service.save(user);
        } catch (BadRequestException bre) {
            return new ResponseEntity<String>(bre.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
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
        return new ResponseEntity<String>("Ok : " + user.toString(), HttpStatus.CREATED);
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
