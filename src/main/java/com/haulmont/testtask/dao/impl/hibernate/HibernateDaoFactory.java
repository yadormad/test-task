package com.haulmont.testtask.dao.impl.hibernate;

import com.haulmont.testtask.dao.DaoFactory;
import com.haulmont.testtask.dao.GenericDao;
import com.haulmont.testtask.dao.OrderStatusDao;
import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Machinist;
import com.haulmont.testtask.model.Order;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class HibernateDaoFactory implements DaoFactory {

    private final EntityManager manager = Persistence
            .createEntityManagerFactory("workshopPersistenceUnit")
            .createEntityManager();
    private final GenericDao<Long, Client> clientDao = new ClientHibernateDao(manager);
    private final GenericDao<Long, Machinist> machinistDao = new MachinistHibernateDao(manager);
    private final GenericDao<Long, Order> orderDao = new OrderHibernateDao(manager);
    private final OrderStatusDao orderStatusDao = new OrderStatusHibernateDao(manager);

    @Override
    public GenericDao<Long, Client> getClientDao() {
        return clientDao;
    }

    @Override
    public GenericDao<Long, Machinist> getMachinistDao() {
        return machinistDao;
    }

    @Override
    public GenericDao<Long, Order> getOrderDao() {
        return orderDao;
    }

    @Override
    public OrderStatusDao getOrderStatusDao() {
        return orderStatusDao;
    }
}
