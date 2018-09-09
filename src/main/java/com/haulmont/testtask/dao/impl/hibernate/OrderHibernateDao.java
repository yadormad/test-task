package com.haulmont.testtask.dao.impl.hibernate;

import com.haulmont.testtask.dao.GenericDao;
import com.haulmont.testtask.model.Order;

import javax.persistence.EntityManager;

public class OrderHibernateDao implements GenericDao<Long, Order> {
    private EntityManager manager;

    OrderHibernateDao(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void add(Order entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Order entity) {

    }

    @Override
    public void get(Long id) {

    }

    @Override
    public void getAll() {

    }
}
