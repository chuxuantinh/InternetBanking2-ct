package com.bank.ib.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "IB_PAYMENT_INFO")
public class PaymentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "BANK_ID")
    private Bank bank;

    @Size(max = 6)
    @Column(name = "ACCOUNT_PREFIX")
    private String accountPrefix;

    @NotNull
    @Size(min = 10, max = 10)
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @NotNull
    private BigDecimal amount;

    @Size(max = 10)
    @Column(name = "VARIABLE_SYMBOL")
    private String variableSymbol;

    @Size(max = 10)
    @Column(name = "CONSTANT_SYMBOL")
    private String constantSymbol;

    @Size(max = 10)
    @Column(name = "SPECIFIC_SYMBOL")
    private String specificSymbol;

    @Size(max = 50)
    @Column(name = "MESSAGE_TO_RECIPIENT")
    private String messageToRecipient;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getVariableSymbol() {
        return variableSymbol;
    }

    public void setVariableSymbol(String variableSymbol) {
        this.variableSymbol = variableSymbol;
    }

    public String getConstantSymbol() {
        return constantSymbol;
    }

    public void setConstantSymbol(String constantSymbol) {
        this.constantSymbol = constantSymbol;
    }

    public String getSpecificSymbol() {
        return specificSymbol;
    }

    public void setSpecificSymbol(String specificSymbol) {
        this.specificSymbol = specificSymbol;
    }

    public String getMessageToRecipient() {
        return messageToRecipient;
    }

    public void setMessageToRecipient(String messageToRecipient) {
        this.messageToRecipient = messageToRecipient;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
