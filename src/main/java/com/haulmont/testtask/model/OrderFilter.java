package com.haulmont.testtask.model;

public class OrderFilter {
    private String description;
    private Client client;
    private OrderStatus orderStatus;

    public OrderFilter(String description, Client client, OrderStatus orderStatus) {
        this.description = description;
        this.client = client;
        this.orderStatus = orderStatus;
    }

    public String getDescription() {
        return description;
    }

    public Client getClient() {
        return client;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
