package com.bank.ib.service;

import com.bank.ib.dao.UserDao;
import com.bank.ib.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    public Client findBySso(String sso) {
        return dao.findBySSO(sso);
    }

}
