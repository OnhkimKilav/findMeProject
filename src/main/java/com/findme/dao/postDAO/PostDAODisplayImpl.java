package com.findme.dao.postDAO;

import com.findme.model.Post;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PostDAODisplayImpl implements IPostDAODisplay {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ArrayList<Post> displayPost(String userId) {
        Query query = entityManager.createNativeQuery("SELECT MESSAGE, LOCATION, USERS_TAGGED, DATE_POSTED FROM POST " +
                "WHERE USER_POSTED = ?")
                .setParameter(1, userId);
        query.executeUpdate();
        ArrayList<Post> posts = (ArrayList<Post>) query.getResultList();
        return posts;
    }
}
