package com.findme.dao;

import com.findme.RelationshipStatus;
import com.findme.model.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;

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
        } catch (NoResultException e) {
            return null;
        }
    }

    public void addRelationship(String userIdFrom, String userIdTo) {
        Query query = entityManager.createNativeQuery("INSERT INTO RELATIONSHIP VALUES(?, ?, ?)")
                .setParameter(1, userIdFrom)
                .setParameter(2, userIdTo)
                .setParameter(3, "REQUEST_SENDED");
        query.executeUpdate();
    }

    public void updateRelationship(String userIdFrom, String userIdTo, String status) {
        Query query = entityManager.createNativeQuery("UPDATE RELATIONSHIP SET STATUS = ? WHERE USER_ONE_ID = ? AND USER_TWO_ID = ?")
                .setParameter(1, status)
                .setParameter(2, userIdFrom)
                .setParameter(3, userIdTo);
        query.executeUpdate();
    }

    public ArrayList<User> getIncomeRequests(String userId){
        Query query = entityManager.createNativeQuery("select USERS_ID, FIRST_NAME, LAST_NAME\n" +
                "from USERS\n" +
                "where USERS_ID IN (\n" +
                "  select USER_ONE_ID\n" +
                "  from RELATIONSHIP\n" +
                "  where USER_TWO_ID = ?\n" +
                "    and STATUS = 'REQUEST_SENDED'\n" +
                ")\n").setParameter(1, userId);
        query.executeUpdate();
        ArrayList<User> users = (ArrayList<User>) query.getResultList();
        return users;
    }

    public ArrayList<User> getOutcomeRequests(String userId){
        Query query = entityManager.createNativeQuery("select USERS_ID, FIRST_NAME, LAST_NAME\n" +
                "from USERS\n" +
                "where USERS_ID IN (\n" +
                "  select USER_TWO_ID\n" +
                "  from RELATIONSHIP\n" +
                "  where USER_ONE_ID = ?\n" +
                "    and STATUS = 'REQUEST_SENDED'\n" +
                ")\n").setParameter(1, userId);
        query.executeUpdate();
        ArrayList<User> users = (ArrayList<User>) query.getResultList();
        return users;
    }

    public String getStatusRelationship(String userIdFrom, String userIdTo){
        Query query = entityManager.createNativeQuery("SELECT STATUS FROM RELATIONSHIP WHERE USER_ONE_ID = ? and USER_TWO_ID = ?")
                .setParameter(1, userIdFrom)
                .setParameter(2, userIdTo);
        query.executeUpdate();
        return String.valueOf(query.getSingleResult());
    }
}
