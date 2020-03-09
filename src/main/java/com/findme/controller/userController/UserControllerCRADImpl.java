package com.findme.controller.userController;

import com.findme.controller.IControllerCRAD;
import com.findme.model.User;
import com.findme.service.IServiceCRAD;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@RestController
public class UserControllerCRADImpl implements IControllerCRAD{
    private IServiceCRAD<User> service;

    @Autowired
    public UserControllerCRADImpl(IServiceCRAD<User> service) {
        this.service = service;
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, value = "/saveUser", produces = "text/plain")
    public ResponseEntity<String> save(HttpServletRequest req){
        User user = null;
        try {
            user = service.save(readValuesPostman(req));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), null, null);
        }
        return new ResponseEntity<String>("Ok : " + user.toString(), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findUser", produces = "text/plain")
    public ResponseEntity<String> findById(HttpServletRequest req) {
        User user = null;
        try {
            user = service.findById(Long.parseLong(req.getParameter("id")));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), null, null);
        }
        return new ResponseEntity<String>("Ok : " + user.toString(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteUser", produces = "text/plain")
    public ResponseEntity<String> delete(HttpServletRequest req) {
        try {
            service.delete(Long.parseLong(req.getParameter("id")));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), null, null);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateUser", produces = "text/plain")
    public ResponseEntity<String> update(HttpServletRequest req){
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
