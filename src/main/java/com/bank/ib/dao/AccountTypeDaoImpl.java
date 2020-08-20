package com.bank.ib.dao;

import com.bank.ib.model.AccountType;
import org.springframework.stereotype.Repository;

@Repository
public class AccountTypeDaoImpl extends GenericDaoJpaImpl<AccountType, Integer>
        implements AccountTypeDao {

    @Override
    public boolean canBeDeleted(AccountType at) {

        return this.referenceNotExist("Account", "accountType", at.getId().toString());
    }

    @Override
    public boolean canBeInserted(AccountType at) {

        return this.recordNotExist("accountTypeName", at.getAccountTypeName(), at.getId());
    }

}
