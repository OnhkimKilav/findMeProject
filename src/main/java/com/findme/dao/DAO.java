package com.findme.dao;


public interface DAO<T> {

    T save(T t);

    T findById(Long id);

    void delete(Long id);

    void update(T t);
}
