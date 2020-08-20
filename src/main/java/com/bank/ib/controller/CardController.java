package com.bank.ib.controller;


import com.bank.ib.model.Account;
import com.bank.ib.model.Card;
import com.bank.ib.service.AccountService;
import com.bank.ib.service.CardService;
import com.bank.ib.service.GenericService;
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
@RequestMapping(value = "/main/card/")
@PreAuthorize("hasRole('ROLE_CLIENT')")
public class CardController extends AbstractController<Card> {

    private final CardService cardService;

    @Autowired
    private AccountService accountService;

    @Autowired
    public CardController(@Qualifier("cardServiceImpl") GenericService<Card, Integer> service) {

        super(service, Comparator.comparing(Card::getCardNumber, String.CASE_INSENSITIVE_ORDER));
        this.cardService = (CardService) service;

    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Card>> getAllClientCard() {

        List<Card> entities = new ArrayList<>();
        Set<Account> accounts = this.getClient().getAccounts();

        for (Account acc : accounts) {
            entities.addAll(acc.getCards());
        }

        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createCard(@RequestBody Card c) {

        if (!this.isMyAccount(c.getAccount().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        this.cardService.prepareCard(c);

        c.getAccount().setClients(this.accountService.read(c.getAccount().getId()).getClients());

        return this.standardCreateRecord(c);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateCard(@PathVariable("id") int id, @RequestBody Card c) {

        if (!this.isMyCard(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Card ca = this.service.read(c.getId());

        ca.setDayWithdrawLimit(c.getDayWithdrawLimit());
        ca.setDayPayLimit(c.getDayPayLimit());

        return this.standardUpdateRecord(id, ca);
    }

    @RequestMapping(value = "changestate/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> changeStateCard(@PathVariable("id") int id, @RequestBody Card c) {

        if (!this.isMyCard(c.getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Card ca = this.service.read(c.getId());

        if (ca.getActiveFlag() == 1) {
            ca.setActiveFlag(0);
        } else {
            ca.setActiveFlag(1);
        }

        return this.standardUpdateRecord(id, ca);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Card> readCard(@PathVariable("id") int id) {

        if (!this.isMyCard(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return this.standardReadRecord(id);
    }

    public boolean isMyCard(int id) {

        for (Account account : this.getClient().getAccounts()) {
            for (Card card : account.getCards()) {
                if (card.getId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

}
