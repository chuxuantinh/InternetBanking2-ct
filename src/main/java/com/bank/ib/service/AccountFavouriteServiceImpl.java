package com.bank.ib.service;

import com.bank.ib.dao.GenericDao;
import com.bank.ib.model.AccountFavourite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class AccountFavouriteServiceImpl extends GenericServiceImpl<AccountFavourite, Integer>
        implements AccountFavouriteService {

    public AccountFavouriteServiceImpl() {

    }

    @Autowired
    public AccountFavouriteServiceImpl(
            @Qualifier("accountFavouriteDaoImpl") GenericDao<AccountFavourite, Integer> genericDao) {
        super(genericDao);
    }


}
