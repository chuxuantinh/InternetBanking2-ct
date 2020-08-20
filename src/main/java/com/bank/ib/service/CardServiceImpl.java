package com.bank.ib.service;

import com.bank.ib.dao.GenericDao;
import com.bank.ib.model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class CardServiceImpl extends GenericServiceImpl<Card, Integer>
        implements CardService {

    public CardServiceImpl() {
    }

    @Autowired
    public CardServiceImpl(
            @Qualifier("cardDaoImpl") GenericDao<Card, Integer> genericDao) {
        super(genericDao);
    }

    public void prepareCard(Card c) {

        long cnMin = 1000000000000000L;
        long cnMax = 9999999999999998L;
        int scMin = 100;
        int scMax = 998;

        long cardNumber = ThreadLocalRandom.current().nextLong(cnMin, cnMax + 1);
        int securityCode = ThreadLocalRandom.current().nextInt(scMin, scMax + 1);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 3);
        Date vt = cal.getTime();

        c.setCardNumber(String.valueOf(cardNumber));
        c.setSecurityCode(String.valueOf(securityCode));
        c.setValidityTo(vt);
        c.setActiveFlag(1);

    }

}