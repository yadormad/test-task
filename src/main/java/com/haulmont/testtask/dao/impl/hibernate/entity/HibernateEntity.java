package com.haulmont.testtask.dao.impl.hibernate.entity;

public interface HibernateEntity<M> {
    M toModel();
    HibernateEntity toEntity(M model);
}
