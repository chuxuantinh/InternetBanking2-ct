package com.bank.ib.service;

import com.bank.ib.dao.GenericDao;
import com.bank.ib.model.PaymentScheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class PaymentScheduledServiceImpl extends GenericServiceImpl<PaymentScheduled, Integer>
        implements PaymentScheduledService {

    public PaymentScheduledServiceImpl() {

    }

    @Autowired
    public PaymentScheduledServiceImpl(
            @Qualifier("paymentScheduledDaoImpl") GenericDao<PaymentScheduled, Integer> genericDao) {
        super(genericDao);
    }


}
