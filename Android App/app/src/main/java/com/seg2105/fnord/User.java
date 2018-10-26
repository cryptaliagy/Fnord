package com.seg2105.fnord;

import java.util.Date;

public abstract class User {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Date lastLoginDate; // TODO: Setup login date tracking logic
    public enum AccountType { HOMEOWNER, ADMIN, SERVICEPROVIDER }
    private AccountType type;

    public User () {

    }

    public User(String username,  String email,
                String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String username, String email, AccountType type ) {
        this.username = username;
        this.type = type;
    }
}
