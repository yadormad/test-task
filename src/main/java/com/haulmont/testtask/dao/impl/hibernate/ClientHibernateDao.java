package com.haulmont.testtask.dao.impl.hibernate;

import com.haulmont.testtask.dao.DeleteException;
import com.haulmont.testtask.dao.GenericDao;
import com.haulmont.testtask.dao.impl.hibernate.entity.ClientTableEntity;
import com.haulmont.testtask.model.Client;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import java.util.ArrayList;
import java.util.List;

public class ClientHibernateDao implements GenericDao<Long, Client> {

    private EntityManager manager;

    ClientHibernateDao(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Client add(Client clientModel) {
        manager.getTransaction().begin();
        ClientTableEntity clientEntity = new ClientTableEntity();
        clientEntity.toEntity(clientModel);
        manager.merge(clientEntity);
        manager.getTransaction().commit();
        return clientEntity.toModel();
    }

    @Override
    public void delete(Long id) throws DeleteException {
        try {
            manager.getTransaction().begin();
            manager.remove(manager.find(ClientTableEntity.class, id));
            manager.getTransaction().commit();
        }
        catch(RollbackException e) {
            manager.getTransaction().rollback();
            throw new DeleteException();
        }
    }

    @Override
    public Client get(Long id) {
        return manager.find(ClientTableEntity.class, id).toModel();
    }

    @Override
    public List<Client> getAll() {
        List<Client> allClients = new ArrayList<>();
        for(ClientTableEntity clientEntity: manager.createQuery("SELECT c FROM ClientTableEntity c", ClientTableEntity.class).getResultList()) {
            allClients.add(clientEntity.toModel());
        }
        return allClients;
    }
}
