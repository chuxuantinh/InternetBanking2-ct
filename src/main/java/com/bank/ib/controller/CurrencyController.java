package com.bank.ib.controller;

import com.bank.ib.model.Currency;
import com.bank.ib.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
public class CurrencyController extends AbstractController<Currency> {

    @Autowired
    public CurrencyController(@Qualifier("currencyServiceImpl") GenericService<Currency, Integer> service) {

        super(service, Comparator.comparing(Currency::getCurrencyName, String.CASE_INSENSITIVE_ORDER));
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @RequestMapping(value = "/main/currency/", method = RequestMethod.GET)
    public ResponseEntity<List<Currency>> getAllCurrencyClient() {
        return this.standardGetAllRecord();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/currency/", method = RequestMethod.GET)
    public ResponseEntity<List<Currency>> getAllCurrency() {
        return this.standardGetAllRecord();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/currency/", method = RequestMethod.POST)
    public ResponseEntity<Void> createCurrency(@RequestBody Currency c) {

        return this.standardCreateRecord(c);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/currency/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateCurrency(@PathVariable("id") int id, @RequestBody Currency c) {

        return this.standardUpdateRecord(id, c);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/currency/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeCurrency(@PathVariable("id") int id) {

        return this.standardRemoveRecord(id);
    }


}
