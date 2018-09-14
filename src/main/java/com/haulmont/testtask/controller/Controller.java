package com.haulmont.testtask.controller;

import com.haulmont.testtask.dao.DaoFactory;
import com.haulmont.testtask.dao.DeleteException;
import com.haulmont.testtask.dao.impl.hibernate.HibernateDaoFactory;
import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Machinist;
import com.haulmont.testtask.model.Order;
import com.haulmont.testtask.model.OrderStatus;

import java.util.List;
import java.util.Map;

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
    public List<OrderStatus> getAllOrderStatuses() {
        return daoFactory.getOrderStatusDao().getAll();
    }

    public Client getClient(Long clientId) {
        return daoFactory.getClientDao().get(clientId);
    }

    public Client addClient(Client client) {
        return daoFactory.getClientDao().add(client);
    }

    public void updateClient(Client client) {
        daoFactory.getClientDao().update(client);
    }

    public void deleteClient(Long clientId) throws DeleteException {
        daoFactory.getClientDao().delete(clientId);
    }

    public Machinist addMachinist(Machinist machinist) {
        return daoFactory.getMachinistDao().add(machinist);
    }

    public void updateMachinist(Machinist machinist) {
        daoFactory.getMachinistDao().update(machinist);
    }

    public void deleteMachinist(Long machinistId) throws DeleteException {
        daoFactory.getMachinistDao().delete(machinistId);
    }


    public Order addOrder(Order order) {
        return daoFactory.getOrderDao().add(order);
    }

    public void updateOrder(Order order) {
        daoFactory.getOrderDao().update(order);
    }

    public void deleteOrder(Long orderId) throws DeleteException {
        daoFactory.getOrderDao().delete(orderId);
    }

    public Map<String, Long> getMachinistStat(Machinist machinist) {
        return daoFactory.getServiceDao().getMachinistOrderStatistic(machinist);
    }
}
