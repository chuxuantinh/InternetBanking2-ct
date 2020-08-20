package com.bank.ib.service;


import com.bank.ib.model.Client;

public interface UserService {

    Client findBySso(String sso);

}
