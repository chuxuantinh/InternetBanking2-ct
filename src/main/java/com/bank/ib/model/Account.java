package com.bank.ib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "IB_ACCOUNT")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ACCOUNT_TYPE_C_ID")
    private AccountType accountType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CURRENCY_C_ID")
    private Currency currency;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    @NotNull
    private BigDecimal balance;

    @Size(max = 6)
    @Pattern(regexp = "^[0-9]*$")
    @Column(name = "ACCOUNT_PREFIX")
    private String accountPrefix;

    @NotNull
    @Size(min = 10, max = 10)
    @Pattern(regexp = "^[0-9]*$")
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore //Due to an error Infinite recursion Jackson
    @Fetch(value = FetchMode.SUBSELECT) //Due to an error Hibernate cannot simultaneously fetch multiple bags
    private List<Card> cards;

    @OneToMany(mappedBy = "fromAccount", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Payment> payments;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    @Fetch(value = FetchMode.SUBSELECT)
    private List<PaymentScheduled> paymentsScheduled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "IB_M_CLIENT_ACCOUNT",
            joinColumns = {@JoinColumn(name = "ACCOUNT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "CLIENT_ID")})
    private Set<Client> clients;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAccountPrefix() {
        return accountPrefix;
    }

    public void setAccountPrefix(String accountPrefix) {
        this.accountPrefix = accountPrefix;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<PaymentScheduled> getPaymentsScheduled() {
        return paymentsScheduled;
    }

    public void setPaymentsScheduled(List<PaymentScheduled> paymentsScheduled) {
        this.paymentsScheduled = paymentsScheduled;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", accountType=" + accountType + ", currency=" + currency + ", accountName="
                + accountName + ", balance=" + balance + ", accountPrefix=" + accountPrefix + ", accountNumber="
                + accountNumber + ", cards=" + cards + ", payments=" + payments + ", paymentsScheduled=" + paymentsScheduled
                + ", clients=" + clients + "]";
    }

}
