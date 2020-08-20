package com.bank.ib.controller;


import com.bank.ib.enums.State;
import com.bank.ib.enums.UserProfileType;
import com.bank.ib.model.Client;
import com.bank.ib.model.UserProfile;
import com.bank.ib.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;


@RestController
public class ClientController extends AbstractController<Client> {

    @Autowired
    public ClientController(@Qualifier("clientServiceImpl") GenericService<Client, Integer> service) {

        super(service, Comparator.comparing(Client::getLastName, String.CASE_INSENSITIVE_ORDER));
    }

    @RequestMapping(value = "/client/", method = RequestMethod.POST)
    public ResponseEntity<Void> createClient(@RequestBody Client a) {

        UserProfile upClient = new UserProfile();
        Set<UserProfile> ups = new HashSet<>();

        upClient.setId(2);
        upClient.setType(UserProfileType.CLIENT.getUserProfileType());

        a.setUserProfiles(null);
        ups.add(upClient);
        a.setUserProfiles(ups);
        a.setState(State.ACTIVE.getState());
        a.setAccountFavourites(null);
        a.setAccounts(null);
        return this.standardCreateRecord(a);
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @RequestMapping(value = "main/client/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateClient(@PathVariable("id") int id, @RequestBody Client a) {

        Client cl = this.getClient();

        cl.setEmail(a.getEmail());
        cl.setTelephoneNumber(a.getTelephoneNumber());
        cl.setPassword(a.getPassword());

        return this.standardUpdateRecord(id, cl);
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @RequestMapping(value = "main/client/{id}", method = RequestMethod.GET)
    public ResponseEntity<Client> readClient(@PathVariable("id") int id) {

        return new ResponseEntity<>(this.getClient(), HttpStatus.OK);
    }

}
