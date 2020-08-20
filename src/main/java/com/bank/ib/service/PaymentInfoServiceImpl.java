package com.bank.ib.service;

import com.bank.ib.dao.GenericDao;
import com.bank.ib.model.PaymentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class PaymentInfoServiceImpl extends GenericServiceImpl<PaymentInfo, Integer>
        implements PaymentInfoService {

    public PaymentInfoServiceImpl() {
    }

    @Autowired
    public PaymentInfoServiceImpl(
            @Qualifier("paymentInfoDaoImpl") GenericDao<PaymentInfo, Integer> genericDao) {
        super(genericDao);
    }


}