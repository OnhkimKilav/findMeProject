package com.findme.dao;

import com.findme.model.Post;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
@Scope( proxyMode = ScopedProxyMode.TARGET_CLASS )
public class PostDAO implements DAO<Post> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Post save(Post post) {
        entityManager.persist(post);
        return post;
    }

    @Override
    public Post findById(Long id) {
        return entityManager.find(Post.class, id);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public void update(Post post) {
        entityManager.merge(post);
    }
}
