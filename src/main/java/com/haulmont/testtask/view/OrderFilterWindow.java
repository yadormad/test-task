package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.model.*;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

public class OrderFilterWindow extends Window{

    private TextField descriptionField;
    private ComboBox clientSelect;
    private ComboBox statusSelect;
    private GridContainerHelper gridContainerHelper;

    private Button saveButton;
    private Button deleteButton;
    private Button cancelButton;


    public OrderFilterWindow(GridContainerHelper gridContainerHelper) {
        super();
        this.gridContainerHelper = gridContainerHelper;
        setWidth("50%");
        setHeight("50%");
        setModal(true);
        VerticalLayout fieldLayout = initFields();
        fieldLayout.setMargin(new MarginInfo(true));
        HorizontalLayout buttonsLayout = initButtons();
        fieldLayout.addComponent(buttonsLayout);
        fieldLayout.setExpandRatio(buttonsLayout, 0.1f);
        fieldLayout.setSizeFull();
        center();
        cancelButton.addClickListener((Button.ClickListener) clickEvent -> {
            this.close();
        });
        deleteButton.addClickListener((Button.ClickListener) clickEvent -> {
            Controller.getInstance().setOrderFilter(null);
            gridContainerHelper.updateOrderContainer();
            this.close();
        });
        setContent(fieldLayout);
    }

    private VerticalLayout initFields() {
        VerticalLayout fieldsLayout = new VerticalLayout();
        descriptionField = new TextField("Description");
        clientSelect = new ComboBox("Client");
        statusSelect = new ComboBox("Status");

        clientSelect.setTextInputAllowed(false);
        statusSelect.setTextInputAllowed(false);

        clientSelect.setContainerDataSource(gridContainerHelper.getClientsContainer());
        for (Client client: gridContainerHelper.getClientsContainer().getItemIds()) {
            clientSelect.setItemCaption(client, client.getFirstName() + " " + client.getLastName());
        }

        statusSelect.setContainerDataSource(gridContainerHelper.getOrderStatusContainer());
        for(OrderStatus orderStatus: gridContainerHelper.getOrderStatusContainer().getItemIds()) {
            statusSelect.setItemCaption(orderStatus, orderStatus.getStatus());
        }

        fieldsLayout.addComponents(
                descriptionField,
                clientSelect,
                statusSelect);

        fieldsLayout.setComponentAlignment(descriptionField, Alignment.TOP_CENTER);
        fieldsLayout.setComponentAlignment(clientSelect, Alignment.TOP_CENTER);
        fieldsLayout.setComponentAlignment(statusSelect, Alignment.TOP_CENTER);

        fieldsLayout.setExpandRatio(descriptionField, 0.1f);
        fieldsLayout.setExpandRatio(clientSelect, 0.1f);
        fieldsLayout.setExpandRatio(statusSelect, 0.1f);

        return fieldsLayout;
    }

    private Client getContainerClient(Client client) {
        if(client != null) {
            for (Client containerClient : gridContainerHelper.getClientsContainer().getItemIds()) {
                if (containerClient.getId().equals(client.getId())) {
                    return containerClient;
                }
            }
        }
        return null;
    }

    private OrderStatus getContainerOrderStatus(OrderStatus status) {
        if(status != null) {
            for (OrderStatus containerOrderStatus : gridContainerHelper.getOrderStatusContainer().getItemIds()) {
                if (containerOrderStatus.getId().equals(status.getId())) {
                    return containerOrderStatus;
                }
            }
        }
        return null;
    }

    public void setFilter() {
        OrderFilter orderFilter = Controller.getInstance().getOrderFilter();
        if(orderFilter != null) {
            deleteButton.setVisible(true);
            descriptionField.setValue(orderFilter.getDescription());
            clientSelect.select(getContainerClient(orderFilter.getClient()));
            statusSelect.select(getContainerOrderStatus(orderFilter.getOrderStatus()));
        } else {
            deleteButton.setVisible(false);
        }
        this.saveButton.addClickListener((Button.ClickListener) clickEvent -> {
            if(descriptionField.isEmpty() && clientSelect.isEmpty() && statusSelect.isEmpty()) {
                Controller.getInstance().setOrderFilter(null);
            } else {
                Controller.getInstance().setOrderFilter(new OrderFilter(descriptionField.getValue(),
                        (Client) clientSelect.getValue(),
                        (OrderStatus) statusSelect.getValue()));
            }
            gridContainerHelper.updateOrderContainer();
            this.close();
        });
    }

    private HorizontalLayout initButtons() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        this.saveButton = new Button("Save");
        this.saveButton.setStyleName("friendly");
        this.deleteButton = new Button("Delete");
        this.deleteButton.setStyleName("danger");
        this.cancelButton = new Button("Cancel");
        buttonsLayout.addComponents(saveButton, deleteButton, cancelButton);
        buttonsLayout.setSizeFull();
        buttonsLayout.setComponentAlignment(saveButton, Alignment.MIDDLE_LEFT);
        buttonsLayout.setComponentAlignment(deleteButton, Alignment.MIDDLE_LEFT);
        buttonsLayout.setComponentAlignment(cancelButton, Alignment.MIDDLE_RIGHT);
        buttonsLayout.setExpandRatio(saveButton, 0.1f);
        buttonsLayout.setExpandRatio(deleteButton, 0.1f);
        buttonsLayout.setExpandRatio(cancelButton, 0.1f);
        return buttonsLayout;
    }

}
