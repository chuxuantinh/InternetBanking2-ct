package com.bank.ib.dao;

import com.bank.ib.model.AccountFavourite;
import org.springframework.stereotype.Repository;

@Repository
public class AccountFavouriteDaoImpl extends GenericDaoJpaImpl<AccountFavourite, Integer>
        implements AccountFavouriteDao {

    @Override
    public boolean canBeInserted(AccountFavourite af) {

        return this.recordNotExist("accountFavouriteName", af.getAccountFavouriteName(), af.getId());
    }

}
