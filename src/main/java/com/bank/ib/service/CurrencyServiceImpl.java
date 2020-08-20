package com.bank.ib.service;

import com.bank.ib.dao.GenericDao;
import com.bank.ib.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

//import com.bank.ib.dao.CurrencyDao;

@Service
public class CurrencyServiceImpl extends GenericServiceImpl<Currency, Integer>
        implements CurrencyService {


    public CurrencyServiceImpl() {

    }

    @Autowired
    public CurrencyServiceImpl(
            @Qualifier("currencyDaoImpl") GenericDao<Currency, Integer> genericDao) {
        super(genericDao);
    }

}