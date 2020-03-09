package com.findme.controller.userController;

import com.findme.exception.BadRequestException;
import com.findme.service.userService.IUserServiceRelationship;
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
public class UserControllerRelationshipImpl implements IUserControllerRelationship {
    private IUserServiceRelationship serviceRelationship;

    @Autowired
    public UserControllerRelationshipImpl(IUserServiceRelationship serviceRelationship) {
        this.serviceRelationship = serviceRelationship;
    }

    @Override
    @RequestMapping(path = "/addRelationship", method = RequestMethod.GET)
    public ResponseEntity<String> addRelationship(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        try {
            serviceRelationship.addRelationship(session, request.getParameter("userIdFrom"), request.getParameter("userIdTo"));
        } catch (BadRequestException bre) {
            return new ResponseEntity<String>(bre.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }

    @Override
    @RequestMapping(path = "/updateRelationship", method = RequestMethod.GET)
    public ResponseEntity<String> updateRelationship(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        try {
            serviceRelationship.updateRelationship(session, request.getParameter("userIdFrom"), request.getParameter("userIdTo"), request.getParameter("status"));
        } catch (BadRequestException bre) {
            return new ResponseEntity<String>(bre.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }
}
