package com.bank.ib.dao;


import com.bank.ib.model.Client;

public interface UserDao {

    Client findBySSO(String sso);

}
