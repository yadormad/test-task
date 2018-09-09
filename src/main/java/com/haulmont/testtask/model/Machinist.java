package com.haulmont.testtask.model;

import java.util.HashSet;
import java.util.Set;

public class Machinist {
    private Long id;
    private String firstName;
    private String lastName;
    private String fatherName;
    private Double valueCost;
    private Set<Order> machinistOrders;

    public Machinist(Long id, String firstName, String lastName, String fatherName, Double valueCost) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.valueCost = valueCost;
        this.machinistOrders = new HashSet<>();
    }

    public Machinist(String firstName, String lastName, String fatherName, Double valueCost) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.valueCost = valueCost;
        this.machinistOrders = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Double getValueCost() {
        return valueCost;
    }

    public void setValueCost(Double valueCost) {
        this.valueCost = valueCost;
    }

    public Set<Order> getMachinistOrders() {
        return machinistOrders;
    }

    public void setMachinistOrders(Set<Order> machinistOrders) {
        this.machinistOrders = machinistOrders;
    }
}
