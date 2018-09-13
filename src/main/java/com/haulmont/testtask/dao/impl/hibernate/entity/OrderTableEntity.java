package com.haulmont.testtask.dao.impl.hibernate.entity;

import com.haulmont.testtask.model.Order;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "ORDER_TABLE", schema = "PUBLIC", catalog = "PUBLIC")
public class OrderTableEntity implements HibernateEntity<Order> {
    private long id;
    private String description;
    private ClientTableEntity clientEntity;
    private MachinistTableEntity machinistEntity;
    private Date startDate;
    private Date endDate;
    private OrderStatusTableEntity orderStatusEntity;
    private double fullCost;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    public ClientTableEntity getClientEntity() {
        return clientEntity;
    }

    public void setClientEntity(ClientTableEntity clientEntity) {
        this.clientEntity = clientEntity;
    }

    @ManyToOne
    @JoinColumn(name = "machinist_id", nullable = false)
    public MachinistTableEntity getMachinistEntity() {
        return machinistEntity;
    }

    public void setMachinistEntity(MachinistTableEntity machinistEntity) {
        this.machinistEntity = machinistEntity;
    }

    @ManyToOne
    @JoinColumn(name = "order_status_id", nullable = false)
    public OrderStatusTableEntity getOrderStatusEntity() {
        return orderStatusEntity;
    }

    public void setOrderStatusEntity(OrderStatusTableEntity orderStatusEntity) {
        this.orderStatusEntity = orderStatusEntity;
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
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




    @Basic
    @Column(name = "START_DATE")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "END_DATE")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "FULL_COST")
    public double getFullCost() {
        return fullCost;
    }

    public void setFullCost(double fullCost) {
        this.fullCost = fullCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderTableEntity that = (OrderTableEntity) o;
        return id == that.id &&
                Double.compare(that.fullCost, fullCost) == 0 &&
                Objects.equals(description, that.description) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, description, startDate, endDate, fullCost);
    }

    @Override
    public Order toModel() {
        return new Order(id, description, startDate, endDate, fullCost);
    }

    @Override
    public OrderTableEntity toEntity(Order model) {
        if(model.getId() != null) {
            this.id = model.getId();
        }
        this.description = model.getDescription();
        this.startDate = new java.sql.Date(model.getStartDate().getTime());
        this.endDate = new java.sql.Date(model.getEndDate().getTime());
        this.fullCost = model.getCost();
        return this;
    }

    public Order exportRelations(Order model) {
        model.setClient(this.clientEntity.toModel());
        model.setMachinist(this.machinistEntity.toModel());
        model.setStatus(this.orderStatusEntity.toModel());
        return model;
    }

    public void importRelations(Order model) {
        this.clientEntity = new ClientTableEntity().toEntity(model.getClient());
        this.machinistEntity = new MachinistTableEntity().toEntity(model.getMachinist());
        this.orderStatusEntity = new OrderStatusTableEntity().toEntity(model.getStatus());
    }
}
