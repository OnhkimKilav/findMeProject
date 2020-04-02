package com.findme.dao.userDAO;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;

@Repository
@Transactional
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserDAODeleteRelationshipImpl implements IUserDAODeleteRelationship {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Date relationshipTime(String userIdFrom, String userIdTo) {
        Query query = entityManager.createNativeQuery("SELECT DATE_CREATED FROM RELATIONSHIP WHERE USER_ONE_ID = ? and USER_TWO_ID = ?")
                .setParameter(1, userIdFrom)
                .setParameter(2, userIdTo);
        query.executeUpdate();
        try {
            return (Date) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public int maxCountOutgoingRequest(String userIdFrom, String userIdTo) {
        Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM RELATIONSHIP WHERE USER_ONE_ID = ? AND STATUS = 'FRIENDS'")
                .setParameter(1, userIdFrom);
        query.executeUpdate();
        return ((Number)query.getSingleResult()).intValue();
    }

    @Override
    public int maxCountFriends(String userIdFrom, String userIdTo) {
        Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM RELATIONSHIP WHERE USER_ONE_ID = ? AND STATUS = 'REQUEST_SENDED'")
                .setParameter(1, userIdFrom);
        query.executeUpdate();
        return ((Number)query.getSingleResult()).intValue();
    }
}
