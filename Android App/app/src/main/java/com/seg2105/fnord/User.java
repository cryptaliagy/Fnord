package com.seg2105.fnord;

import java.util.Date;

public abstract class User {
    private String username;
    private String passwordHash;
    private String email;
    private String firstName;
    private String lastName;
    private Date lastLoginDate; // TODO: Setup login date tracking logic

    public User () {

    }

    public User(String username, String passwordHash, String email,
                String firstName, String lastName) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }
}
