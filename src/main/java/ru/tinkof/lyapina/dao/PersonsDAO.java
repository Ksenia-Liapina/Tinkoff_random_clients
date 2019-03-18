package ru.tinkof.lyapina.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.tinkof.lyapina.domain.AddressEntity;
import ru.tinkof.lyapina.domain.PersonsEntity;

import java.util.List;

public class PersonsDAO implements IDAOInterface<PersonsEntity, Integer> {

    private Session currentSession;

    private Transaction currentTransaction;

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(builder.build());
    }

    public PersonsEntity findByFullName(String surname, String name, String middleName){
        this.openCurrentSession();
        PersonsEntity ent = (PersonsEntity) this.getCurrentSession()
                                                .createQuery("select p from PersonsEntity p " +
                                             "where p.surname = :surname and p.name = :name and p.middleName = :middleName")
                                                .setParameter("surname", surname)
                                                .setParameter("name", name)
                                                .setParameter("middleName", middleName)
                                                .getSingleResult();
        this.closeCurrentSession();
        return ent;
    }

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
    public void persist(PersonsEntity entity) {
        this.openCurrentSessionwithTransaction();
        this.getCurrentSession().save(entity);
        this.closeCurrentSessionwithTransaction();
    }

    @Override
    public void persistAll(List<PersonsEntity> entities) {
        this.openCurrentSessionwithTransaction();
        for(PersonsEntity personsEntity : entities){
            this.getCurrentSession().save(personsEntity);
        }
        this.closeCurrentSessionwithTransaction();
    }

    @Override
    public void update(PersonsEntity entity) {
        this.openCurrentSessionwithTransaction();
        this.getCurrentSession().update(entity);
        this.closeCurrentSessionwithTransaction();

    }

    @Override
    public PersonsEntity findById(Integer id) {
        this.openCurrentSession();
        PersonsEntity ent = this.getCurrentSession().get(PersonsEntity.class, id);
        this.closeCurrentSession();
        return ent;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PersonsEntity> findAll() {
        this.openCurrentSession();
        List<PersonsEntity> entities = getCurrentSession().createQuery("select a from PersonsEntity a").list();
        this.closeCurrentSession();
        return entities;
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
}
