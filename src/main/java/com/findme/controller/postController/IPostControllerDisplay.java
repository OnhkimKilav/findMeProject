package com.findme.controller.postController;

import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface IPostControllerDisplay {
    String displayPost(Model model, HttpServletRequest request, HttpSession session);
}
