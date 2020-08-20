package com.bank.ib.service;

import com.bank.ib.model.Client;

public interface ClientService extends GenericService<Client, Integer> {

    Client getClientBySsoId(String ssoId);
}
