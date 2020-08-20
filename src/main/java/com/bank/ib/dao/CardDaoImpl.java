package com.bank.ib.dao;

import com.bank.ib.model.Card;
import org.springframework.stereotype.Repository;


@Repository
public class CardDaoImpl extends GenericDaoJpaImpl<Card, Integer>
        implements CardDao {

    @Override
    public boolean canBeInserted(Card c) {

        return this.recordNotExist("cardNumber", c.getCardNumber(), c.getId());
    }

}
