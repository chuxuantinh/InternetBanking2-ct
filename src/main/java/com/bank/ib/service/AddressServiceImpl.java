package com.bank.ib.service;

import com.bank.ib.dao.GenericDao;
import com.bank.ib.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class AddressServiceImpl extends GenericServiceImpl<Address, Integer>
        implements AddressService {

    public AddressServiceImpl() {

    }

    @Autowired
    public AddressServiceImpl(
            @Qualifier("addressDaoImpl") GenericDao<Address, Integer> genericDao) {
        super(genericDao);
    }

}
