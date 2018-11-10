package slng.fnord;
//
import android.accounts.Account;

import java.util.ArrayList;
//
//The accounts class will keep sets of arraylists for emails, usernames and passwords for each account type (admin, homeowner, service provider)

public class Accounts {
    //booleam adminFlag set to false - once an admin account is created, flag is set to true and no other admin accounts may be created
    boolean adminFlag = false;
    //admin
    String[] adminEmail = new String[1];
    String[] adminUsername = new String[1];
    String[] adminPassword = new String[1];

    //homeowners
    ArrayList<String> homeOwnerEmails = new ArrayList<String>();
    ArrayList<String> homeOwnerUsernames = new ArrayList<String>();
    ArrayList<String> homeOwnerPasswords = new ArrayList<String>();

    //service providers
    ArrayList<String> serviceProviderEmails = new ArrayList<String>();
    ArrayList<String> serviceProviderUsernames = new ArrayList<String>();
    ArrayList<String> serviceProviderPasswords = new ArrayList<String>();

    public Accounts() {
    }
    public Accounts getAcc(){
        return this;
    }

    //public methods to add admin account
    public void addAdminEmail(String email){
        adminEmail[0] = email;
    }
    public void addAdminUsername(String username){
        adminUsername[0] = username;
    }
    public void addAdminPassword(String password){
        adminPassword[0] = password;
    }

    //public methods to add homeowner account
    public void addHomeOwnerEmail(String email){
        homeOwnerEmails.add(email);
    }
    public void addHomeOwnerUsernames(String username){
        homeOwnerUsernames.add(username);
    }
    public void addHomeOwnerPassword(String password){
        homeOwnerPasswords.add(password);
    }

    //public methods to add service provider account
    public void addSPEmail(String email){
        serviceProviderEmails.add(email);
    }
    public void addSPUsernames(String username){
        serviceProviderUsernames.add(username);
    }
    public void addSPPassword(String password){
        serviceProviderPasswords.add(password);
    }


    //getters
    public String getAdminEmail() {
        return adminEmail[0];
    }

    public String getAdminUsername() {
        return adminUsername[0];
    }

    public String getAdminPassword() {
        return adminPassword[0];
    }

    public ArrayList<String> getHomeOwnerEmails() {
        return homeOwnerEmails;
    }

    public ArrayList<String> getHomeOwnerUsernames() {
        return homeOwnerUsernames;
    }

    public ArrayList<String> getHomeOwnerPasswords() {
        return homeOwnerPasswords;
    }

    public ArrayList<String> getServiceProviderEmails() {
        return serviceProviderEmails;
    }

    public ArrayList<String> getServiceProviderUsernames() {
        return serviceProviderUsernames;
    }

    public ArrayList<String> getServiceProviderPasswords() {
        return serviceProviderPasswords;
    }
}
