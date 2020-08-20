package com.bank.ib.integration;

import com.bank.ib.InternetBanking2Application;
import com.bank.ib.TestBeans;
import com.bank.ib.model.Client;
import com.bank.ib.model.Country;
import com.bank.ib.model.PaymentScheduled;
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
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = InternetBanking2Application.class)
@Import(TestBeans.class)
@WebAppConfiguration
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser(username = "y", roles = {"CLIENT"})
public class PaymentScheduledIntegrationTest {

    @Autowired
    private Country c;

    @Autowired
    private Client client;

    @Autowired
    private PaymentScheduled paymentScheduled;

    @Autowired
    private BankService bankService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private AccountTypeService accountTypeService;

    @Autowired
    private PaymentScheduledService paymentScheduledService;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.currencyService.create(paymentScheduled.getAccount().getCurrency());
        this.accountTypeService.create(paymentScheduled.getAccount().getAccountType());
        this.bankService.create(paymentScheduled.getPaymentInfo().getBank());
        this.accountService.create(paymentScheduled.getAccount());
        this.paymentScheduledService.create(paymentScheduled);

        this.countryService.create(c);

        client.setAccounts(new HashSet<>());
        client.getAccounts().add(paymentScheduled.getAccount());
        this.clientService.create(client);

        paymentScheduled.getAccount().setPaymentsScheduled(new ArrayList<>());
        paymentScheduled.getAccount().getPaymentsScheduled().add(paymentScheduled);
        this.accountService.update(paymentScheduled.getAccount());
    }

    @Test
    public void updatePaymentScheduledTest() throws Exception {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(paymentScheduled);

        this.mockMvc.perform(put("/main/paymentscheduled/" + paymentScheduled.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void removePaymentScheduledTest() throws Exception {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(paymentScheduled);

        this.mockMvc.perform(delete("/main/paymentscheduled/" + paymentScheduled.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNoContent());
    }

    @Test
    public void readPaymentScheduledTest() throws Exception {

        this.mockMvc.perform(get("/main/paymentscheduled/" + paymentScheduled.getId()))
                .andExpect(status().isOk());

    }


} 
