package com.bank.ib.service;

import com.bank.ib.dao.GenericDao;
import com.bank.ib.model.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class AccountTypeServiceImpl extends GenericServiceImpl<AccountType, Integer>
        implements AccountTypeService {

    public AccountTypeServiceImpl() {

    }

    @Autowired
    public AccountTypeServiceImpl(
            @Qualifier("accountTypeDaoImpl") GenericDao<AccountType, Integer> genericDao) {
        super(genericDao);
    }

}
