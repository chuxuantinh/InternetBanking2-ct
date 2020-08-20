package com.bank.ib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "IB_CLIENT")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RESIDENCE_ADDRESS_ID")
    private Address residenceAddress;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "LAST_NAME")
    private String lastName;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PERSONAL_NUMBER")
    private String personalNumber;

    @NotNull
    @Size(min = 1, max = 50)
    private String email;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TELEPHONE_NUMBER")
    private String telephoneNumber;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "SSO_ID", unique = true)
    private String ssoId;

    @NotNull
    @Size(min = 1, max = 100)
    private String password;

    @NotNull
    @Size(min = 1, max = 30)
    private String state;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<AccountFavourite> accountFavourites;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "IB_M_USER_USER_PROFILE",
            joinColumns = {@JoinColumn(name = "CLIENT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_PROFILE_ID")})
    private Set<UserProfile> userProfiles;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "clients")
    private Set<Account> accounts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Address getResidenceAddress() {
        return residenceAddress;
    }

    public void setResidenceAddress(Address residenceAddress) {
        this.residenceAddress = residenceAddress;
    }

    public List<AccountFavourite> getAccountFavourites() {
        return accountFavourites;
    }

    public void setAccountFavourites(List<AccountFavourite> accountFavourites) {
        this.accountFavourites = accountFavourites;
    }

    public Set<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public void setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ssoId == null) ? 0 : ssoId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Client other = (Client) obj;
        if (ssoId == null) {
            return other.ssoId == null;
        } else return ssoId.equals(other.ssoId);
    }

    @Override
    public String toString() {
        return "Client [id=" + id + ","
                + " firstName=" + firstName + ", lastName="
                + lastName + ", personalNumber=" + personalNumber + ", email=" + email + ", telephoneNumber="
                + telephoneNumber + ", ssoId=" + ssoId + ", password=" + password + ", state=" + state
                + "]";
    }

}
