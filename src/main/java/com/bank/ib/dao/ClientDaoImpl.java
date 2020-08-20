package com.bank.ib.dao;

import com.bank.ib.model.Client;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;


@Repository
public class ClientDaoImpl extends GenericDaoJpaImpl<Client, Integer>
        implements ClientDao {


    @Override
    public boolean canBeInserted(Client c) {

        return this.recordNotExist("ssoId", c.getSsoId(), c.getId());
    }

    @Override
    public Client getClientBySsoId(String ssoId) {

        Query sel = this.entityManager.createQuery("Select t from Client t where t.ssoId = '" + ssoId + "'"); //and t.accountPrefix = '" + acPrefix + "'");

        sel.setMaxResults(1);
        @SuppressWarnings("unchecked")
        List<Client> list = sel.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);

    }

}
