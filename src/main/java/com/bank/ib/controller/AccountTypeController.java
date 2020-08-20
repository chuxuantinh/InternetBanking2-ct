package com.bank.ib.controller;

import com.bank.ib.model.AccountType;
import com.bank.ib.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
public class AccountTypeController extends AbstractController<AccountType> {

    @Autowired
    public AccountTypeController(@Qualifier("accountTypeServiceImpl") GenericService<AccountType, Integer> service) {

        super(service, Comparator.comparing(AccountType::getAccountTypeName, String.CASE_INSENSITIVE_ORDER));
    }


    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @RequestMapping(value = "/main/accounttype/", method = RequestMethod.GET)
    public ResponseEntity<List<AccountType>> getAllAccountTypeClient() {
        return this.standardGetAllRecord();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/accounttype/", method = RequestMethod.GET)
    public ResponseEntity<List<AccountType>> getAllAccountType() {
        return this.standardGetAllRecord();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/accounttype/", method = RequestMethod.POST)
    public ResponseEntity<Void> createAccountType(@RequestBody AccountType ac) {

        return this.standardCreateRecord(ac);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/accounttype/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateAccountType(@PathVariable("id") int id, @RequestBody AccountType ac) {

        return this.standardUpdateRecord(id, ac);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/accounttype/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeAccountType(@PathVariable("id") int id) {

        return this.standardRemoveRecord(id);
    }


}
