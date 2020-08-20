package com.bank.ib.dao;

import com.bank.ib.model.Client;

public interface ClientDao extends GenericDao<Client, Integer> {

    boolean canBeInserted(Client c);

    Client getClientBySsoId(String ssoId);
}
