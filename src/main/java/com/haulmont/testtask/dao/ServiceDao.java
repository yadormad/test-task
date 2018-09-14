package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.*;

import java.util.List;
import java.util.Map;

public interface ServiceDao {
    Map<String, Long> getMachinistOrderStatistic(Machinist machinist);
    List<Order> getFilteredOrder(OrderFilter orderFilter);
}
