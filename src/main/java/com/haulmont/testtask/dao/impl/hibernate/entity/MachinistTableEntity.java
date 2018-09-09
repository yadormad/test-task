package com.haulmont.testtask.dao.impl.hibernate.entity;

import com.haulmont.testtask.model.Machinist;
import com.haulmont.testtask.model.Order;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "MACHINIST_TABLE", schema = "PUBLIC", catalog = "PUBLIC")
public class MachinistTableEntity implements HibernateEntity<Machinist> {
    private long id;
    private String firstname;
    private String lastname;
    private String fathername;
    private double hourCost;
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
    @Column(name = "FIRSTNAME")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "LASTNAME")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "FATHERNAME")
    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    @Basic
    @Column(name = "HOUR_COST")
    public double getHourCost() {
        return hourCost;
    }

    public void setHourCost(double hourCost) {
        this.hourCost = hourCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MachinistTableEntity that = (MachinistTableEntity) o;
        return id == that.id &&
                Double.compare(that.hourCost, hourCost) == 0 &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(fathername, that.fathername);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstname, lastname, fathername, hourCost);
    }

    @Override
    public Machinist toModel() {
         Machinist machinistModel = new Machinist(id, firstname, lastname, fathername, hourCost);
         this.exportOrders(machinistModel);
        return machinistModel;
    }

    @Override
    public MachinistTableEntity toEntity(Machinist model) {
        this.id = model.getId();
        this.firstname = model.getFirstName();
        this.lastname = model.getLastName();
        this.fathername = model.getFatherName();
        this.hourCost = model.getValueCost();
        this.importOrders(model);
        return this;
    }

    private void exportOrders(Machinist model) {
        Set<Order> machinistOrders = new HashSet<>();
        for(OrderTableEntity macinistOrderEntity: orderEntitySet) {
            machinistOrders.add(macinistOrderEntity.toModel());
        }
        model.setMachinistOrders(machinistOrders);
    }

    private void importOrders(Machinist model) {
        orderEntitySet = new HashSet<>();
        for(Order machinistOrderModel: model.getMachinistOrders()) {
            orderEntitySet.add(new OrderTableEntity().toEntity(machinistOrderModel));
        }
    }
}
