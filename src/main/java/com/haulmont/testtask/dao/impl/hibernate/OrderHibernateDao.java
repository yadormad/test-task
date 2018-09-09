package com.haulmont.testtask.dao.impl.hibernate;

import com.haulmont.testtask.dao.GenericDao;
import com.haulmont.testtask.dao.impl.hibernate.entity.OrderTableEntity;
import com.haulmont.testtask.model.Order;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class OrderHibernateDao implements GenericDao<Long, Order> {
    private EntityManager manager;

    OrderHibernateDao(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Order add(Order orderModel) {
        manager.getTransaction().begin();
        OrderTableEntity orderEntity = new OrderTableEntity();
        orderEntity.toEntity(orderModel);
        manager.merge(orderEntity);
        manager.getTransaction().commit();
        return orderEntity.toModel();
    }

    @Override
    public void delete(Long id) {
        manager.getTransaction().begin();
        manager.remove(manager.find(OrderTableEntity.class, id));
        manager.getTransaction().commit();
    }

    @Override
    public Order get(Long id) {
        return manager.find(OrderTableEntity.class, id).toModel();
    }

    @Override
    public List<Order> getAll() {
        List<Order> allOrders = new ArrayList<>();
        for(OrderTableEntity orderEntity: manager.createQuery("SELECT c FROM OrderTableEntity c", OrderTableEntity.class).getResultList()) {
            allOrders.add(orderEntity.toModel());
        }
        return allOrders;
    }
}
