package com.findme.controller.postController;

import com.findme.exception.BadRequestException;
import com.findme.exception.Validate;
import com.findme.model.Post;
import com.findme.model.User;
import com.findme.service.IServiceCRAD;
import com.findme.service.postService.IPostServiceDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class PostControllerDisplayImpl implements IPostControllerDisplay {
    private IPostServiceDisplay iPostServiceDisplay;
    private IServiceCRAD<User> userIServiceCRAD;

    @Autowired
    public PostControllerDisplayImpl(IPostServiceDisplay iPostServiceDisplay, IServiceCRAD<User> userIServiceCRAD) {
        this.iPostServiceDisplay = iPostServiceDisplay;
        this.userIServiceCRAD = userIServiceCRAD;
    }

    @Override
    @RequestMapping(path = "/displayPost", method = RequestMethod.GET)
    public String displayPost(Model model, HttpServletRequest request, HttpSession session) {
        ArrayList<Post> posts;
        try {
            Validate.checkLogIn(session);
            posts = iPostServiceDisplay.displayPost(session, request.getParameter("userId"));
        } catch (BadRequestException bre) {
            model.addAttribute("exception", bre.getMessage());
            return "profileException";
        } catch (Exception e) {
            model.addAttribute("exception", e.getMessage());
            return "profileException";
        }
        model.addAttribute("user", userIServiceCRAD.findById(Long.valueOf(request.getParameter("userId"))));
        model.addAttribute("posts", posts);
        return "profile";
    }
}
