package com.haulmont.testtask.dao.impl.hibernate;

import com.haulmont.testtask.dao.GenericDao;
import com.haulmont.testtask.dao.impl.hibernate.entity.ClientTableEntity;
import com.haulmont.testtask.dao.impl.hibernate.entity.MachinistTableEntity;
import com.haulmont.testtask.dao.impl.hibernate.entity.OrderStatusTableEntity;
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
        importRelations(orderEntity, orderModel);
        manager.persist(orderEntity);
        manager.getTransaction().commit();
        orderModel.setId(orderEntity.getId());
        return orderModel;
    }

    @Override
    public void update(Order orderModel) {
        manager.getTransaction().begin();
        OrderTableEntity orderEntity = new OrderTableEntity();
        orderEntity.toEntity(orderModel);
        importRelations(orderEntity, orderModel);
        manager.merge(orderEntity);
        manager.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        manager.getTransaction().begin();
        manager.remove(manager.find(OrderTableEntity.class, id));
        manager.getTransaction().commit();
    }

    @Override
    public Order get(Long id) {
        OrderTableEntity orderEntity = manager.find(OrderTableEntity.class, id);
        return orderEntity.exportRelations(orderEntity.toModel());
    }

    @Override
    public List<Order> getAll() {
        List<Order> allOrders = new ArrayList<>();
        for(OrderTableEntity orderEntity: manager.createQuery("SELECT c FROM OrderTableEntity c", OrderTableEntity.class).getResultList()) {
            allOrders.add(orderEntity.exportRelations(orderEntity.toModel()));
        }
        return allOrders;
    }

    private void importRelations(OrderTableEntity orderEntity, Order orderModel) {
        orderEntity.setClientEntity(manager.find(ClientTableEntity.class, orderModel.getClient().getId()));
        orderEntity.setMachinistEntity(manager.find(MachinistTableEntity.class, orderModel.getMachinist().getId()));
        orderEntity.setOrderStatusEntity(manager.find(OrderStatusTableEntity.class, orderModel.getStatus().getId()));
    }
}
