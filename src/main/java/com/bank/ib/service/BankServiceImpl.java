package com.bank.ib.service;

import com.bank.ib.dao.GenericDao;
import com.bank.ib.model.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class BankServiceImpl extends GenericServiceImpl<Bank, Integer>
        implements BankService {

    public BankServiceImpl() {

    }

    @Autowired
    public BankServiceImpl(
            @Qualifier("bankDaoImpl") GenericDao<Bank, Integer> genericDao) {
        super(genericDao);
    }


}

