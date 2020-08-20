package com.bank.ib.integration;

import com.bank.ib.InternetBanking2Application;
import com.bank.ib.TestBeans;
import com.bank.ib.model.AccountFavourite;
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

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = InternetBanking2Application.class)
@Import(TestBeans.class)
@WebAppConfiguration
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser(username = "y", roles = {"CLIENT"})
public class AccountFavouriteIntegrationTest {


    @Autowired
    private AccountFavourite accountFavourite;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private BankService bankService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountFavouriteService accountFavouriteService;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.countryService.create(accountFavourite.getClient().getResidenceAddress().getCountry());
        this.bankService.create(accountFavourite.getPaymentInfo().getBank());
        this.clientService.create(accountFavourite.getClient());

        this.accountFavouriteService.create(accountFavourite);

        accountFavourite.getClient().setAccountFavourites(new ArrayList<>());
        accountFavourite.getClient().getAccountFavourites().add(accountFavourite);
        this.clientService.update(accountFavourite.getClient());
    }

    @Test
    public void updateAccountFavouriteTest() throws Exception {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(accountFavourite);

        this.mockMvc.perform(put("/main/accountfavourite/" + accountFavourite.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void removeAccountFavouriteTest() throws Exception {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(accountFavourite);

        this.mockMvc.perform(delete("/main/accountfavourite/" + accountFavourite.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNoContent());
    }

    @Test
    public void readAccountFavouriteTest() throws Exception {

        this.mockMvc.perform(get("/main/accountfavourite/" + accountFavourite.getId()))
                .andExpect(status().isOk());

    }


} 
