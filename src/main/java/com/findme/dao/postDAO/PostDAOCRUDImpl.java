package com.findme.dao.postDAO;

import com.findme.dao.ICRUDDAO;
import com.findme.model.Post;
import com.findme.model.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Set;

@Repository
@Transactional
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PostDAOCRUDImpl implements ICRUDDAO<Post> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Post save(Post post) {
        Query query = entityManager.createNativeQuery("INSERT INTO POST (POST_ID ,MESSAGE, DATE_POSTED, LOCATION," +
                "USERS_TAGGED, USER_POSTED, USER_PAGE_POSTED) VALUES (?, ?, ?, ?, ?, ?, ?)", Post.class)
                .setParameter(1, post.getId())
                .setParameter(2, post.getMessage())
                .setParameter(3, post.getDatePosted())
                .setParameter(4, post.getLocation())
                .setParameter(5, usersTagged(post.getUsersTagged()))
                .setParameter(6, post.getUserPosted().getId())
                .setParameter(7, post.getUserPagePosted().getId());
        query.executeUpdate();
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

    private String usersTagged(Set<User> usersTagged) {
        String userId = "";
        for (User user : usersTagged) {
            userId += String.valueOf(user.getId()) + " ";
        }
        return userId;
    }
}


