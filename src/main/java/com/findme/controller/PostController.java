package com.findme.controller;

import com.findme.model.Post;
import com.findme.service.PostService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Controller
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/savePost", produces = "text/plain")
    public ResponseEntity<String> savePost(HttpServletRequest req) throws IOException {
        Post post = null;
        try {
            post = postService.save(readValuesPostman(req));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), null, null);
        }
        return new ResponseEntity<String>("Ok : " + post.toString(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findPost", produces = "text/plain")
    public ResponseEntity<String> findPost(HttpServletRequest req) throws IOException {
        Post post = null;
        try {
            post = postService.findById(Long.parseLong(req.getParameter("id")));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), null, null);
        }
        return new ResponseEntity<String>("Ok : " + post.toString(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deletePost", produces = "text/plain")
    public ResponseEntity<String> deletePost(HttpServletRequest req) {
        try {
            postService.delete(Long.parseLong(req.getParameter("id")));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), null, null);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updatePost", produces = "text/plain")
    public ResponseEntity<String> updatePost(HttpServletRequest req) throws IOException {
        try {
            postService.update(readValuesPostman(req));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), null, null);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }

    private Post readValuesPostman(HttpServletRequest req) throws IOException {
        Post post;
        try (BufferedReader reader = req.getReader()) {
            Gson gson = new Gson();
            post = gson.fromJson(reader, Post.class);
        }
        return post;
    }
}
