package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.model.Client;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;

public class ClientEditWindow extends Window {
    private Long id;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField fatherNameField;
    private TextField phoneNumberField;
    private Button saveButton;
    private Button cancelButton;
    private Controller controller;

    public ClientEditWindow(Controller controller) {
        super();
        this.controller = controller;
        setWidth("50%");
        setHeight("50%");
        setModal(true);
        VerticalLayout fieldLayout = initFields();
        HorizontalLayout buttonsLayout = initButtons();
        fieldLayout.addComponent(buttonsLayout);
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
        return fieldsLayout;
    }

    public void addClient(BeanItemContainer<Client> clientsContainer) {
        this.saveButton.addClickListener((Button.ClickListener) clickEvent -> {
            String errorMsg = validateInput();
            if(errorMsg != null) {
                saveButton.setComponentError(new UserError(errorMsg));
            } else {
                clientsContainer.addItem(controller.addClient(new Client(firstNameField.getValue(), lastNameField.getValue(), fatherNameField.getValue(), phoneNumberField.getValue())));
                this.close();
            }
        });
    }

    public void editClient(Client client) {
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
                controller.updateClient(client);
                this.close();
            }
        });
    }

    private String validateInput() {
        if(firstNameField.isEmpty()) return "First name field is mandatory";
        if(lastNameField.isEmpty()) return "Last name field is mandatory";
        try {
            int meh = Integer.parseInt(phoneNumberField.getValue());
        } catch (NumberFormatException e) {
            return "Phone number must contain only digits (no spaces)";
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
        return buttonsLayout;
    }


}
