package com.haulmont.testtask.model;

public class Machinist {
    private Long id;
    private String firstName;
    private String lastName;
    private String fatherName;
    private Integer valueCost;

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

    public Integer getValueCost() {
        return valueCost;
    }

    public void setValueCost(Integer valueCost) {
        this.valueCost = valueCost;
    }
}
