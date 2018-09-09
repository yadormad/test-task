package com.haulmont.testtask.dao.impl.hibernate;

import com.haulmont.testtask.dao.DeleteException;
import com.haulmont.testtask.dao.GenericDao;
import com.haulmont.testtask.dao.impl.hibernate.entity.ClientTableEntity;
import com.haulmont.testtask.dao.impl.hibernate.entity.MachinistTableEntity;
import com.haulmont.testtask.model.Machinist;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import java.util.ArrayList;
import java.util.List;

public class MachinistHibernateDao implements GenericDao<Long, Machinist> {
    private EntityManager manager;

    MachinistHibernateDao(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Machinist add(Machinist machinistModel) {
        manager.getTransaction().begin();
        MachinistTableEntity machinistEntity = new MachinistTableEntity();
        machinistEntity.toEntity(machinistModel);
        manager.merge(machinistEntity);
        manager.getTransaction().commit();
        return machinistEntity.toModel();
    }

    @Override
    public void delete(Long id) throws DeleteException{
        try {
            manager.getTransaction().begin();
            manager.remove(manager.find(MachinistTableEntity.class, id));
            manager.getTransaction().commit();
        }
        catch(RollbackException e) {
            manager.getTransaction().rollback();
            throw new DeleteException();
        }
    }

    @Override
    public Machinist get(Long id) {
        return manager.find(MachinistTableEntity.class, id).toModel();
    }

    @Override
    public List<Machinist> getAll() {
        List<Machinist> allMachinists = new ArrayList<>();
        for(MachinistTableEntity machinistEntity: manager.createQuery("SELECT c FROM MachinistTableEntity c", MachinistTableEntity.class).getResultList()) {
            allMachinists.add(machinistEntity.toModel());
        }
        return allMachinists;
    }
}
