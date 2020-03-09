package com.findme.service;

import com.findme.dao.PostDAO;
import com.findme.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@org.springframework.stereotype.Service
@Scope( proxyMode = ScopedProxyMode.TARGET_CLASS )
public class PostService implements IServiceCRAD<Post> {
    private PostDAO postDAO;

    @Autowired
    public PostService(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    @Override
    public Post save(Post post) {
        return postDAO.save(post);
    }

    @Override
    public Post findById(Long id) {
        return postDAO.findById(id);
    }

    @Override
    public void delete(Long id) {
        postDAO.delete(id);
    }

    @Override
    public void update(Post post) {
        postDAO.update(post);
    }
}
