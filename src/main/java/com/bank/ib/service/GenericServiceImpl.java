package com.bank.ib.service;

import com.bank.ib.dao.GenericDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public abstract class GenericServiceImpl<T, PK>
        implements GenericService<T, PK> {

    private GenericDao<T, PK> genericDao;

    public GenericServiceImpl(GenericDao<T, PK> genericDao) {
        this.genericDao = genericDao;
    }

    public GenericServiceImpl() {
    }

    @Override
    @Transactional
    public T read(PK id) {
        return genericDao.read(id);
    }

    @Override
    @Transactional
    public void create(T entity) {
        genericDao.create(entity);
    }

    @Override
    @Transactional
    public void update(T entity) {
        genericDao.update(entity);
    }

    @Override
    @Transactional
    public void remove(T entity) {
        genericDao.remove(entity);
    }

    @Override
    @Transactional
    public List<T> getAll() {
        return genericDao.getAll();
    }

    @Override
    @Transactional
    public long countAll() {
        return genericDao.countAll();
    }

    @Override
    @Transactional
    public boolean canBeDeleted(T entity) {
        return genericDao.canBeDeleted(entity);
    }

    @Override
    @Transactional
    public boolean canBeInserted(T entity) {
        return genericDao.canBeInserted(entity);
    }

}