package com.bank.ib.enums;

public enum UserProfileType {
    CLIENT("CLIENT"),
    USER("USER"),
    DBA("DBA"),
    ADMIN("ADMIN");

    final String userProfileType;

    UserProfileType(String userProfileType) {
        this.userProfileType = userProfileType;
    }

    public String getUserProfileType() {
        return userProfileType;
    }

}
