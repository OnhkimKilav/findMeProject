package com.findme.service.postService;

import com.findme.check.checkUser;
import com.findme.dao.postDAO.IPostDAODisplay;
import com.findme.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Service
public class PostServiceDisplayImpl implements IPostServiceDisplay {
    private IPostDAODisplay iPostDAODisplay;

    @Autowired
    public PostServiceDisplayImpl(IPostDAODisplay iPostDAODisplay) {
        this.iPostDAODisplay = iPostDAODisplay;
    }

    @Override
    public ArrayList<Post> displayPost(HttpSession session, String userId) {
        checkUser.userIsSessionUser(session, userId);

        return iPostDAODisplay.displayPost(userId);
    }
}
