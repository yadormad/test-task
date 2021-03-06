package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.dao.DeleteException;
import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Machinist;
import com.haulmont.testtask.model.Order;
import com.haulmont.testtask.model.OrderStatus;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.*;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.converters.DesignDateConverter;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.themes.ValoTheme;

import java.text.SimpleDateFormat;
import java.util.*;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private Grid clientGrid;
    private Grid machinistGrid;
    private Grid orderGrid;
    private GridContainerHelper gridContainerHelper;
    private Button addClientButton = new Button("Add");
    private Button editClientButton = new Button("Edit");
    private Button deleteClientButton = new Button("Delete");
    private Button addMachinistButton = new Button("Add");
    private Button editMachinistButton = new Button("Edit");
    private Button deleteMachinistButton = new Button("Delete");
    private Button addOrderButton = new Button("Add");
    private Button editOrderButton = new Button("Edit");
    private Button deleteOrderButton = new Button("Delete");
    private VerticalLayout machinistStatisticsLayout;

    @Override
    protected void init(VaadinRequest request) {
        gridContainerHelper = new GridContainerHelper();
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
        addClientButton.addStyleName("primary");
        editClientButton.addStyleName("friendly");
        deleteClientButton.addStyleName("danger");
        addClientButton.addClickListener((Button.ClickListener) clickEvent -> {
            ClientEditWindow clientEditWindow = new ClientEditWindow();
            clientEditWindow.addClient(gridContainerHelper.getClientsContainer());
            addWindow(clientEditWindow);
        });

        buttonLayout.addComponents(addClientButton, editClientButton, deleteClientButton);
        buttonLayout.setComponentAlignment(addClientButton, Alignment.MIDDLE_LEFT);
        buttonLayout.setComponentAlignment(editClientButton, Alignment.MIDDLE_LEFT);
        buttonLayout.setComponentAlignment(deleteClientButton, Alignment.MIDDLE_LEFT);
        buttonLayout.setExpandRatio(addClientButton, 0.2f);
        buttonLayout.setExpandRatio(editClientButton, 0.2f);
        buttonLayout.setExpandRatio(deleteClientButton, 0.2f);
        clientsTab.addComponents(buttonLayout, clientGrid);
        return clientsTab;
    }

    private VerticalLayout initMachinistsTab() {
        VerticalLayout machinistsTab = new VerticalLayout();
        machinistsTab.setSizeFull();

        VerticalLayout machinistTableLayout = new VerticalLayout();

        machinistStatisticsLayout = new VerticalLayout();
        machinistStatisticsLayout.setCaption("Orders");

        initMachinistsTable();
        machinistsTab.setCaption("Machinists");

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.setMargin(new MarginInfo(true,true, true, true));
        addMachinistButton.addStyleName("primary");
        editMachinistButton.addStyleName("friendly");
        deleteMachinistButton.addStyleName("danger");
        addMachinistButton.addClickListener((Button.ClickListener) clickEvent -> {
            MachinistEditWindow machinistEditWindow = new MachinistEditWindow();
            machinistEditWindow.addMachinist(gridContainerHelper.getMachinistsContainer());
            addWindow(machinistEditWindow);
        });

        buttonLayout.addComponents(addMachinistButton, editMachinistButton, deleteMachinistButton);
        buttonLayout.setComponentAlignment(addMachinistButton, Alignment.MIDDLE_LEFT);
        buttonLayout.setComponentAlignment(editMachinistButton, Alignment.MIDDLE_LEFT);
        buttonLayout.setComponentAlignment(deleteMachinistButton, Alignment.MIDDLE_LEFT);
        buttonLayout.setExpandRatio(addMachinistButton, 0.2f);
        buttonLayout.setExpandRatio(editMachinistButton, 0.2f);
        buttonLayout.setExpandRatio(deleteMachinistButton, 0.2f);

        machinistTableLayout.addComponent(machinistGrid);

        HorizontalLayout machinistInfo = new HorizontalLayout();
        machinistInfo.setSizeFull();

        machinistInfo.addComponents(machinistTableLayout, machinistStatisticsLayout);

        machinistInfo.setExpandRatio(machinistTableLayout, 0.7f);
        machinistInfo.setExpandRatio(machinistStatisticsLayout, 0.3f);
        machinistTableLayout.setMargin(new MarginInfo(false, true, false, false));
        machinistStatisticsLayout.setMargin(true);


        machinistsTab.addComponents(buttonLayout, machinistInfo);
        return machinistsTab;
    }

    private VerticalLayout initOrdersTab() {
        VerticalLayout ordersTab = new VerticalLayout();
        initOrdersTable();
        ordersTab.setCaption("Orders");

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.setMargin(new MarginInfo(true,true, true, true));
        addOrderButton.addStyleName("primary");
        editOrderButton.addStyleName("friendly");
        deleteOrderButton.addStyleName("danger");
        editOrderButton.setVisible(false);
        deleteOrderButton.setVisible(false);
        Button filterButton = new Button("Filter");
        filterButton.addClickListener((Button.ClickListener) clickEvent -> {
            OrderFilterWindow orderFilterWindow = new OrderFilterWindow(gridContainerHelper);
            orderFilterWindow.setFilter();
            addWindow(orderFilterWindow);
        });

        addOrderButton.addClickListener((Button.ClickListener) clickEvent -> {
            OrderEditWindow orderEditWindow = new OrderEditWindow(gridContainerHelper);
            orderEditWindow.addOrder(gridContainerHelper.getOrdersContainer());
            addWindow(orderEditWindow);
        });

        buttonLayout.addComponents(addOrderButton, editOrderButton, deleteOrderButton, filterButton);
        buttonLayout.setComponentAlignment(addOrderButton, Alignment.MIDDLE_LEFT);
        buttonLayout.setComponentAlignment(editOrderButton, Alignment.MIDDLE_LEFT);
        buttonLayout.setComponentAlignment(deleteOrderButton, Alignment.MIDDLE_LEFT);
        buttonLayout.setComponentAlignment(filterButton, Alignment.MIDDLE_RIGHT);
        buttonLayout.setExpandRatio(addOrderButton, 0.2f);
        buttonLayout.setExpandRatio(editOrderButton, 0.2f);
        buttonLayout.setExpandRatio(deleteOrderButton, 0.2f);

        ordersTab.addComponents(buttonLayout, orderGrid);

        return ordersTab;
    }

    private void initClientsTable() {
        clientGrid = new Grid();
        clientGrid.setSizeFull();

        editClientButton.setVisible(false);
        deleteClientButton.setVisible(false);
        editClientButton.addClickListener((Button.ClickListener) clickEvent -> {
            ClientEditWindow clientEditWindow = new ClientEditWindow();
            Client selectedClient = (Client) clientGrid.getSelectedRow();
            clientEditWindow.editClient(gridContainerHelper, selectedClient);
            addWindow(clientEditWindow);
        });
        deleteClientButton.addClickListener((Button.ClickListener) clickEvent -> {
            Client selectedClient = (Client) clientGrid.getSelectedRow();
            try {
                Controller.getInstance().deleteClient(selectedClient.getId());
                gridContainerHelper.getClientsContainer().removeItem(selectedClient);
                deleteClientButton.setComponentError(null);
            } catch (DeleteException e) {
                deleteClientButton.setComponentError(new UserError("The client has dependent orders"));
            }
        });
        clientGrid.addSelectionListener(getSelectionListener(editClientButton, deleteClientButton));

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
        machinistGrid.setSizeFull();

        editMachinistButton.setVisible(false);
        deleteMachinistButton.setVisible(false);
        editMachinistButton.addClickListener((Button.ClickListener) clickEvent -> {
            MachinistEditWindow MachinistEditWindow = new MachinistEditWindow();
            Machinist selectedMachinist = (Machinist) machinistGrid.getSelectedRow();
            MachinistEditWindow.editMachinist(gridContainerHelper, selectedMachinist);
            addWindow(MachinistEditWindow);
        });
        deleteMachinistButton.addClickListener((Button.ClickListener) clickEvent -> {
            Machinist selectedMachinist = (Machinist) machinistGrid.getSelectedRow();
            try {
                Controller.getInstance().deleteMachinist(selectedMachinist.getId());
                gridContainerHelper.getMachinistsContainer().removeItem(selectedMachinist);
                deleteMachinistButton.setComponentError(null);
            } catch (DeleteException e) {
                deleteMachinistButton.setComponentError(new UserError("The machinist has dependent orders"));
            }
        });
        machinistGrid.addSelectionListener(getSelectionListener(editMachinistButton, deleteMachinistButton));
        machinistGrid.addSelectionListener((SelectionEvent.SelectionListener) selectionEvent -> {
            if(selectionEvent.getSelected().size() == 1) {
                viewStatistics();
            } else {
                machinistStatisticsLayout.removeAllComponents();
            }
        });

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

    private void viewStatistics() {
        machinistStatisticsLayout.removeAllComponents();
        Controller.getInstance().getMachinistStat((Machinist) machinistGrid.getSelectedRow()).forEach((s, integer) -> machinistStatisticsLayout.addComponent(new Label(s + ": " + integer)));
    }

    private void initOrdersTable() {
        orderGrid = new Grid();
        orderGrid.setSizeFull();

        editOrderButton.addClickListener((Button.ClickListener) clickEvent -> {
            OrderEditWindow OrderEditWindow = new OrderEditWindow(gridContainerHelper);
            Order selectedOrder = (Order) orderGrid.getSelectedRow();
            OrderEditWindow.editOrder(selectedOrder);
            addWindow(OrderEditWindow);
        });
        deleteOrderButton.addClickListener((Button.ClickListener) clickEvent -> {
            Order selectedOrder = (Order) orderGrid.getSelectedRow();
            try {
                Controller.getInstance().deleteOrder(selectedOrder.getId());
                gridContainerHelper.getOrdersContainer().removeItem(selectedOrder);
                deleteOrderButton.setComponentError(null);
            } catch (DeleteException e) {
                deleteOrderButton.setComponentError(new UserError("The Order has dependent orders"));
            }
        });
        orderGrid.addSelectionListener(getSelectionListener(editOrderButton, deleteOrderButton));

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
        orderGrid.getColumn("startDate").setHeaderCaption("Start Date").setRenderer(new DateRenderer(new SimpleDateFormat("yyyy-MM-dd")));
        orderGrid.getColumn("endDate").setHeaderCaption("End Date").setRenderer(new DateRenderer(new SimpleDateFormat("yyyy-MM-dd")));;
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

    private SelectionEvent.SelectionListener getSelectionListener(Button editButton, Button deleteButton) {
        return (SelectionEvent.SelectionListener) selectionEvent -> {
            if(selectionEvent.getSelected().size() == 1) {
                editButton.setVisible(true);
                deleteButton.setVisible(true);
            } else {
                editButton.setVisible(false);
                deleteButton.setVisible(false);
            }
        };
    }
}