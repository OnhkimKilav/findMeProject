package com.findme.dao.userDAO;

import com.findme.model.User;
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
public class UserDAORequestFriendsImpl implements IUserDAORequestFriends {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
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

    @Override
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
}
