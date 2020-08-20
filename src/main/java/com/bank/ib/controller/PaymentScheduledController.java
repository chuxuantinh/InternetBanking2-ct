package com.bank.ib.controller;


import com.bank.ib.model.Account;
import com.bank.ib.model.PaymentScheduled;
import com.bank.ib.service.AccountService;
import com.bank.ib.service.GenericService;
import com.bank.ib.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(value = "/main/paymentscheduled/")
@PreAuthorize("hasRole('ROLE_CLIENT')")
public class PaymentScheduledController extends AbstractController<PaymentScheduled> {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    public PaymentScheduledController(@Qualifier("paymentScheduledServiceImpl") GenericService<PaymentScheduled, Integer> service
    ) {

        super(service, Comparator.comparing(PaymentScheduled::getPaymentDate));
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PaymentScheduled>> getAllClientPaymentScheduled() {

        List<PaymentScheduled> entities = new ArrayList<>();
        Set<Account> accounts = this.getClient().getAccounts();


        for (Account acc : accounts) {
            for (PaymentScheduled psd : acc.getPaymentsScheduled()) {
                psd.getAccount().setClients(null);
                entities.add(psd);
            }
        }

        if (entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        entities.sort(comp);

        return new ResponseEntity<>(entities, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createPaymentScheduled(@RequestBody PaymentScheduled a) {

        if (!this.isMyAccount(a.getAccount().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Account scheduledAccount;

        a.getAccount().setClients(this.accountService.read(a.getAccount().getId()).getClients());

        scheduledAccount = this.paymentService.getAccountByNumber(a.getPaymentInfo().getAccountPrefix(), a.getPaymentInfo().getAccountNumber());

        if (scheduledAccount == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        service.create(a);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updatePaymentScheduled(@PathVariable("id") int id, @RequestBody PaymentScheduled a) {

        if (!this.isMyPayScheduled(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        a.getAccount().setClients(this.accountService.read(a.getAccount().getId()).getClients());

        return this.standardUpdateRecord(id, a);
    }


    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removePaymentScheduled(@PathVariable("id") int id) {

        if (!this.isMyPayScheduled(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return this.standardRemoveRecord(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<PaymentScheduled> readPaymentScheduled(@PathVariable("id") int id) {

        if (!this.isMyPayScheduled(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        PaymentScheduled ps = this.service.read(id);

        if (ps == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ps.getAccount().setClients(null);
        return new ResponseEntity<>(ps, HttpStatus.OK);
    }

    public boolean isMyPayScheduled(int id) {
        for (Account account : this.getClient().getAccounts()) {
            for (PaymentScheduled payScheduled : account.getPaymentsScheduled()) {
                if (payScheduled.getId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

}
