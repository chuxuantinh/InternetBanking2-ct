package com.bank.ib.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;


public class GenericDaoJpaImpl<T, PK extends Serializable>
        implements GenericDao<T, PK> {

    protected final Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public GenericDaoJpaImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass
                .getActualTypeArguments()[0];
    }

    @Override
    public void create(T t) {
        this.entityManager.persist(t);
    }

    @Override
    public T read(PK id) {
        return this.entityManager.find(entityClass, id);
    }

    @Override
    public void update(T t) {
        this.entityManager.merge(t);

    }

    @Override
    public void remove(T t) {
        t = this.entityManager.merge(t);
        this.entityManager.remove(t);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getAll() {

        return entityManager.createQuery("Select t from " + entityClass.getSimpleName() + " t").getResultList();
    }

    @Override
    public long countAll() {

        return (long) entityManager.createQuery("Select count(*) from " + entityClass.getSimpleName()).getSingleResult();
    }

    @Override
    public boolean recordNotExist(String column, String columnValue, Integer id) {

        Query query = entityManager.createQuery("Select t from " + entityClass.getSimpleName() + " t where t." + column + " = '" + columnValue + "' and t.id <> " + id);

        return query.getResultList().isEmpty();

    }

    @Override
    public boolean referenceNotExist(String table, String column, String id) {

        Query query = entityManager.createQuery("Select t from " + table + " t where t." + column + " = " + id);

        return query.getResultList().isEmpty();

    }

    @Override
    public boolean canBeDeleted(T entity) {

        return true;
    }

    @Override
    public boolean canBeInserted(T entity) {

        return true;
    }


}

