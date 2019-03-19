package ru.tinkof.lyapina.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.tinkof.lyapina.domain.AddressEntity;
import ru.tinkof.lyapina.domain.PersonsEntity;


import java.util.List;

public class AddressDAO implements IDAOInterface<AddressEntity, Integer> {

    private Session currentSession;

    private Transaction currentTransaction;

    @Override
    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    @Override
    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    @Override
    public void closeCurrentSession() {
        currentSession.close();
    }

    @Override
    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        return configuration.buildSessionFactory();
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    @Override
    public void persist(AddressEntity entity) {
        this.openCurrentSessionwithTransaction();
        this.getCurrentSession().save(entity);
        this.closeCurrentSessionwithTransaction();
    }

    @Override
    public void update(AddressEntity entity) {
        this.openCurrentSessionwithTransaction();
        this.getCurrentSession().update(entity);
        this.closeCurrentSessionwithTransaction();

    }

    @Override
    public AddressEntity findById(Integer id) {
        this.openCurrentSession();
        AddressEntity ent = this.getCurrentSession().get(AddressEntity.class, id);
        this.closeCurrentSession();
        return ent;
    }

    @Override
    public void persistAll(List<AddressEntity> entities) {
        this.openCurrentSessionwithTransaction();
        for(AddressEntity addressEntity : entities){
            this.getCurrentSession().save(addressEntity);
        }
        this.closeCurrentSessionwithTransaction();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AddressEntity> findAll() {
        this.openCurrentSession();
        List<AddressEntity> entities = getCurrentSession().createQuery("select a from AddressEntity a").list();
        this.closeCurrentSession();
        return entities;
    }
}
