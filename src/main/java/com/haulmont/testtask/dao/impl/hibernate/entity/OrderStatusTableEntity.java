package com.haulmont.testtask.dao.impl.hibernate.entity;

import com.haulmont.testtask.model.OrderStatus;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ORDER_STATUS_TABLE", schema = "PUBLIC", catalog = "PUBLIC")
public class OrderStatusTableEntity {
    private long id;
    private String status;
    private Set<OrderTableEntity> orderEntitySet;

    @OneToMany(mappedBy = "orderStatusEntity")
    public Set<OrderTableEntity> getOrderEntitySet() {
        return orderEntitySet;
    }

    public void setOrderEntitySet(Set<OrderTableEntity> orderEntitySet) {
        this.orderEntitySet = orderEntitySet;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStatusTableEntity that = (OrderStatusTableEntity) o;
        return id == that.id &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, status);
    }

    public OrderStatus toModel() {
        return new OrderStatus(id, status);
    }
}
