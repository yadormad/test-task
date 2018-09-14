package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Machinist;
import com.haulmont.testtask.model.Order;
import com.haulmont.testtask.model.OrderStatus;

import java.util.List;
import java.util.Map;

public interface ServiceDao {
    Map<String, Long> getMachinistOrderStatistic(Machinist machinist);
    List<Order> getFilteredOrder(String descriptionFilter, Client clientFilter, OrderStatus orderStatusFilter);
}
