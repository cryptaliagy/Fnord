package slng.fnord;

import java.util.LinkedList;
import java.util.List;

public class ServiceProvider extends User {
    public ServiceProvider() {

    }
    private List<String> services;
    private String SPAddress;
    private String SPPhoneNumber;
    private String SPCompanyName;
    private String SPBiography;

    public ServiceProvider(String email, String username, String password) {
        super(email, username, password, UserTypes.SERVICEPROVIDER);
        services = new LinkedList<>();
        SPAddress = "";
        SPPhoneNumber = "";
        SPCompanyName = "";
        SPBiography = "";
    }

    public void addService(String serviceName) {
        services.add(serviceName);
    }

    public void removeService(String serviceName) {
        services.remove(serviceName);
    }

    public void setServiceList(List<String> serviceList) {
        this.services = serviceList;
    }

    public List<String> getServiceList() {
        return services;
    }

    public String getSPAddress() {
        return SPAddress;
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
