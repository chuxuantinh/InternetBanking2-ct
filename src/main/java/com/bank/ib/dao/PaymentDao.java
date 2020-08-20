package com.bank.ib.dao;

import com.bank.ib.model.Account;
import com.bank.ib.model.Payment;

import java.util.List;

public interface PaymentDao extends GenericDao<Payment, Integer> {

    Account getTargetAccount(String acPrefix, String acNumber);

    List<Payment> getAccountPayments(int accountId);

    List<Payment> getAccountPaymentsFav(String accountNumber);

}
