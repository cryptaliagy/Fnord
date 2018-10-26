package com.seg2105.fnord;

import java.util.ArrayList;

public class ServiceProvider extends User {
    private String companyName;
    private ArrayList<String> services;

    public ServiceProvider() {

    }

    public ServiceProvider (String username, String passwordHash,
                            String email, String firstName, String lastName) {
        super(username, email, firstName, lastName);
    }

    public ServiceProvider (String username, String email) {
        super(username, email, AccountType.SERVICEPROVIDER);
    }
}
