package com.findme.dao.userDAO;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
@Transactional
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserDAOGetUserImpl implements IUserDAOGetUser {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Integer userByPhone(String phone) {
        Query query = entityManager.createNativeQuery("SELECT * FROM USERS WHERE PHONE = ?").setParameter(1, phone);
        return query.executeUpdate();
    }

    @Override
    public Integer userByEmail(String email) {
        Query query = entityManager.createNativeQuery("SELECT * FROM USERS WHERE EMAIL = ?").setParameter(1, email);
        return query.executeUpdate();
    }
}
