package com.bank.ib.service;

import com.bank.ib.dao.GenericDao;
import com.bank.ib.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class CountryServiceImpl extends GenericServiceImpl<Country, Integer>
        implements CountryService {

    public CountryServiceImpl() {
    }

    @Autowired
    public CountryServiceImpl(
            @Qualifier("countryDaoImpl") GenericDao<Country, Integer> genericDao) {
        super(genericDao);
    }


}
