package com.bank.ib.service;

import com.bank.ib.model.Account;
import com.bank.ib.model.Payment;

public interface AccountService extends GenericService<Account, Integer> {

    void paymentProcess(Payment p);
}
