package com.findme.dao.postDAO;

import com.findme.model.Post;
import java.util.ArrayList;

public interface IPostDAODisplay {
    ArrayList<Post> displayPost(String userId);
}
