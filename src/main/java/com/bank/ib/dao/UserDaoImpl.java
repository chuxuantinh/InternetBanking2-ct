package com.bank.ib.dao;

import com.bank.ib.model.Client;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl implements UserDao  //extends AbstractDao<Integer, Client>
{

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public Client findBySSO(String sso) {

        Query query = entityManager.createQuery("select c from Client c where c.ssoId = :ssoId");
        query.setParameter("ssoId", sso);

        List<Client> results = query.getResultList();
        Client c = null;
        if (!results.isEmpty()) {
            c = results.get(0);
        }

        return c;

    }


}
