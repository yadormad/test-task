package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.model.Client;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

public class ClientEditWindow extends Window {
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField fatherNameField;
    private TextField phoneNumberField;
    private Button saveButton;
    private Button cancelButton;

    public ClientEditWindow() {
        super();
        setWidth("30%");
        setHeight("60%");
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
        firstNameField = new TextField("First Name");
        lastNameField = new TextField("Last Name");
        fatherNameField = new TextField("Father Name");
        phoneNumberField = new TextField("Phone Number");
        firstNameField.setRequired(true);
        lastNameField.setRequired(true);
        fatherNameField.setRequired(false);
        phoneNumberField.setMaxLength(15);
        fieldsLayout.addComponent(firstNameField);
        fieldsLayout.addComponent(lastNameField);
        fieldsLayout.addComponent(fatherNameField);
        fieldsLayout.addComponent(phoneNumberField);
        fieldsLayout.setComponentAlignment(firstNameField, Alignment.TOP_CENTER);
        fieldsLayout.setComponentAlignment(lastNameField, Alignment.TOP_CENTER);
        fieldsLayout.setComponentAlignment(fatherNameField, Alignment.TOP_CENTER);
        fieldsLayout.setComponentAlignment(phoneNumberField, Alignment.TOP_CENTER);
        fieldsLayout.setExpandRatio(firstNameField, 0.1f);
        fieldsLayout.setExpandRatio(lastNameField, 0.1f);
        fieldsLayout.setExpandRatio(fatherNameField, 0.1f);
        fieldsLayout.setExpandRatio(phoneNumberField, 0.1f);
        return fieldsLayout;
    }

    public void addClient(BeanItemContainer<Client> clientsContainer) {
        this.saveButton.addClickListener((Button.ClickListener) clickEvent -> {
            String errorMsg = validateInput();
            if(errorMsg != null) {
                saveButton.setComponentError(new UserError(errorMsg));
            } else {
                clientsContainer.addItem(Controller.getInstance().addClient(new Client(firstNameField.getValue(), lastNameField.getValue(), fatherNameField.getValue(), phoneNumberField.getValue())));
                this.close();
            }
        });
    }

    public void editClient(GridContainerHelper gridContainerHelper, Client client) {
        firstNameField.setValue(client.getFirstName());
        lastNameField.setValue(client.getLastName());
        fatherNameField.setValue(client.getFatherName());
        phoneNumberField.setValue(client.getPhoneNumber());
        this.saveButton.addClickListener((Button.ClickListener) clickEvent -> {
            String errorMsg = validateInput();
            if(errorMsg != null) {
                saveButton.setComponentError(new UserError(errorMsg));
            } else {
                client.setFirstName(firstNameField.getValue());
                client.setLastName(lastNameField.getValue());
                client.setFatherName(fatherNameField.getValue());
                client.setPhoneNumber(phoneNumberField.getValue());
                Controller.getInstance().updateClient(client);
                gridContainerHelper.updateClientContainer();
                this.close();
            }
        });
    }

    private String validateInput() {
        if(firstNameField.isEmpty()) return "First name field is mandatory";
        if(lastNameField.isEmpty()) return "Last name field is mandatory";
        try {
            new Integer(phoneNumberField.getValue());
        } catch (NumberFormatException e) {
            return "Phone number must contain only digits (no spaces)";
        }
        return null;
    }

    private HorizontalLayout initButtons() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        this.saveButton = new Button("Save");
        this.saveButton.setStyleName("friendly");
        this.cancelButton = new Button("Cancel");
        buttonsLayout.addComponent(saveButton);
        buttonsLayout.addComponent(cancelButton);
        buttonsLayout.setSizeFull();
        buttonsLayout.setComponentAlignment(saveButton, Alignment.MIDDLE_LEFT);
        buttonsLayout.setComponentAlignment(cancelButton, Alignment.MIDDLE_RIGHT);
        return buttonsLayout;
    }
}
