package com.bank.ib.service;

import com.bank.ib.model.Card;

public interface CardService extends GenericService<Card, Integer> {

    void prepareCard(Card c);

}
