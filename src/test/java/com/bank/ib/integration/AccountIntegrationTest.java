package com.bank.ib.integration;

import com.bank.ib.InternetBanking2Application;
import com.bank.ib.TestBeans;
import com.bank.ib.model.Account;
import com.bank.ib.model.Client;
import com.bank.ib.model.Country;
import com.bank.ib.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = InternetBanking2Application.class)
@Import(TestBeans.class)
@WebAppConfiguration
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser(username = "y", roles = {"CLIENT"})
public class AccountIntegrationTest {

    @Autowired
    private Country country;

    @Autowired
    private Client client;

    @Autowired
    private Account account;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private AccountTypeService accountTypeService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private WebApplicationContext wac;


    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.currencyService.create(account.getCurrency());
        this.accountTypeService.create(account.getAccountType());

        this.countryService.create(country);
    }

    @Test
    public void createAccountTest() throws Exception {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(account);

        this.mockMvc.perform(post("/main/account/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateAccountTest() throws Exception {

        this.accountService.create(account);

        client.setAccounts(new HashSet<>());
        client.getAccounts().add(account);
        this.clientService.create(client);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(account);

        this.mockMvc.perform(put("/main/account/" + account.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void readAccountTest() throws Exception {

        this.accountService.create(account);

        client.setAccounts(new HashSet<>());
        client.getAccounts().add(account);
        this.clientService.create(client);

        this.mockMvc.perform(get("/main/account/" + account.getId()))
                .andExpect(status().isOk());

    }


} 
