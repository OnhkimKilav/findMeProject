package com.findme.service;

import com.findme.dao.DAO;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class Service <T> {
    private DAO dao;

    @Autowired
    public Service(DAO dao) {
        this.dao = dao;
    }

    public T save(T t) {
        return (T) dao.save(t);
    }

    public void delete(T t, Long id) {
        dao.delete((Class) t, id);
    }


    public void update(T t) {
        dao.update(t);
    }

    public T findById(T t, Long id) {
        return (T) dao.findById((Class) t, id);
    }
}
