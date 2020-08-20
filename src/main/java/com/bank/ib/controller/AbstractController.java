package com.bank.ib.controller;

import com.bank.ib.model.Account;
import com.bank.ib.model.Client;
import com.bank.ib.service.ClientService;
import com.bank.ib.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractController<T> {

    protected final GenericService<T, Integer> service;
    protected final Comparator<T> comp;

    @Autowired
    protected ClientService cs;

    protected AbstractController(GenericService<T, Integer> se, Comparator<T> com) {
        this.service = se;
        this.comp = com;
    }


    protected ResponseEntity<List<T>> standardGetAllRecord() {

        List<T> entities = service.getAll();
        entities.sort(comp);

        if (entities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(entities, HttpStatus.OK);

    }

    protected ResponseEntity<Void> standardCreateRecord(T entity) {

        if (!service.canBeInserted(entity)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        service.create(entity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    protected ResponseEntity<Void> standardUpdateRecord(int id, T entity) {

        if (!service.canBeInserted(entity)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if (service.read(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        service.update(entity);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    protected ResponseEntity<Void> standardRemoveRecord(int id) {

        T entity = service.read(id);

        if (!service.canBeDeleted(entity)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        service.remove(entity);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    protected ResponseEntity<T> standardReadRecord(int id) {

        T entity = service.read(id);

        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }


    protected Client getClient() {
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }

        return this.cs.getClientBySsoId(userName);

    }

    public boolean isMyAccount(int id) {

        for (Account account : this.getClient().getAccounts()) {
            if (account.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }


}
