package com.bank.ib.model;

import com.bank.ib.enums.UserProfileType;

import javax.persistence.*;

@Entity
@Table(name = "IB_USER_PROFILE")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String type = UserProfileType.USER.getUserProfileType();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof UserProfile))
            return false;
        UserProfile other = (UserProfile) obj;
        if (!id.equals(other.id))
            return false;
        if (type == null) {
            return other.type == null;
        } else return type.equals(other.type);
    }

    @Override
    public String toString() {
        return "UserProfile [id=" + id + ",  type=" + type + "]";
    }


}