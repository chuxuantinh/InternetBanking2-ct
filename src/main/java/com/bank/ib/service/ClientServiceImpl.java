package com.bank.ib.service;

import com.bank.ib.dao.ClientDao;
import com.bank.ib.dao.GenericDao;
import com.bank.ib.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class ClientServiceImpl extends GenericServiceImpl<Client, Integer>
        implements ClientService {

    private ClientDao clientDao;

    @Override
    public Client getClientBySsoId(String ssoId) {

        return clientDao.getClientBySsoId(ssoId);
    }

    public ClientServiceImpl() {
    }

    @Autowired
    public ClientServiceImpl(
            @Qualifier("clientDaoImpl") GenericDao<Client, Integer> genericDao) {
        super(genericDao);
        this.clientDao = (ClientDao) genericDao;
    }


}
