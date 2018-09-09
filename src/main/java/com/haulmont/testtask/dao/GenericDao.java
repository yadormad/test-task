package com.haulmont.testtask.dao;

import java.util.List;

public interface GenericDao<K, O> {
    O add(O entity);
    void delete(K id) throws DeleteException;
    O get(K id);
    List<O> getAll();
}
