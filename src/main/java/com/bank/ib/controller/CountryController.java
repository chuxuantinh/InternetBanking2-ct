package com.bank.ib.controller;


import com.bank.ib.model.Country;
import com.bank.ib.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;


@RestController
public class CountryController extends AbstractController<Country> {

    @Autowired
    public CountryController(@Qualifier("countryServiceImpl") GenericService<Country, Integer> service) {

        super(service, Comparator.comparing(Country::getCountryName, String.CASE_INSENSITIVE_ORDER));
    }

    @RequestMapping(value = "/country/", method = RequestMethod.GET)
    public ResponseEntity<List<Country>> getAllCountryClient() {
        return this.standardGetAllRecord();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/country/", method = RequestMethod.GET)
    public ResponseEntity<List<Country>> getAllCountry() {
        return this.standardGetAllRecord();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/country/", method = RequestMethod.POST)
    public ResponseEntity<Void> createCountry(@RequestBody Country c) {

        return this.standardCreateRecord(c);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/country/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateCountry(@PathVariable("id") int id, @RequestBody Country c) {

        return this.standardUpdateRecord(id, c);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/country/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeCountry(@PathVariable("id") int id) {

        return this.standardRemoveRecord(id);
    }

}
