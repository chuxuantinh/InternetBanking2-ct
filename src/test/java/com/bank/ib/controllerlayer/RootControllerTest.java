package com.bank.ib.controllerlayer;

import com.bank.ib.InternetBanking2Application;
import com.bank.ib.controller.RootController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = InternetBanking2Application.class)
@WebAppConfiguration
public class RootControllerTest {

    @Autowired
    private RootController rootController;

    @Test
    public void rootTest() {
        Assertions.assertEquals(rootController.root(), "root");
    }

    @Test
    public void mainTest() {
        Assertions.assertEquals(rootController.main(), "main");
    }

    @Test
    public void adminTest() {
        Assertions.assertEquals(rootController.admin(), "admin");
    }

    @Test
    public void loginTest() {
        Assertions.assertEquals(rootController.login(), "login");
    }

    @Test
    public void registrationTest() {
        Assertions.assertEquals(rootController.registration(), "registration");
    }

} 
