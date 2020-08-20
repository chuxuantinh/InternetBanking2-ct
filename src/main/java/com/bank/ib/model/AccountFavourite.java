package com.bank.ib.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "IB_ACCOUNT_FAVOURITE")
public class AccountFavourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PAYMENT_INFO_ID")
    private PaymentInfo paymentInfo;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ACCOUNT_FAVOURITE_NAME")
    private String accountFavouriteName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public String getAccountFavouriteName() {
        return accountFavouriteName;
    }

    public void setAccountFavouriteName(String accountFavouriteName) {
        this.accountFavouriteName = accountFavouriteName;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
