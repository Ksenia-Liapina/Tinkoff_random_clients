package ru.tinkof.lyapina.dao;

import org.hibernate.Session;

import java.util.List;

public interface IDAOInterface<T, Id> {

    Session openCurrentSession();

    Session openCurrentSessionwithTransaction();

    void closeCurrentSession();

    void closeCurrentSessionwithTransaction();

    void persist(T entity);

    void persistAll(List<T> entities);

    void update(T entity);

    T findById(Id id);

    List<T> findAll();
}
