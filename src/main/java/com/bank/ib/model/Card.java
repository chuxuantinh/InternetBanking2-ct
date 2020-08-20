package com.bank.ib.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "IB_CARD")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @NotNull
    @Size(min = 16, max = 16)
    @Column(name = "CARD_NUMBER")
    private String cardNumber;

    @NotNull
    @Size(min = 3, max = 3)
    @Column(name = "SECURITY_CODE")
    private String securityCode;

    @NotNull
    @Column(name = "VALIDITY_TO")
    private Date validityTo;

    @NotNull
    @Min(0)
    @Max(1)
    @Column(name = "ACTIVE_FLAG")
    private Integer activeFlag;

    @NotNull
    @Column(name = "DAY_WITHDRAW_LIMIT")
    private BigDecimal dayWithdrawLimit;

    @NotNull
    @Column(name = "DAY_PAY_LIMIT")
    private BigDecimal dayPayLimit;


    public void setDayWithdrawLimit(BigDecimal dayWithdrawLimit) {
        this.dayWithdrawLimit = dayWithdrawLimit;
    }

    public BigDecimal getDayPayLimit() {
        return dayPayLimit;
    }

    public void setDayPayLimit(BigDecimal dayPayLimit) {
        this.dayPayLimit = dayPayLimit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public Date getValidityTo() {
        return validityTo;
    }

    public void setValidityTo(Date validityTo) {
        this.validityTo = validityTo;
    }

    public Integer getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Integer activeFlag) {
        this.activeFlag = activeFlag;
    }

    public BigDecimal getDayWithdrawLimit() {
        return dayWithdrawLimit;
    }

    @Override
    public String toString() {
        return "Card [id=" + id + ", account=" + account + ", cardNumber=" + cardNumber + ", securityCode=" + securityCode
                + ", validityTo=" + validityTo + ", activeFlag=" + activeFlag + ", dayWithdrawLimit=" + dayWithdrawLimit
                + ", dayPayLimit=" + dayPayLimit + "]";
    }

}
