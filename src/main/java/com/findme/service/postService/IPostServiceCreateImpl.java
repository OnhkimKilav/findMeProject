package com.findme.service.postService;

import com.findme.RelationshipStatus;
import com.findme.check.checkUser;
import com.findme.dao.ICRUDDAO;
import com.findme.dao.userDAO.IUserDAORelationship;
import com.findme.exception.BadRequestException;
import com.findme.model.Post;
import com.findme.model.User;
import com.findme.service.IServiceCRAD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class IPostServiceCreateImpl implements IPostServiceCreate {
    private IServiceCRAD<Post> postIServiceCRAD;
    private ICRUDDAO<User> userICRUDDAO;
    private IUserDAORelationship userDAORelationship;

    @Autowired
    public IPostServiceCreateImpl(IServiceCRAD<Post> postIServiceCRAD, ICRUDDAO<User> userICRUDDAO, IUserDAORelationship userDAORelationship) {
        this.postIServiceCRAD = postIServiceCRAD;
        this.userICRUDDAO = userICRUDDAO;
        this.userDAORelationship = userDAORelationship;
    }

    //http://localhost:8080/createPost/message/location/3%2043%20103/105/61
    @Override
    public void createPost(HttpSession session, String message, String location, String usersTagged, Long userPosted,
                           Long userPagePosted) {
        Post post = new Post(message, new Date(), location, usersTagged(usersTagged), userICRUDDAO.findById(userPosted),
                userICRUDDAO.findById(userPagePosted));

        checkUser.userIsSessionUser(session, String.valueOf(post.getUserPosted().getId()));

        if (post.getUserPosted().getId().equals(post.getUserPagePosted().getId())) {
            postIServiceCRAD.save(post);
        } else {
            String status = userDAORelationship.getStatusRelationship(String.valueOf(post.getUserPosted().getId()),
                    String.valueOf(post.getUserPagePosted().getId()));

            if (status.equals(RelationshipStatus.FRIENDS.name()))
                postIServiceCRAD.save(post);
            else throw new BadRequestException("You can't write this post");
        }

    }

    private Set<User> usersTagged(String usersTagged) {
        Set<User> users = new HashSet<>();
        long[] numbers = Arrays.stream(usersTagged.split("\\s"))
                .mapToLong(Long::parseLong)
                .toArray();

        for (long userId : numbers) {
            users.add(userICRUDDAO.findById(userId));
        }
        return users;
    }
}
