package com.bank.ib.dao;

import java.util.List;


public interface GenericDao<T, PK> {

    void create(T entity);

    void update(T entity);

    void remove(T entity);

    T read(PK key);

    List<T> getAll();

    long countAll();

    boolean recordNotExist(String column, String columnValue, Integer id);

    boolean referenceNotExist(String table, String column, String id);

    boolean canBeDeleted(T entity);

    boolean canBeInserted(T entity);


}
