package com.haulmont.testtask.dao.impl.hibernate;

import com.haulmont.testtask.dao.OrderStatusDao;
import com.haulmont.testtask.dao.impl.hibernate.entity.OrderStatusTableEntity;
import com.haulmont.testtask.model.OrderStatus;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class OrderStatusHibernateDao implements OrderStatusDao {
    private EntityManager manager;
    
    OrderStatusHibernateDao(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public List<OrderStatus> getAll() {
        List<OrderStatus> allOrderStatuses = new ArrayList<>();
        for(OrderStatusTableEntity OrderStatusEntity: manager.createQuery("SELECT c FROM OrderStatusTableEntity c", OrderStatusTableEntity.class).getResultList()) {
            allOrderStatuses.add(OrderStatusEntity.toModel());
        }
        return allOrderStatuses;
    }
}
