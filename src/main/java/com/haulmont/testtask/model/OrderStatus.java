package com.haulmont.testtask.model;

import java.util.Objects;

public class OrderStatus {
    private Long id;
    private String status;

    public OrderStatus(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderStatus)) return false;
        OrderStatus that = (OrderStatus) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }
}
