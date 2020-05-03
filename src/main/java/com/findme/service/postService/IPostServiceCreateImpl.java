package com.findme.service.postService;

import com.findme.check.checkUser;
import com.findme.dao.userDAO.IUserDAORelationship;
import com.findme.exception.BadRequestException;
import com.findme.model.Post;
import com.findme.service.IServiceCRAD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class IPostServiceCreateImpl implements IPostServiceCreate {
    private IServiceCRAD<Post> postIServiceCRAD;
    private IUserDAORelationship userDAORelationship;

    @Autowired
    public IPostServiceCreateImpl(IServiceCRAD<Post> postIServiceCRAD, IUserDAORelationship userDAORelationship) {
        this.postIServiceCRAD = postIServiceCRAD;
        this.userDAORelationship = userDAORelationship;
    }

    @Override
    public void createPost(HttpSession session, Post post) {
        checkUser.userIsSessionUser(session, String.valueOf(post.getUserPosted().getId()));
        String status = userDAORelationship.getStatusRelationship(String.valueOf(post.getUserPosted()), String.valueOf(post.getUserPagePosted()));
        if (post.getUserPosted().equals(post.getUserPagePosted())) {
            postIServiceCRAD.save(post);
        } else {
            if (status.equals("FRIENDS"))
                postIServiceCRAD.save(post);
            else throw new BadRequestException("You can't write this post");
        }

    }
}
