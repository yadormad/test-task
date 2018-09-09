package com.haulmont.testtask.dao.impl.hibernate;

import com.haulmont.testtask.dao.GenericDao;
import com.haulmont.testtask.model.Machinist;

import javax.persistence.EntityManager;

public class MachinistHibernateDao implements GenericDao<Long, Machinist> {
    private EntityManager manager;

    MachinistHibernateDao(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void add(Machinist entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Machinist entity) {

    }

    @Override
    public void get(Long id) {

    }

    @Override
    public void getAll() {

    }
}
