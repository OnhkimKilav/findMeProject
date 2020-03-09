package com.findme.dao.userDAO;

import com.findme.dao.ICRUDDAO;
import com.findme.model.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserDAOCRUDImpl implements ICRUDDAO<User> {

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
}
