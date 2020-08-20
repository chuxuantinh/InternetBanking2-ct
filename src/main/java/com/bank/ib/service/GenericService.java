package com.bank.ib.service;

import java.util.List;

public interface GenericService<T, PK> {

    void create(T entity);

    void update(T entity);

    void remove(T entity);

    T read(PK key);

    List<T> getAll();

    long countAll();

    boolean canBeDeleted(T entity);

    boolean canBeInserted(T entity);

}
