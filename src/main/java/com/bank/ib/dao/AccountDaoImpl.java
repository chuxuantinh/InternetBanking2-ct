package com.bank.ib.dao;

import com.bank.ib.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl extends GenericDaoJpaImpl<Account, Integer>
        implements AccountDao {

    @Override
    public boolean canBeInserted(Account a) {

        return this.recordNotExist("accountNumber", a.getAccountNumber(), a.getId());
    }

    @Override
    public boolean canBeDeleted(Account a) {

        return (a.getBalance().signum() == 0);
    }

}
