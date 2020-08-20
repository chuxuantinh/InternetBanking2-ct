package com.bank.ib.dao;

import com.bank.ib.model.Bank;
import org.springframework.stereotype.Repository;


@Repository
public class BankDaoImpl extends GenericDaoJpaImpl<Bank, Integer>
        implements BankDao {

    @Override
    public boolean canBeDeleted(Bank b) {


        return this.referenceNotExist("PaymentInfo", "bank", b.getId().toString());
    }


    @Override
    public boolean canBeInserted(Bank b) {

        return this.recordNotExist("bankName", b.getBankName(), b.getId());
    }

}
