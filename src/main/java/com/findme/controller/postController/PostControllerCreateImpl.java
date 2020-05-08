package com.findme.controller.postController;

import com.findme.exception.BadRequestException;
import com.findme.exception.Validate;
import com.findme.service.postService.PostServiceCreateImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PostControllerCreateImpl implements IPostControllerCreate {
    private PostServiceCreateImpl iPostServiceCreate;

    @Autowired
    public PostControllerCreateImpl(PostServiceCreateImpl iPostServiceCreate) {
        this.iPostServiceCreate = iPostServiceCreate;
    }

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
