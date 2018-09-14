package com.haulmont.testtask.dao.impl.hibernate;

import com.haulmont.testtask.dao.ServiceDao;
import com.haulmont.testtask.dao.impl.hibernate.entity.OrderTableEntity;
import com.haulmont.testtask.model.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceHibernateDao implements ServiceDao {
    private EntityManager manager;

    ServiceHibernateDao(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Map<String, Long> getMachinistOrderStatistic(Machinist machinist) {
        String statisticQueryString = String.format("select s.status, count(s.id) from OrderTableEntity o " +
                "inner join OrderStatusTableEntity s on o.orderStatusEntity.id = s.id " +
                "where o.machinistEntity.id = %d " +
                "group by s.id", machinist.getId());
        List<Object[]> orderStatistics = manager.createQuery(statisticQueryString).getResultList();
        Map<String, Long> orderStatisticsMap = new HashMap<>();
        for(Object[] orderStatisticsRow: orderStatistics) {
            orderStatisticsMap.put((String)orderStatisticsRow[0], (Long)orderStatisticsRow[1]);
        }
        return orderStatisticsMap;
    }

    @Override
    public List<Order> getFilteredOrder(OrderFilter orderFilter) {
        String filteredQueryString = "select o from OrderTableEntity o " +
                "where description like '%" + orderFilter.getDescription() + "%'";
        if (orderFilter.getClient() != null) {
            filteredQueryString = filteredQueryString + " and clientEntity.id = " + orderFilter.getClient().getId();
        }
        if (orderFilter.getOrderStatus() != null) {
            filteredQueryString = filteredQueryString + " and orderStatusEntity.id = " + orderFilter.getOrderStatus().getId();
        }
        List<Order> orderList = new ArrayList<>();
        for (OrderTableEntity orderEntity: manager.createQuery(filteredQueryString, OrderTableEntity.class).getResultList()) {
            orderList.add(orderEntity.exportRelations(orderEntity.toModel()));
        }
        return orderList;
    }
}
