package com.bank.ib.service;

import com.bank.ib.model.Account;
import com.bank.ib.model.Payment;

import java.util.List;


public interface PaymentService extends GenericService<Payment, Integer> {

    Account getAccountByNumber(String acPrefix, String acNumber);

    List<Payment> getAccountPayments(int accountId);

    List<Payment> getAccountPaymentsFav(String accountNumber);

}
