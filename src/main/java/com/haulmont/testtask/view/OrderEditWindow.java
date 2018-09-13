package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Machinist;
import com.haulmont.testtask.model.Order;
import com.haulmont.testtask.model.OrderStatus;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

public class OrderEditWindow extends Window{
    private TextField descriptionField;
    private ComboBox clientSelect;
    private ComboBox machinistSelect;
    private TextField costField;
    private DateField startDateField;
    private DateField endDateField;
    private ComboBox statusSelect;
    private GridContainerHelper gridContainerHelper;

    private Button saveButton;
    private Button cancelButton;

    private Controller controller;


    public OrderEditWindow(Controller controller, GridContainerHelper gridContainerHelper) {
        super();
        this.controller = controller;
        this.gridContainerHelper = gridContainerHelper;
        setWidth("50%");
        setHeight("100%");
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
        setContent(fieldLayout);
    }

    private VerticalLayout initFields() {
        VerticalLayout fieldsLayout = new VerticalLayout();
        descriptionField = new TextField("Description");
        clientSelect = new ComboBox("Client");
        machinistSelect = new ComboBox("Machinist");
        costField = new TextField("Cost");
        startDateField = new DateField("Start Date");
        endDateField = new DateField("End Date");
        statusSelect = new ComboBox("Status");

        clientSelect.setTextInputAllowed(false);
        machinistSelect.setTextInputAllowed(false);
        statusSelect.setTextInputAllowed(false);

        clientSelect.setNullSelectionAllowed(false);
        machinistSelect.setNullSelectionAllowed(false);
        statusSelect.setNullSelectionAllowed(false);

        clientSelect.setContainerDataSource(gridContainerHelper.getClientsContainer());
        for (Client client: gridContainerHelper.getClientsContainer().getItemIds()) {
            clientSelect.setItemCaption(client, client.getFirstName() + " " + client.getLastName());
        }

        machinistSelect.setContainerDataSource(gridContainerHelper.getMachinistsContainer());
        for(Machinist machinist: gridContainerHelper.getMachinistsContainer().getItemIds()) {
            machinistSelect.setItemCaption(machinist, machinist.getFirstName() + " " + machinist.getLastName());
        }

        statusSelect.setContainerDataSource(gridContainerHelper.getOrderStatusContainer());
        for(OrderStatus orderStatus: gridContainerHelper.getOrderStatusContainer().getItemIds()) {
            statusSelect.setItemCaption(orderStatus, orderStatus.getStatus());
        }

        descriptionField.setRequired(true);
        clientSelect.setRequired(true);
        machinistSelect.setRequired(true);
        costField.setRequired(true);
        startDateField.setRequired(true);
        endDateField.setRequired(true);
        statusSelect.setRequired(true);
        fieldsLayout.addComponents(
                descriptionField,
                clientSelect,
                machinistSelect,
                costField,
                startDateField,
                endDateField,
                statusSelect);

        fieldsLayout.setComponentAlignment(descriptionField, Alignment.TOP_CENTER);
        fieldsLayout.setComponentAlignment(clientSelect, Alignment.TOP_CENTER);
        fieldsLayout.setComponentAlignment(machinistSelect, Alignment.TOP_CENTER);
        fieldsLayout.setComponentAlignment(costField, Alignment.TOP_CENTER);
        fieldsLayout.setComponentAlignment(startDateField, Alignment.TOP_CENTER);
        fieldsLayout.setComponentAlignment(endDateField, Alignment.TOP_CENTER);
        fieldsLayout.setComponentAlignment(statusSelect, Alignment.TOP_CENTER);

        fieldsLayout.setExpandRatio(descriptionField, 0.1f);
        fieldsLayout.setExpandRatio(clientSelect, 0.1f);
        fieldsLayout.setExpandRatio(machinistSelect, 0.1f);
        fieldsLayout.setExpandRatio(costField, 0.1f);
        fieldsLayout.setExpandRatio(startDateField, 0.1f);
        fieldsLayout.setExpandRatio(endDateField, 0.1f);
        fieldsLayout.setExpandRatio(statusSelect, 0.1f);

        return fieldsLayout;
    }

