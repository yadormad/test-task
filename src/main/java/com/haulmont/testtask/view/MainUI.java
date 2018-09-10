package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Machinist;
import com.haulmont.testtask.model.Order;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Date;
import java.util.List;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private Grid clientGrid;
    private Grid machinistGrid;
    private Grid orderGrid;
    private Controller controller;

    @Override
    protected void init(VaadinRequest request) {
        controller = new Controller();
        TabSheet tabSheet = new TabSheet();

        VerticalLayout clientsTab = initClientsTab();

        VerticalLayout machinistsTab = initMachinistsTab();

        VerticalLayout ordersTab = initOrdersTab();

        tabSheet.addTab(clientsTab);
        tabSheet.addTab(machinistsTab);
        tabSheet.addTab(ordersTab);

        setContent(tabSheet);
    }

    private VerticalLayout initClientsTab() {
        VerticalLayout clientsTab = new VerticalLayout();
        initClientsTable();
        loadClients();
        clientsTab.addComponent(clientGrid);
        clientsTab.setCaption("Clients");
        return clientsTab;
    }

    private VerticalLayout initMachinistsTab() {
        VerticalLayout machinistsTab = new VerticalLayout();
        initMachinistsTable();
        loadMachinists();
        machinistsTab.addComponent(machinistGrid);
        machinistsTab.setCaption("Machinists");
        return machinistsTab;
    }

    private VerticalLayout initOrdersTab() {
        VerticalLayout ordersTab = new VerticalLayout();
        initOrdersTable();
        loadOrders();
        ordersTab.addComponent(orderGrid);
        ordersTab.setCaption("Orders");
        return ordersTab;
    }

    private void initClientsTable() {
        clientGrid = new Grid();
        clientGrid.setWidth("100%");
        clientGrid.addColumn("id", Long.class).setHidden(true);
        clientGrid.addColumn("firstName", String.class).setHeaderCaption("First Name");
        clientGrid.addColumn("lastName", String.class).setHeaderCaption("Last Name");
        clientGrid.addColumn("fatherName", String.class).setHeaderCaption("Father Name");
        clientGrid.addColumn("phoneNumber", String.class).setHeaderCaption("Phone Number");
    }

    private void loadClients() {
        List<Client> clientList = controller.getAllClients();

        for(Client client:clientList) {
            clientGrid.addRow(
                    client.getId(),
                    client.getFirstName(),
                    client.getLastName(),
                    client.getFatherName(),
                    client.getPhoneNumber()
            );
        }
    }
    
    private void initMachinistsTable() {
        machinistGrid = new Grid();
        machinistGrid.setWidth("100%");
        machinistGrid.addColumn("id", Long.class).setHidden(true);
        machinistGrid.addColumn("firstName", String.class).setHeaderCaption("First Name");
        machinistGrid.addColumn("lastName", String.class).setHeaderCaption("Last Name");
        machinistGrid.addColumn("fatherName", String.class).setHeaderCaption("Father Name");
        machinistGrid.addColumn("valueCost", Double.class).setHeaderCaption("Hour Cost");
    }
    
    private void loadMachinists() {
        List<Machinist> machinistList = controller.getAllMachinists();

        for(Machinist machinist:machinistList) {
            machinistGrid.addRow(
                    machinist.getId(),
                    machinist.getFirstName(),
                    machinist.getLastName(),
                    machinist.getFatherName(),
                    machinist.getValueCost()
            );
        }
    }
    
    private void initOrdersTable() {
        orderGrid = new Grid();
        orderGrid.setWidth("100%");
        machinistGrid.addColumn("id", Long.class).setHidden(true);
        orderGrid.addColumn("description", String.class).setHeaderCaption("Description");
        orderGrid.addColumn("clientName", String.class).setHeaderCaption("Client");
        orderGrid.addColumn("machinistName", String.class).setHeaderCaption("Machinist");
        orderGrid.addColumn("startDate", Date.class).setHeaderCaption("Start Date");
        orderGrid.addColumn("endDate", Date.class).setHeaderCaption("End Date");
        orderGrid.addColumn("cost", Double.class).setHeaderCaption("Cost");
        orderGrid.addColumn("status", String.class).setHeaderCaption("Status");
    }
    
    private void loadOrders() {
        List<Order> orderList = controller.getAllOrders();

        for(Order order:orderList) {
            orderGrid.addRow(
                    order.getId(),
                    order.getDescription(),
                    order.getClient().getLastName(),
                    order.getMachinist().getLastName(),
                    order.getStartDate(),
                    order.getEndDate(),
                    order.getCost(),
                    order.getStatus().getStatus()
            );
        }
    }
}