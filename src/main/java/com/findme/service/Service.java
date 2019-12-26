package com.findme.service;


public interface Service<T> {
    T save(T t);

    T findById(Long id);

    void delete(Long id);

    void update(T t);
}
