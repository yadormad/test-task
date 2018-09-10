package com.haulmont.testtask.controller;

import com.haulmont.testtask.dao.DaoFactory;
import com.haulmont.testtask.dao.impl.hibernate.HibernateDaoFactory;
import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Machinist;
import com.haulmont.testtask.model.Order;

import java.util.List;

public class Controller {
    private DaoFactory daoFactory;

    public Controller() {
        daoFactory = new HibernateDaoFactory();
    }

    public List<Client> getAllClients() {
        return daoFactory.getClientDao().getAll();
    }
    public List<Machinist> getAllMachinists() {
        return daoFactory.getMachinistDao().getAll();
    }
    public List<Order> getAllOrders() {
        return daoFactory.getOrderDao().getAll();
    }
}
