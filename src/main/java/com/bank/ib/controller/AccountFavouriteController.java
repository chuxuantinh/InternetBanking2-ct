package com.bank.ib.controller;


import com.bank.ib.model.Account;
import com.bank.ib.model.AccountFavourite;
import com.bank.ib.service.GenericService;
import com.bank.ib.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;


@RestController
@RequestMapping(value = "/main/accountfavourite/")
@PreAuthorize("hasRole('ROLE_CLIENT')")
public class AccountFavouriteController extends AbstractController<AccountFavourite> {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    public AccountFavouriteController(@Qualifier("accountFavouriteServiceImpl") GenericService<AccountFavourite, Integer> service) {

        super(service, Comparator.comparing(AccountFavourite::getAccountFavouriteName, String.CASE_INSENSITIVE_ORDER));

    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AccountFavourite>> getAllClientAccountFavourite() {

        List<AccountFavourite> entities = this.getClient().getAccountFavourites();

        entities.sort(comp);

        if (entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        for (AccountFavourite af : entities) {
            af.setClient(null);
        }

        return new ResponseEntity<>(entities, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createAccountFavourite(@RequestBody AccountFavourite a) {

        Account favAccount;

        a.setClient(getClient());

        favAccount = this.paymentService.getAccountByNumber(a.getPaymentInfo().getAccountPrefix(), a.getPaymentInfo().getAccountNumber());

        if (favAccount == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        service.create(a);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateAccountFavourite(@PathVariable("id") int id, @RequestBody AccountFavourite a) {

        if (!this.isMyAccountFavourite(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        a.setClient(getClient());

        return this.standardUpdateRecord(id, a);
    }


    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeAccountFavourite(@PathVariable("id") int id) {

        if (!this.isMyAccountFavourite(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return this.standardRemoveRecord(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<AccountFavourite> readAccountFavourite(@PathVariable("id") int id) {

        if (!this.isMyAccountFavourite(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        AccountFavourite af = this.service.read(id);

        if (af == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        af.setClient(null);
        return new ResponseEntity<>(af, HttpStatus.OK);
    }

    public boolean isMyAccountFavourite(int id) {

        for (AccountFavourite accountFavourite : this.getClient().getAccountFavourites()) {
            if (accountFavourite.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

}
