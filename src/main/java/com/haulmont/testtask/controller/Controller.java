package com.haulmont.testtask.controller;

import com.haulmont.testtask.dao.DaoFactory;
import com.haulmont.testtask.dao.impl.hibernate.HibernateDaoFactory;
import com.haulmont.testtask.model.Client;

import java.util.List;

public class Controller {
    private DaoFactory daoFactory;

    public Controller() {
        daoFactory = new HibernateDaoFactory();
    }

    public List<Client> getAllClients() {
        return daoFactory.getClientDao().getAll();
    }
}
