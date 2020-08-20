package com.bank.ib.controller;


import com.bank.ib.model.Account;
import com.bank.ib.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/main/account/")
@PreAuthorize("hasRole('ROLE_CLIENT')")
public class AccountController extends AbstractController<Account> {

    @Autowired
    public AccountController(@Qualifier("accountServiceImpl") GenericService<Account, Integer> service) {

        super(service, Comparator.comparing(Account::getAccountName, String.CASE_INSENSITIVE_ORDER));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Set<Account>> getAllClientAccount() {

        Set<Account> entities = this.getClient().getAccounts();

        for (Account acc : entities) {
            acc.setClients(null);
        }

        if (entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createAccount(@RequestBody Account a) {
        a.setClients(new HashSet<>());
        a.getClients().add(getClient());

        //Security
        a.setBalance(BigDecimal.ZERO);
        a.setCards(null);
        a.setPayments(null);
        a.setPaymentsScheduled(null);
        return this.standardCreateRecord(a);
    }


    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateAccount(@PathVariable("id") int id, @RequestBody Account a) {


        if (!this.isMyAccount(a.getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Account ia = this.service.read(a.getId());

        ia.setAccountName(a.getAccountName());

        return this.standardUpdateRecord(id, ia);
    }


    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Account> readAccount(@PathVariable("id") int id) {

        if (!this.isMyAccount(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return this.standardReadRecord(id);
    }


}
