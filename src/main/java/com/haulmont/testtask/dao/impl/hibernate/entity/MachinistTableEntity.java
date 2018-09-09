package com.haulmont.testtask.dao.impl.hibernate.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "MACHINIST_TABLE", schema = "PUBLIC", catalog = "PUBLIC")
public class MachinistTableEntity {
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
}
