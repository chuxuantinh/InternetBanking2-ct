package com.bank.ib;

import com.bank.ib.model.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Date;


@TestConfiguration
public class TestBeans {

    public static final int testID = 10;

    @Bean
    public Country countryTestGenerator() {
        Country countryTest = new Country();
        countryTest.setCountryName("Test_1");
        return countryTest;
    }

    @Bean
    public Currency currencyTestGenerator() {
        Currency currencyTest = new Currency();
        currencyTest.setCurrencyName("Test_1");
        currencyTest.setSymbol("x");
        return currencyTest;
    }

    @Bean
    public Bank bankTestGenerator() {
        Bank bankTest = new Bank();
        bankTest.setBankName("Test_1");
        bankTest.setCode("1234");
        bankTest.setSwift("x");
        return bankTest;
    }

    @Bean
    public AccountType accountTypeGenerator() {
        AccountType accountTypeTest = new AccountType();
        accountTypeTest.setAccountTypeName("Test_1");
        accountTypeTest.setInterestRate(1.2);
        return accountTypeTest;
    }

    @Bean
    public Announcement announcementGenerator() {
        Announcement announcementTest = new Announcement();
        announcementTest.setHeader("Test_1");
        announcementTest.setMainText("x");
        announcementTest.setImportance(1);
        announcementTest.setInsertDate(new Date());
        return announcementTest;
    }

    @Bean
    public PaymentInfo paymentInfoGenerator() {
        PaymentInfo paymentInfoTest = new PaymentInfo();
        paymentInfoTest.setBank(bankTestGenerator());
        paymentInfoTest.setAccountNumber("1234567891");
        paymentInfoTest.setAmount(new BigDecimal("64.5"));
        return paymentInfoTest;
    }

    @Bean
    public Address addressGenerator() {
        Address addressTest = new Address();
        addressTest.setCountry(countryTestGenerator());
        addressTest.setStreet("Test_1");
        addressTest.setCity("x");
        return addressTest;
    }

    @Bean
    public Account accountGenerator() {
        Account accountTest = new Account();
        accountTest.setAccountType(accountTypeGenerator());
        accountTest.setCurrency(currencyTestGenerator());
        accountTest.setAccountNumber("1234567891");
        accountTest.setAccountName("Test_1");
        accountTest.setBalance(new BigDecimal("0.0"));
        return accountTest;
    }

    @Bean
    public Card cardGenerator() {
        Card cardTest = new Card();
        cardTest.setAccount(accountGenerator());
        cardTest.setSecurityCode("123");
        cardTest.setCardNumber("1234512345123456");
        cardTest.setActiveFlag(1);
        cardTest.setDayWithdrawLimit(new BigDecimal("10000.0"));
        cardTest.setDayPayLimit(new BigDecimal("10000.0"));
        cardTest.setValidityTo(new Date());
        return cardTest;
    }

    @Bean
    public Payment paymentGenerator() {
        Payment paymentTest = new Payment();
        paymentTest.setFromAccount(accountGenerator());
        paymentTest.setToAccount(accountGenerator());
        paymentTest.setPaymentInfo(paymentInfoGenerator());
        paymentTest.setPaymentDate(new Date());
        return paymentTest;
    }

    @Bean
    public PaymentScheduled paymentScheduledGenerator() {
        PaymentScheduled paymentScheduledTest = new PaymentScheduled();
        paymentScheduledTest.setAccount(accountGenerator());
        paymentScheduledTest.setPaymentInfo(paymentInfoGenerator());
        paymentScheduledTest.setPeriode("MONTHLY");
        paymentScheduledTest.setPaymentDate(new Date());
        paymentScheduledTest.setEndDate(new Date());
        return paymentScheduledTest;
    }

    @Bean
    public Client clientGenerator() {
        Client clientTest = new Client();
        clientTest.setResidenceAddress(addressGenerator());
        clientTest.setFirstName("Test_1");
        clientTest.setLastName("x");
        clientTest.setPersonalNumber("x");
        clientTest.setEmail("x");
        clientTest.setTelephoneNumber("x");
        clientTest.setSsoId("y");
        clientTest.setPassword("y");
        clientTest.setState("x");
        return clientTest;
    }

    @Bean
    public AccountFavourite accountFavouriteGenerator() {
        AccountFavourite accountFavouriteTest = new AccountFavourite();
        accountFavouriteTest.setClient(clientGenerator());
        accountFavouriteTest.setPaymentInfo(paymentInfoGenerator());
        accountFavouriteTest.setAccountFavouriteName("Test_1");
        return accountFavouriteTest;
    }

}
