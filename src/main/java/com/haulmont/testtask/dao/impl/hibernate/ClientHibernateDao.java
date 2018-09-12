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
        manager.persist(clientEntity);
        manager.getTransaction().commit();
        clientModel.setId(clientEntity.getId());
        return clientModel;
    }

    @Override
    public Client update(Client clientModel) {
        manager.getTransaction().begin();
        ClientTableEntity clientEntity = new ClientTableEntity();
        clientEntity.toEntity(clientModel);
        clientEntity.importOrders(clientModel);
        manager.merge(clientEntity);
        manager.getTransaction().commit();
        return clientModel;
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
        ClientTableEntity clientEntity = manager.find(ClientTableEntity.class, id);
        return clientEntity.exportOrders(clientEntity.toModel());
    }

    @Override
    public List<Client> getAll() {
        List<Client> allClients = new ArrayList<>();
        for(ClientTableEntity clientEntity: manager.createQuery("SELECT c FROM ClientTableEntity c", ClientTableEntity.class).getResultList()) {
            allClients.add(clientEntity.exportOrders(clientEntity.toModel()));
        }
        return allClients;
    }
}
