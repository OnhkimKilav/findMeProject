package com.findme.controller.postController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface IPostControllerCreate {
    @RequestMapping(path = "/createPost/{message}{location}{usersTagged}{userPosted}{userPagePosted}", method = RequestMethod.POST)
    ResponseEntity<String> createPost(HttpServletRequest request, HttpSession session, @PathVariable(value = "message") String message,
                                      @PathVariable(value = "location") String location,
                                      @PathVariable(value = "usersTagged") String usersTagged,
                                      @PathVariable(value = "userPosted") Long userPosted,
                                      @PathVariable(value = "userPagePosted") Long userPagePosted);
}
