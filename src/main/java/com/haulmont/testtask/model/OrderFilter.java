package com.haulmont.testtask.model;

public class OrderFilter {
    private String description;
    private Client clientId;
    private OrderStatus orderStatus;

    public OrderFilter(String description, Client clientId, OrderStatus orderStatus) {
        this.description = description;
        this.clientId = clientId;
        this.orderStatus = orderStatus;
    }

    public String getDescription() {
        return description;
    }

    public Client getClientId() {
        return clientId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
