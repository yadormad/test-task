package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Machinist;
import com.haulmont.testtask.model.Order;
import com.haulmont.testtask.model.OrderStatus;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.*;
import com.vaadin.shared.communication.SharedState;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.Renderer;
import com.vaadin.ui.themes.ValoTheme;
import elemental.json.JsonObject;
import elemental.json.JsonValue;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private Grid clientGrid;
    private Grid machinistGrid;
    private Grid orderGrid;
    private Controller controller;
    private GridContainerHelper gridContainerHelper;
    private Button addButton = new Button("Add");
    Button editButton = new Button("Edit");

    @Override
    protected void init(VaadinRequest request) {
        controller = new Controller();
        gridContainerHelper = new GridContainerHelper(controller);
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
        clientsTab.setCaption("Clients");

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.setMargin(new MarginInfo(true,true, true, true));
        buttonLayout.setSizeFull();
        addButton.addStyleName("primary");
        addButton.addClickListener((Button.ClickListener) clickEvent -> {
            ClientEditWindow clientEditWindow = new ClientEditWindow(controller);
            clientEditWindow.addClient(gridContainerHelper.getClientsContainer());
            addWindow(clientEditWindow);
        });

        buttonLayout.addComponents(addButton, editButton);
        clientsTab.addComponents(buttonLayout, clientGrid);
        return clientsTab;
    }

    private VerticalLayout initMachinistsTab() {
        VerticalLayout machinistsTab = new VerticalLayout();
        initMachinistsTable();
        machinistsTab.addComponent(machinistGrid);
        machinistsTab.setCaption("Machinists");
        return machinistsTab;
    }

    private VerticalLayout initOrdersTab() {
        VerticalLayout ordersTab = new VerticalLayout();
        initOrdersTable();
        ordersTab.addComponent(orderGrid);
        ordersTab.setCaption("Orders");
        return ordersTab;
    }

    private void initClientsTable() {
        clientGrid = new Grid();

        editButton.addStyleName("friendly");
        editButton.addClickListener((Button.ClickListener) clickEvent -> {
            ClientEditWindow clientEditWindow = new ClientEditWindow(controller);
            Client selectedClient = (Client) clientGrid.getSelectedRow();
            clientEditWindow.editClient(selectedClient);
            clientGrid.setContainerDataSource(gridContainerHelper.updateClientContainer());
            addWindow(clientEditWindow);
        });

        clientGrid.addSelectionListener((SelectionEvent.SelectionListener) selectionEvent -> {

            if(selectionEvent.getSelected().size() == 1) {
                editButton.setDisableOnClick(false);
            } else {
                editButton.setDisableOnClick(true);
            }
        });
        clientGrid.setContainerDataSource(gridContainerHelper.getClientsContainer());
        clientGrid.setWidth("100%");
        clientGrid.removeColumn("id");
        clientGrid.removeColumn("clientOrders");
        clientGrid.getColumn("firstName").setHeaderCaption("First Name");
        clientGrid.getColumn("lastName").setHeaderCaption("Last Name");
        clientGrid.getColumn("fatherName").setHeaderCaption("Father Name");
        clientGrid.getColumn("phoneNumber").setHeaderCaption("Phone Number");
        clientGrid.setColumnOrder("firstName", "lastName", "fatherName", "phoneNumber");
    }
    
    private void initMachinistsTable() {
        machinistGrid = new Grid();
        machinistGrid.setContainerDataSource(gridContainerHelper.getMachinistsContainer());
        machinistGrid.setWidth("100%");
        machinistGrid.removeColumn("id");
        machinistGrid.removeColumn("machinistOrders");
        machinistGrid.getColumn("firstName").setHeaderCaption("First Name");
        machinistGrid.getColumn("lastName").setHeaderCaption("Last Name");
        machinistGrid.getColumn("fatherName").setHeaderCaption("Father Name");
        machinistGrid.getColumn("valueCost").setHeaderCaption("Hour Cost");
        machinistGrid.setColumnOrder("firstName", "lastName", "fatherName", "valueCost");
    }
    
    private void initOrdersTable() {
        orderGrid = new Grid();
        orderGrid.setContainerDataSource(gridContainerHelper.getOrdersContainer());
        orderGrid.setWidth("100%");
        orderGrid.removeColumn("id");
        orderGrid.getColumn("description").setHeaderCaption("Description");
        orderGrid.getColumn("client").setHeaderCaption("Client");
        orderGrid.getColumn("client").setConverter(new Converter<String, Client>() {
            @Override
            public Client convertToModel(String s, Class<? extends Client> aClass, Locale locale) throws ConversionException {
                throw new ConversionException("not supported");
            }

            @Override
            public String convertToPresentation(Client client, Class<? extends String> aClass, Locale locale) throws ConversionException {
                return client.getLastName();
            }

            @Override
            public Class<Client> getModelType() {
                return Client.class;
            }

            @Override
            public Class<String> getPresentationType() {
                return String.class;
            }
        });
        orderGrid.getColumn("machinist").setHeaderCaption("Machinist");
        orderGrid.getColumn("machinist").setConverter(new Converter<String, Machinist>() {
            @Override
            public Machinist convertToModel(String s, Class<? extends Machinist> aClass, Locale locale) throws ConversionException {
                throw new ConversionException("not supported");
            }

            @Override
            public String convertToPresentation(Machinist machinist, Class<? extends String> aClass, Locale locale) throws ConversionException {
                return machinist.getLastName();
            }

            @Override
            public Class<Machinist> getModelType() {
                return Machinist.class;
            }

            @Override
            public Class<String> getPresentationType() {
                return String.class;
            }
        });
        orderGrid.getColumn("startDate").setHeaderCaption("Start Date");
        orderGrid.getColumn("endDate").setHeaderCaption("End Date");
        orderGrid.getColumn("cost").setHeaderCaption("Cost");
        orderGrid.getColumn("status").setHeaderCaption("Status");
        orderGrid.getColumn("status").setConverter(new Converter<String, OrderStatus>() {
            @Override
            public OrderStatus convertToModel(String s, Class<? extends OrderStatus> aClass, Locale locale) throws ConversionException {
                throw new ConversionException("not supported");
            }

            @Override
            public String convertToPresentation(OrderStatus orderStatus, Class<? extends String> aClass, Locale locale) throws ConversionException {
                return orderStatus.getStatus();
            }

            @Override
            public Class<OrderStatus> getModelType() {
                return OrderStatus.class;
            }

            @Override
            public Class<String> getPresentationType() {
                return String.class;
            }
        });
        orderGrid.setColumnOrder("description", "client", "machinist", "startDate", "endDate", "cost", "status");
    }
}