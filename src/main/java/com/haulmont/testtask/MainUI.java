package com.haulmont.testtask;

import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.model.Client;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private Grid clientGrid;
    private Controller controller;

    @Override
    protected void init(VaadinRequest request) {
        controller = new Controller();
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        initClientsTable();
        loadClients();
        layout.addComponent(new Label("Main UI"));
        layout.addComponent(clientGrid);
        setContent(layout);
    }

    private void initClientsTable() {
        clientGrid = new Grid();
        clientGrid.setWidth("100%");
        clientGrid.addColumn("First name", String.class);
        clientGrid.addColumn("Last Name", String.class);
        clientGrid.addColumn("Father Name", String.class);
        clientGrid.addColumn("Phone", String.class);
    }

    private void loadClients() {
        List<Client> clientList = controller.getAllClients();

        for(Client client:clientList) {
            clientGrid.addRow(
                    client.getFirstName(),
                    client.getLastName(),
                    client.getFatherName(),
                    client.getPhoneNumber()
            );
        }
    }
}