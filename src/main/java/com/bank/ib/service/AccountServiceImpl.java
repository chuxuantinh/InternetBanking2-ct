package com.bank.ib.service;

import com.bank.ib.dao.GenericDao;
import com.bank.ib.model.Account;
import com.bank.ib.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class AccountServiceImpl extends GenericServiceImpl<Account, Integer>
        implements AccountService {

    public AccountServiceImpl() {

    }

    @Autowired
    public AccountServiceImpl(
            @Qualifier("accountDaoImpl") GenericDao<Account, Integer> genericDao) {
        super(genericDao);
    }


    @Override
    public void paymentProcess(Payment p) {

        BigDecimal paymentSum = p.getPaymentInfo().getAmount();

        Account fromAccount = this.read(p.getFromAccount().getId());
        Account toAccount = this.read(p.getToAccount().getId());

        //From Account -
        fromAccount.setBalance(fromAccount.getBalance().subtract(paymentSum));

        //Target account +
        toAccount.setBalance(toAccount.getBalance().add(paymentSum));

        p.setFromAccount(fromAccount);
        p.setToAccount(toAccount);

    }

}
