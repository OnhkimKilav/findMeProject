package com.findme.dao;


public interface ICRUDDAO<T> {
    T save(T t);

    T findById(Long id);

    void delete(Long id);

    void update(T t);
}