    public void addOrder(BeanItemContainer<Order> orderContainer) {
        this.saveButton.addClickListener((Button.ClickListener) clickEvent -> {
            String errorMsg = validateInput();
            if(errorMsg != null) {
                saveButton.setComponentError(new UserError(errorMsg));
            } else {
                Order addedOrder = new Order(descriptionField.getValue(), startDateField.getValue(), endDateField.getValue(), new Double(costField.getValue()));
                addedOrder.setMachinist((Machinist) machinistSelect.getValue());
                addedOrder.setClient((Client) clientSelect.getValue());
                addedOrder.setStatus((OrderStatus) statusSelect.getValue());
                orderContainer.addItem(controller.addOrder(addedOrder));
                this.close();
            }
        });
    }

    private Client getContainerClient(Client client) {
        for(Client containerClient: gridContainerHelper.getClientsContainer().getItemIds()) {
            if(containerClient.getId().equals(client.getId())) {
                return containerClient;
            }
        }
        return null;
    }

    private Machinist getContainerMachinist(Machinist machinist) {
        for(Machinist containerMachinist: gridContainerHelper.getMachinistsContainer().getItemIds()) {
            if(containerMachinist.getId().equals(machinist.getId())) {
                return containerMachinist;
            }
        }
        return null;
    }

    private OrderStatus getContainerOrderStatus(OrderStatus status) {
        for(OrderStatus containerOrderStatus: gridContainerHelper.getOrderStatusContainer().getItemIds()) {
            if(containerOrderStatus.getId().equals(status.getId())) {
                return containerOrderStatus;
            }
        }
        return null;
    }

    public void editOrder(Order order) {
        descriptionField.setValue(order.getDescription());

        clientSelect.select(getContainerClient(order.getClient()));
        machinistSelect.select(getContainerMachinist(order.getMachinist()));

        costField.setValue(String.valueOf(order.getCost()));
        startDateField.setValue(order.getStartDate());
        endDateField.setValue(order.getEndDate());
        statusSelect.select(getContainerOrderStatus(order.getStatus()));
        this.saveButton.addClickListener((Button.ClickListener) clickEvent -> {
            String errorMsg = validateInput();
            if(errorMsg != null) {
                saveButton.setComponentError(new UserError(errorMsg));
            } else {
                order.setDescription(descriptionField.getValue());
                order.setStartDate(startDateField.getValue());
                order.setEndDate(endDateField.getValue());
                order.setCost(Double.valueOf(costField.getValue()));
                order.setMachinist((Machinist) machinistSelect.getValue());
                order.setClient((Client) clientSelect.getValue());
                order.setStatus((OrderStatus) statusSelect.getValue());
                controller.updateOrder(order);
                gridContainerHelper.updateOrderContainer();
                this.close();
            }
        });
    }

    private String validateInput() {
        if(descriptionField.isEmpty()) return "Description field is mandatory";
        if(clientSelect.isEmpty()) return "Client field is mandatory";
        if(machinistSelect.isEmpty()) return "Machinist field is mandatory";
        if(startDateField.isEmpty()) return "Start date field is mandatory";
        if(endDateField.isEmpty()) return "End date field is mandatory";
        if(statusSelect.isEmpty()) return "Status field is mandatory";
        try {
            new Double(costField.getValue());
        } catch (NumberFormatException e) {
            return "Cost must be floating point number";
        }
        return null;
    }

    private HorizontalLayout initButtons() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        this.saveButton = new Button("Save");
        this.saveButton.setStyleName("Friendly");
        this.cancelButton = new Button("Cancel");
        buttonsLayout.addComponent(saveButton);
        buttonsLayout.addComponent(cancelButton);
        buttonsLayout.setSizeFull();
        buttonsLayout.setComponentAlignment(saveButton, Alignment.MIDDLE_LEFT);
        buttonsLayout.setComponentAlignment(cancelButton, Alignment.MIDDLE_RIGHT);
        return buttonsLayout;
    }
}
