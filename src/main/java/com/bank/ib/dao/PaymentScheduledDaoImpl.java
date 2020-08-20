package com.bank.ib.dao;

import com.bank.ib.model.PaymentScheduled;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentScheduledDaoImpl extends GenericDaoJpaImpl<PaymentScheduled, Integer>
        implements PaymentScheduledDao {


}
