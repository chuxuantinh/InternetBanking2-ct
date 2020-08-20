package com.bank.ib.controller;


import com.bank.ib.model.Account;
import com.bank.ib.model.AccountFavourite;
import com.bank.ib.model.Payment;
import com.bank.ib.service.AccountFavouriteService;
import com.bank.ib.service.AccountService;
import com.bank.ib.service.GenericService;
import com.bank.ib.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/main/payment/")
@PreAuthorize("hasRole('ROLE_CLIENT')")
public class PaymentController extends AbstractController<Payment> {

    private final PaymentService paymentService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountFavouriteService accountFavouriteService;

    @Autowired
    public PaymentController(@Qualifier("paymentServiceImpl") GenericService<Payment, Integer> service
    ) {

        super(service, Comparator.comparing(Payment::getPaymentDate));
        this.paymentService = (PaymentService) service;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createPayment(@RequestBody Payment a) {

        if (!this.isMyAccount(a.getFromAccount().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        int comp = a.getFromAccount().getBalance().compareTo(a.getPaymentInfo().getAmount());
        Account toAccount;

        /*Check:
        1) Zero amount
        2) Sent amount is bigger then balance
        3) Same account*/
        if (a.getPaymentInfo().getAmount().equals(BigDecimal.ZERO) ||
                comp < 0 ||
                a.getPaymentInfo().getAccountNumber().equals(a.getFromAccount().getAccountNumber())) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //Get target account
        toAccount = this.paymentService.getAccountByNumber(a.getPaymentInfo().getAccountPrefix(), a.getPaymentInfo().getAccountNumber());

        if (toAccount == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        a.setToAccount(toAccount);
        a.setPaymentDate(new Date());

        //Process payment
        this.accountService.paymentProcess(a);

        //Save payment
        accountService.update(a.getFromAccount());
        accountService.update(a.getToAccount());

        return this.standardCreateRecord(a);

    }

    @RequestMapping(value = "account/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Payment>> getAllAccountPayment(@PathVariable("id") int id) {

        if (!this.isMyAccount(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Payment> entities = this.paymentService.getAccountPayments(id);
        entities.sort(comp);

        if (entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @RequestMapping(value = "accountfav/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Payment>> getAllAccountPaymentFav(@PathVariable("id") int id) {

        AccountFavourite af;

        if (!this.isMyAccountFav(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        af = this.accountFavouriteService.read(id);

        List<Payment> entities = this.paymentService.getAccountPaymentsFav(af.getPaymentInfo().getAccountNumber());
        entities.sort(comp);

        if (entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    public boolean isMyAccountFav(int id) {
        for (AccountFavourite accountFavourite : this.getClient().getAccountFavourites()) {
            if (accountFavourite.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

}
