package com.bank.ib.controller;

import com.bank.ib.model.Bank;
import com.bank.ib.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
public class BankController extends AbstractController<Bank> {

    @Autowired
    public BankController(@Qualifier("bankServiceImpl") GenericService<Bank, Integer> service) {

        super(service, Comparator.comparing(Bank::getBankName, String.CASE_INSENSITIVE_ORDER));
    }


    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @RequestMapping(value = "/main/bank/", method = RequestMethod.GET)
    public ResponseEntity<List<Bank>> getAllBankClient() {
        return this.standardGetAllRecord();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/bank/", method = RequestMethod.GET)
    public ResponseEntity<List<Bank>> getAllBank() {
        return this.standardGetAllRecord();
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/bank/", method = RequestMethod.POST)
    public ResponseEntity<Void> createBank(@RequestBody Bank b) {

        return this.standardCreateRecord(b);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/bank/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateBank(@PathVariable("id") int id, @RequestBody Bank b) {

        return this.standardUpdateRecord(id, b);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/bank/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeBank(@PathVariable("id") int id) {

        return this.standardRemoveRecord(id);
    }


}
