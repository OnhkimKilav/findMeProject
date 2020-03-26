package com.findme.controller.userController;

import com.findme.exception.BadRequestException;
import com.findme.exception.Validate;
import com.findme.service.userService.CheckDeleteRelationship.MaxCountFriends;
import com.findme.service.userService.CheckDeleteRelationship.MaxCountOutgoingRequests;
import com.findme.service.userService.CheckDeleteRelationship.RelationshipTime;
import com.findme.service.userService.CheckDeleteRelationship.UserDeleteRelationshipCheck;
import com.findme.service.userService.IUserServiceRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserControllerRelationshipImpl implements IUserControllerRelationship {
    private IUserServiceRelationship serviceRelationship;

    @Autowired
    public UserControllerRelationshipImpl(IUserServiceRelationship serviceRelationship) {
        this.serviceRelationship = serviceRelationship;
    }

    //http://localhost:8080/addRelationship?userIdFrom=161&userIdTo=141
    @Override
    @RequestMapping(path = "/addRelationship", method = RequestMethod.GET)
    public ResponseEntity<String> addRelationship(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        try {
            Validate.checkLogIn(session);
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
            Validate.checkLogIn(session);
            serviceRelationship.updateRelationship(session, request.getParameter("userIdFrom"), request.getParameter("userIdTo"), request.getParameter("status"));
        } catch (BadRequestException bre) {
            return new ResponseEntity<String>(bre.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }

    @Override
    @RequestMapping(path = "/deleteRelationship", method = RequestMethod.GET)
    public ResponseEntity<String> deleteRelationship(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        serviceRelationship.deleteRelationship(session, request.getParameter("userIdFrom"), request.getParameter("userIdTo"));
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }
}
