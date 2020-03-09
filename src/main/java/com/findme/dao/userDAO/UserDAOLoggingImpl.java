package com.findme.dao.userDAO;

import com.findme.model.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
@Transactional
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserDAOLoggingImpl implements IUserDAOLogging {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User checkLogIn(String email, String password) {
        Query query = entityManager.createNativeQuery("SELECT * FROM USERS WHERE EMAIL = ? AND PASSWORD = ?", User.class)
                .setParameter(1, email)
                .setParameter(2, password);
        query.executeUpdate();
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
