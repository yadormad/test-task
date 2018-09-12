package com.haulmont.testtask.dao.impl.hibernate.entity;

import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Order;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "CLIENT_TABLE", schema = "PUBLIC", catalog = "PUBLIC")
public class ClientTableEntity implements HibernateEntity<Client> {
    private long id;
    private String firstname;
    private String lastname;
    private String fathername;
    private String phone;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "PHONE")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientTableEntity that = (ClientTableEntity) o;
        return id == that.id &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(fathername, that.fathername) &&
                Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, fathername, phone);
    }


    @Override
    public Client toModel() {
        return new Client(id, firstname, lastname, fathername, phone);
    }

    @Override
    public ClientTableEntity toEntity(Client model) {
        if(model.getId() != null) {
            this.id = model.getId();
        }
        this.firstname = model.getFirstName();
        this.lastname = model.getLastName();
        this.fathername = model.getFatherName();
        this.phone = model.getPhoneNumber();
        return this;
    }

    public Client exportOrders(Client model) {
        Set<Order> clientOrders = new HashSet<>();
        for(OrderTableEntity clientOrderEntity: orderEntitySet) {
            clientOrders.add(clientOrderEntity.toModel());
        }
        model.setClientOrders(clientOrders);
        return model;
    }

    public void importOrders(Client model) {
        orderEntitySet = new HashSet<>();
        for(Order clientOrderModel: model.getClientOrders()) {
            orderEntitySet.add(new OrderTableEntity().toEntity(clientOrderModel));
        }
    }
}
