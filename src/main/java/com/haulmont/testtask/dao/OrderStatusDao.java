package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.OrderStatus;

import java.util.List;

public interface OrderStatusDao {
    List<OrderStatus> getAll();
}
