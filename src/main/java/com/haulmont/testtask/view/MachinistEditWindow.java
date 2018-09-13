package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.model.Machinist;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

public class MachinistEditWindow extends Window{
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField fatherNameField;
    private TextField hourCostField;
    private Button saveButton;
    private Button cancelButton;
    private Controller controller;

    public MachinistEditWindow(Controller controller) {
        super();
        this.controller = controller;
        setWidth("50%");
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
        hourCostField = new TextField("Hour Cost");
        firstNameField.setRequired(true);
        lastNameField.setRequired(true);
        fatherNameField.setRequired(false);
        fieldsLayout.addComponent(firstNameField);
        fieldsLayout.addComponent(lastNameField);
        fieldsLayout.addComponent(fatherNameField);
        fieldsLayout.addComponent(hourCostField);
        fieldsLayout.setComponentAlignment(firstNameField, Alignment.TOP_CENTER);
        fieldsLayout.setComponentAlignment(lastNameField, Alignment.TOP_CENTER);
        fieldsLayout.setComponentAlignment(fatherNameField, Alignment.TOP_CENTER);
        fieldsLayout.setComponentAlignment(hourCostField, Alignment.TOP_CENTER);
        fieldsLayout.setExpandRatio(firstNameField, 0.1f);
        fieldsLayout.setExpandRatio(lastNameField, 0.1f);
        fieldsLayout.setExpandRatio(fatherNameField, 0.1f);
        fieldsLayout.setExpandRatio(hourCostField, 0.1f);
        return fieldsLayout;
    }

    public void addMachinist(BeanItemContainer<Machinist> machinistContainer) {
        this.saveButton.addClickListener((Button.ClickListener) clickEvent -> {
            String errorMsg = validateInput();
            if(errorMsg != null) {
                saveButton.setComponentError(new UserError(errorMsg));
            } else {
                machinistContainer.addItem(controller.addMachinist(new Machinist(firstNameField.getValue(), lastNameField.getValue(), fatherNameField.getValue(), new Double(hourCostField.getValue()))));
                this.close();
            }
        });
    }

    public void editMachinist(GridContainerHelper gridContainerHelper, Machinist machinist) {
        firstNameField.setValue(machinist.getFirstName());
        lastNameField.setValue(machinist.getLastName());
        fatherNameField.setValue(machinist.getFatherName());
        hourCostField.setValue(String.valueOf(machinist.getValueCost()));
        this.saveButton.addClickListener((Button.ClickListener) clickEvent -> {
            String errorMsg = validateInput();
            if(errorMsg != null) {
                saveButton.setComponentError(new UserError(errorMsg));
            } else {
                machinist.setFirstName(firstNameField.getValue());
                machinist.setLastName(lastNameField.getValue());
                machinist.setFatherName(fatherNameField.getValue());
                machinist.setValueCost(Double.valueOf(hourCostField.getValue()));
                controller.updateMachinist(machinist);
                gridContainerHelper.updateMachinistContainer();
                this.close();
            }
        });
    }

    private String validateInput() {
        if(firstNameField.isEmpty()) return "First name field is mandatory";
        if(lastNameField.isEmpty()) return "Last name field is mandatory";
        try {
            new Double(hourCostField.getValue());
        } catch (NumberFormatException e) {
            return "Hour cost must be floating point number";
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
