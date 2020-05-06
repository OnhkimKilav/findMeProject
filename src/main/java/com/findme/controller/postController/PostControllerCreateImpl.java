package com.findme.controller.postController;

import com.findme.exception.BadRequestException;
import com.findme.exception.Validate;
import com.findme.model.Post;
import com.findme.model.User;
import com.findme.service.postService.IPostServiceCreateImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class PostControllerCreateImpl implements IPostControllerCreate {
    private IPostServiceCreateImpl iPostServiceCreate;

    @Autowired
    public PostControllerCreateImpl(IPostServiceCreateImpl iPostServiceCreate) {
        this.iPostServiceCreate = iPostServiceCreate;
    }

    //http://localhost:8080/createPost?message=%22Hello,%20I%27m%20Valik%22&usersTagged=2&userPosted=1&userPagePosted=4
    /*@Override
    @RequestMapping(path = "/createPost", method = RequestMethod.POST)
    public ResponseEntity<String> createPost(@ModelAttribute Post post, HttpSession session, HttpServletRequest request) {
        try {
            *//*Post post = new Post(String.valueOf(request.getAttribute("message")), new Date(), String.valueOf(request.getAttribute("location")),
                    request.getAttribute("usersTagged"),  request.getAttribute("usersTagged"), request.getAttribute("usersTagged"));*//*
            iPostServiceCreate.createPost(session, post);
        } catch (BadRequestException bre) {
            return new ResponseEntity<String>(bre.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }*/

    //http://localhost:8080/createPost/message%20location%201%202%203%205%2056

    @Override
    @RequestMapping(path = "/createPost/{message}/{location}/{usersTagged}/{userPosted}/{userPagePosted}", method = RequestMethod.GET)
    public ResponseEntity<String> createPost(HttpServletRequest request, HttpSession session, @PathVariable(value = "message") String message,
                                             @PathVariable(value = "location") String location,
                                             @PathVariable(value = "usersTagged") String usersTagged,
                                             @PathVariable(value = "userPosted") Long userPosted,
                                             @PathVariable(value = "userPagePosted") Long userPagePosted) {
        try {
            Validate.checkLogIn(session);
            iPostServiceCreate.createPost(session, message, location, usersTagged, userPosted, userPagePosted);
        } catch (BadRequestException bre) {
            return new ResponseEntity<String>(bre.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }

    @RequestMapping(path = "/create-post", method = RequestMethod.GET)
    public String createPost() {
        return "post";
    }
}
