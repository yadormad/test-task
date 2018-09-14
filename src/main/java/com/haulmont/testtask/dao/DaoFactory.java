package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Machinist;
import com.haulmont.testtask.model.Order;

public interface DaoFactory {
    GenericDao<Long, Client> getClientDao();
    GenericDao<Long, Machinist> getMachinistDao();
    GenericDao<Long, Order> getOrderDao();
    OrderStatusDao getOrderStatusDao();
    ServiceDao getServiceDao();
}
