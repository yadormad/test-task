package com.haulmont.testtask.controller;

import com.haulmont.testtask.dao.DaoFactory;
import com.haulmont.testtask.dao.DeleteException;
import com.haulmont.testtask.dao.impl.hibernate.HibernateDaoFactory;
import com.haulmont.testtask.model.*;

import java.util.List;
import java.util.Map;

public class Controller {
    private DaoFactory daoFactory;

    private OrderFilter orderFilter;

    private static volatile Controller instance;

    public static Controller getInstance() {
        Controller localInstance = instance;
        if (localInstance == null) {
            synchronized (Controller.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Controller();
                }
            }
        }
        return localInstance;
    }


    private Controller() {
        daoFactory = new HibernateDaoFactory();
    }

    public List<Client> getAllClients() {
        return daoFactory.getClientDao().getAll();
    }
    public List<Machinist> getAllMachinists() {
        return daoFactory.getMachinistDao().getAll();
    }
    public List<Order> getAllOrders() {
        if(orderFilter == null) {
            return daoFactory.getOrderDao().getAll();
        } else {
            return daoFactory.getServiceDao().getFilteredOrder(orderFilter);
        }
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

    public OrderFilter getOrderFilter() {
        return orderFilter;
    }

    public void setOrderFilter(OrderFilter orderFilter) {
        this.orderFilter = orderFilter;
    }
}
