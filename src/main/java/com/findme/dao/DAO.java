package com.findme.dao;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class DAO<T> {

    @PersistenceContext
    private EntityManager entityManager;

    public T save(T t) {
        entityManager.persist(t);
        return t;
    }

    public T findById(Class<T> t, Long id) {
        return entityManager.find(t, id);
    }

    public void delete(Class<T> t, Long id) {
        entityManager.remove(findById(t, id));
    }

    public void update(T t) {
        entityManager.merge(t);
    }
}
