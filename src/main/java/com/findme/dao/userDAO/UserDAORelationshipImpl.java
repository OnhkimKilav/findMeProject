package com.findme.dao.userDAO;

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
public class UserDAORelationshipImpl implements IUserDAORelationship {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addRelationship(String userIdFrom, String userIdTo) {
        Query query = entityManager.createNativeQuery("INSERT INTO RELATIONSHIP VALUES(?, ?, ?)")
                .setParameter(1, userIdFrom)
                .setParameter(2, userIdTo)
                .setParameter(3, "REQUEST_SENDED");
        query.executeUpdate();
    }

    @Override
    public void updateRelationship(String userIdFrom, String userIdTo, String status) {
        Query query = entityManager.createNativeQuery("UPDATE RELATIONSHIP SET STATUS = ? WHERE USER_ONE_ID = ? AND USER_TWO_ID = ?")
                .setParameter(1, status)
                .setParameter(2, userIdFrom)
                .setParameter(3, userIdTo);
        query.executeUpdate();
    }

    @Override
    public String getStatusRelationship(String userIdFrom, String userIdTo){
        Query query = entityManager.createNativeQuery("SELECT STATUS FROM RELATIONSHIP WHERE USER_ONE_ID = ? and USER_TWO_ID = ?")
                .setParameter(1, userIdFrom)
                .setParameter(2, userIdTo);
        query.executeUpdate();
        try {
            return String.valueOf(query.getSingleResult());
        }catch (NoResultException e){
            return null;
        }
    }
}
