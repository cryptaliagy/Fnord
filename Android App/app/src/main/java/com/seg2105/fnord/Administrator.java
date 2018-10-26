package com.seg2105.fnord;

public class Administrator extends User {
    public Administrator(String username, String passwordHash,
                         String email, String firstName, String lastName) {
        super(username, passwordHash, email, firstName, lastName);
    }

    public Administrator() {

    }

    public Administrator (String username, String passwordHash) {
        super(username, passwordHash);
    }
}
