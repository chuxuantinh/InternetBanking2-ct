package com.bank.ib.dao;

import com.bank.ib.model.PaymentInfo;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentInfoDaoImpl extends GenericDaoJpaImpl<PaymentInfo, Integer>
        implements PaymentInfoDao {

    @Override
    public boolean canBeDeleted(PaymentInfo pi) {

        return this.referenceNotExist("Payment", "paymentInfo", pi.getId().toString()) &&
                this.referenceNotExist("PaymentScheduled", "paymentInfo", pi.getId().toString()) &&
                this.referenceNotExist("AccountFavourite", "paymentInfo", pi.getId().toString());
    }

}
