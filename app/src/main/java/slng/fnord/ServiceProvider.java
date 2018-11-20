package slng.fnord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ServiceProvider extends User {
    private HashMap<String, Boolean> services;
    private String phone;
    private String address;



    // Necessary for DB
    public ServiceProvider() {

    }

    public ServiceProvider(String email, String username, String password) {
        super(email, username, password, UserTypes.SERVICEPROVIDER);
        services = new HashMap<>();
    }

    public void addService(String serviceName, boolean certified) {
        services.put(serviceName, certified);
        update();
    }

    public void removeService(String serviceName) {
        services.remove(serviceName);
        update();
    }

    public List<String> getServiceList() {
        return new ArrayList<>(services.keySet());

    }

    public void updateCertified(String serviceName, boolean certified) {
        addService(serviceName, certified);
    }

    public void setSPAddress(String SPAddress) {
        this.SPAddress = SPAddress;
    }

    public String getSPPhoneNumber() {
        return SPPhoneNumber;
    }

    public void setSPPhoneNumber(String SPPhoneNumber) {
        this.SPPhoneNumber = SPPhoneNumber;
    }

    public String getSPCompanyName() {
        return SPCompanyName;
    }

    public void setSPCompanyName(String SPCompanyName) {
        this.SPCompanyName = SPCompanyName;
    }

    public String getSPBiography() {
        return SPBiography;
    }

    public void setSPBiography(String SPBiography) {
        this.SPBiography = SPBiography;
    }
}
