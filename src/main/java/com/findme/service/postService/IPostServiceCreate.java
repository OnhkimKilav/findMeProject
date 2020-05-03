package com.findme.service.postService;

import com.findme.model.Post;

import javax.servlet.http.HttpSession;

public interface IPostServiceCreate {
    void createPost(HttpSession session, Post post);
}
