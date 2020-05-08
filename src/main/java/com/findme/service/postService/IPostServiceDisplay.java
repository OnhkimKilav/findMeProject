package com.findme.service.postService;

import com.findme.model.Post;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public interface IPostServiceDisplay {
    ArrayList<Post> displayPost(HttpSession session, String userId);
}
