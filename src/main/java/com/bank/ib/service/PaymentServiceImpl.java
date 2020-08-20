package com.bank.ib.service;

import com.bank.ib.dao.GenericDao;
import com.bank.ib.dao.PaymentDao;
import com.bank.ib.model.Account;
import com.bank.ib.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl extends GenericServiceImpl<Payment, Integer>
        implements PaymentService {

    private PaymentDao paymentDao;

    public PaymentServiceImpl() {

    }

    @Autowired
    public PaymentServiceImpl(
            @Qualifier("paymentDaoImpl") GenericDao<Payment, Integer> genericDao) {
        super(genericDao);
        this.paymentDao = (PaymentDao) genericDao;
    }

    @Override
    public Account getAccountByNumber(String acPrefix, String acNumber) {
        return this.paymentDao.getTargetAccount(acPrefix, acNumber);
    }

    @Override
    public List<Payment> getAccountPayments(int accountId) {

        return this.paymentDao.getAccountPayments(accountId);
    }

    @Override
    public List<Payment> getAccountPaymentsFav(String accountNumber) {

        return this.paymentDao.getAccountPaymentsFav(accountNumber);
    }


}
