package com.findme.controller.postController;

import com.findme.model.Post;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface IPostControllerCreate {
    ResponseEntity<String> createPost(Post post, HttpSession session, HttpServletRequest request);
}
