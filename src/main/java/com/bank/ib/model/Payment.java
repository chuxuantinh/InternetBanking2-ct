package com.bank.ib.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "IB_PAYMENT")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "FROM_ACCOUNT_ID")
    private Account fromAccount;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "TO_ACCOUNT_ID")
    private Account toAccount;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PAYMENT_INFO_ID")
    private PaymentInfo paymentInfo;

    @Column(name = "PAYMENT_DATE")
    private Date paymentDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }


    @Override
    public String toString() {
        return id.toString();
    }

}
