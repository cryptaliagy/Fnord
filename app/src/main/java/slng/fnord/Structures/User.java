package slng.fnord.Structures;

import java.security.SecureRandom;
import java.util.ArrayList;

import slng.fnord.Helpers.Common;
import slng.fnord.Helpers.Enums.UserTypes;
import slng.fnord.Helpers.Interfaces.Identifiable;

public abstract class User implements Identifiable {
    private String id;
    private String email;
    private String passwordHash;
    private String salt;
    private String address;
    private String phone;
    private UserTypes type;
    private ArrayList<String> bookings;


    // Used by the DB callback
    public User() {

    }

    public User (String email, String password, UserTypes type) {
        this.email = email;
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

    public UserTypes getType() {
        return type;
    }

    public String getPasswordHash() { return passwordHash; }

    public String getSalt() { return salt; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public void setEmail(String email) {  this.email = email; }

    public ArrayList<String> getBookings() {
        return bookings;
    }

    public void addBooking(String id) { bookings.add(id); }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
