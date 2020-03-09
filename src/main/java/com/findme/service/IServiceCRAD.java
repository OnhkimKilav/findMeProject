package com.findme.service;


public interface IServiceCRAD<T> {
    T save(T t) throws RuntimeException;

    T findById(Long id);

    void delete(Long id);

    void update(T t);
}
