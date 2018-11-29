package slng.fnord.Structures;

import java.security.SecureRandom;

import slng.fnord.Helpers.Common;
import slng.fnord.Database.DBHelper;

public abstract class User {
    private String id;
    private String email;
    private String username;
    private String passwordHash;
    private String salt;
    private UserTypes type;


    // Used by the DB callback
    public User() {

    }

    public User (String email, String username, String password, UserTypes type) {
        this.email = email;
        this.username = username;
        byte[] saltBytes = new byte[20];
        new SecureRandom().nextBytes(saltBytes);

        salt = Common.makeHex(saltBytes);
        passwordHash = Common.makeMD5(password + salt);
        this.type = type;
        this.id = Common.makeMD5(email);
    }

    public boolean checkPassword (String password) {
        return Common.makeMD5(password + salt).equalsIgnoreCase(passwordHash);
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public UserTypes getType() {
        return type;
    }

    public String getPasswordHash() { return passwordHash; }

    public String getSalt() { return salt; }

    public String getId() { return id; }

    public void setEmail(String email) {  this.email = email; }

    protected void update() {
        DBHelper.updateUser(this);
    }
}
