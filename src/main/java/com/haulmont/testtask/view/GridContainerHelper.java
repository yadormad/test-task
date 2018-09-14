package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Machinist;
import com.haulmont.testtask.model.Order;
import com.haulmont.testtask.model.OrderStatus;
import com.vaadin.data.util.BeanItemContainer;

public class GridContainerHelper {
    private BeanItemContainer<Client> clientsContainer;
    private BeanItemContainer<Order> ordersContainer;
    private BeanItemContainer<Machinist> machinistsContainer;
    private BeanItemContainer<OrderStatus> orderStatusContainer;

    public GridContainerHelper() {
        clientsContainer = new BeanItemContainer<>(Client.class);
        ordersContainer = new BeanItemContainer<>(Order.class);
        machinistsContainer = new BeanItemContainer<>(Machinist.class);
        orderStatusContainer = new BeanItemContainer<>(OrderStatus.class);

        clientsContainer.addAll(Controller.getInstance().getAllClients());
        ordersContainer.addAll(Controller.getInstance().getAllOrders());
        machinistsContainer.addAll(Controller.getInstance().getAllMachinists());
        orderStatusContainer.addAll(Controller.getInstance().getAllOrderStatuses());
    }

    public BeanItemContainer<Client> getClientsContainer() {
        return clientsContainer;
    }

    public BeanItemContainer<Order> getOrdersContainer() {
        return ordersContainer;
    }

    public BeanItemContainer<Machinist> getMachinistsContainer() {
        return machinistsContainer;
    }

    public void updateClientContainer() {
        clientsContainer.removeAllItems();
        clientsContainer.addAll(Controller.getInstance().getAllClients());
    }

    public void updateMachinistContainer() {
        machinistsContainer.removeAllItems();
        machinistsContainer.addAll(Controller.getInstance().getAllMachinists());
    }

    public void updateOrderContainer() {
        ordersContainer.removeAllItems();
        ordersContainer.addAll(Controller.getInstance().getAllOrders());
    }

    public BeanItemContainer<OrderStatus> getOrderStatusContainer() {
        return orderStatusContainer;
    }
}
