package com.findme.dao;

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
public class UserDAO implements DAO<User> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }


    @Override
    public void delete(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    public Integer userByPhone(String phone) {
        Query query = entityManager.createNativeQuery("SELECT * FROM USERS WHERE PHONE = ?").setParameter(1, phone);
        return query.executeUpdate();
    }

    public Integer userByEmail(String email) {
        Query query = entityManager.createNativeQuery("SELECT * FROM USERS WHERE EMAIL = ?").setParameter(1, email);
        return query.executeUpdate();
    }

    public User checkLogIn(String email, String password) {
        Query query = entityManager.createNativeQuery("SELECT * FROM USERS WHERE EMAIL = ? AND PASSWORD = ?", User.class)
                .setParameter(1, email)
                .setParameter(2, password);
        query.executeUpdate();
        try {
            return (User) query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
}
