package com.findme.controller.userController;

import com.findme.exception.BadRequestException;
import com.findme.model.User;
import com.findme.service.userService.IUserServiceLogging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class UserControllerLoggingImpl implements IUserControllerLogging {
    private IUserServiceLogging serviceLogging;

    @Autowired
    public UserControllerLoggingImpl(IUserServiceLogging serviceLogging) {
        this.serviceLogging = serviceLogging;
    }

    //http://localhost:8080/logIn?email=jhkfds@mail.ru&password=3345
    @Override
    @RequestMapping(path = "/logIn", method = RequestMethod.GET)
    public ResponseEntity<String> logIn(HttpSession session, HttpServletRequest request) {
        try {
            User user = serviceLogging.logIn(request.getParameter("email"), request.getParameter("password"), (User) session.getAttribute("user"));
            session.setAttribute("user", user);
        } catch (BadRequestException bre) {
            return new ResponseEntity<String>(bre.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }

    @Override
    @RequestMapping(path = "/logOut", method = RequestMethod.GET)
    public ResponseEntity<String> logOut(HttpSession session) {
        try {
            serviceLogging.logOut((User) session.getAttribute("user"));
            session.invalidate();
        } catch (BadRequestException bre) {
            return new ResponseEntity<String>(bre.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }
}
