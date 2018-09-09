package com.haulmont.testtask.dao.impl.hibernate;

import com.haulmont.testtask.dao.GenericDao;
import com.haulmont.testtask.dao.impl.hibernate.entity.ClientTableEntity;
import com.haulmont.testtask.model.Client;

import javax.persistence.EntityManager;

public class ClientHibernateDao implements GenericDao<Long, Client> {

    private EntityManager manager;

    ClientHibernateDao(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void add(Client clientModel) {
        ClientTableEntity clientEntity = new ClientTableEntity();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Client entity) {

    }

    @Override
    public void get(Long id) {

    }

    @Override
    public void getAll() {

    }
}
