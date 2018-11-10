package slng.fnord;
//
import android.accounts.Account;

import java.util.ArrayList;
import java.util.HashMap;
//
//The accounts class will keep sets of arraylists for emails, usernames and passwords for each account type (admin, homeowner, service provider)

public class Accounts {
    //admin
    private User admin = null;

    //homeowners
    HashMap<String, User> homeOwnerData = new HashMap<>();

    //service providers
    HashMap<String, User> serviceProviderData = new HashMap<>();

    public Accounts() {
    }
    public Accounts getAcc(){
        return this;
    }

    //public methods to add admin account
    public void makeAdminUser(String email, String username, String password) {
        admin = new User(email, username, password);
    }

    //public methods to add homeowner account
    public void addHomeOwnerInfo(String email, String username, String password) {
        String id = Common.makeMD5(username);
        User user = new User(email, username, password);
        homeOwnerData.put(id, user);
    }

    //public methods to add service provider account
    public void addServiceProviderInfo(String email, String username, String password) {
        String id = Common.makeMD5(username);
        User user = new User(email, username, password);
        serviceProviderData.put(id, user);
    }

    //getters
    public boolean existsAdmin() {
        return admin != null;
    }
}
