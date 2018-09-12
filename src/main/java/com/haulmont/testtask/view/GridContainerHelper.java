package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Machinist;
import com.haulmont.testtask.model.Order;
import com.vaadin.data.util.BeanItemContainer;

public class GridContainerHelper {
    private BeanItemContainer<Client> clientsContainer;
    private BeanItemContainer<Order> ordersContainer;
    private BeanItemContainer<Machinist> machinistsContainer;
    private Controller controller;

    public GridContainerHelper(Controller controller) {
        this.controller = controller;
        clientsContainer = new BeanItemContainer<>(Client.class);
        ordersContainer = new BeanItemContainer<>(Order.class);
        machinistsContainer = new BeanItemContainer<>(Machinist.class);

        clientsContainer.addAll(controller.getAllClients());
        ordersContainer.addAll(controller.getAllOrders());
        machinistsContainer.addAll(controller.getAllMachinists());
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

    public BeanItemContainer<Client> updateClientContainer() {
        clientsContainer.removeAllItems();
        clientsContainer.addAll(controller.getAllClients());
        return clientsContainer;
    }
}
