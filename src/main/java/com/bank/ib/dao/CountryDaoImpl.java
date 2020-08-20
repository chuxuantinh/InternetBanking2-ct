package com.bank.ib.dao;

import com.bank.ib.model.Country;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CountryDaoImpl extends GenericDaoJpaImpl<Country, Integer>
        implements CountryDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public boolean canBeDeleted(Country c) {

        return this.referenceNotExist("Address", "country", c.getId().toString());
    }

    @Override
    public boolean canBeInserted(Country c) {

        return this.recordNotExist("countryName", c.getCountryName(), c.getId());
    }

}
