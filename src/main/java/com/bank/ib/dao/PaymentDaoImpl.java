package com.bank.ib.dao;

import com.bank.ib.model.Account;
import com.bank.ib.model.Payment;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class PaymentDaoImpl extends GenericDaoJpaImpl<Payment, Integer>
        implements PaymentDao {

    @Override
    public Account getTargetAccount(String acPrefix, String acNumber) {

        Query sel = this.entityManager.createQuery("Select t from Account t where t.accountNumber = '" + acNumber + "'"); //and t.accountPrefix = '" + acPrefix + "'");		

        sel.setMaxResults(1);
        @SuppressWarnings("unchecked")
        List<Account> list = sel.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);

    }

    @Override
    public List<Payment> getAccountPayments(int accountId) {

        Query sel = this.entityManager.createQuery("Select t from Payment t where t.fromAccount.id = '" + accountId + "' or t.toAccount.id = '" + accountId + "'");

        @SuppressWarnings("unchecked")
        List<Payment> list = sel.getResultList();

        return list;
    }

    @Override
    public List<Payment> getAccountPaymentsFav(String accountNumber) {

        Query sel = this.entityManager.createQuery("Select t from Payment t where t.toAccount.accountNumber = '" + accountNumber + "'");

        @SuppressWarnings("unchecked")
        List<Payment> list = sel.getResultList();

        return list;
    }

}
