package com.findme.service.postService;

import javax.servlet.http.HttpSession;

public interface IPostServiceCreate {
    void createPost(HttpSession session, String message, String location, String usersTagged, Long userPosted,
                    Long userPagePosted);
}
