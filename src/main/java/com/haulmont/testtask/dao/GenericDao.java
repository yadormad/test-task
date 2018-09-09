package com.haulmont.testtask.dao;

public interface GenericDao<K, O> {
    void add(O entity);
    void delete(K id);
    void update(O entity);
    void get(K id);
    void getAll();
}
