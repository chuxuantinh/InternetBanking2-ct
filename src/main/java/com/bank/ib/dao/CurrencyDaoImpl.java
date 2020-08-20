package com.bank.ib.dao;

import com.bank.ib.model.Currency;
import org.springframework.stereotype.Repository;


@Repository
public class CurrencyDaoImpl extends GenericDaoJpaImpl<Currency, Integer>
        implements CurrencyDao {

    @Override
    public boolean canBeDeleted(Currency c) {

        return this.referenceNotExist("Account", "currency", c.getId().toString());
    }

    @Override
    public boolean canBeInserted(Currency c) {

        return this.recordNotExist("currencyName", c.getCurrencyName(), c.getId());
    }

}
