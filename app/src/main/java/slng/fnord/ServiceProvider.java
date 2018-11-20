package slng.fnord;

import java.util.LinkedList;
import java.util.List;

public class ServiceProvider extends User {
    private class SPService {
        private String name;
        private boolean isCertified;

        private SPService() {

        }

        private SPService(String name, boolean certified) {
            this.name = name;
            this.isCertified = certified;
        }

        public boolean isEqual(SPService other) {
            return other.name.equalsIgnoreCase(this.name) && this.isCertified == other.isCertified;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isCertified() {
            return isCertified;
        }

        public void setCertified(boolean certified) {
            isCertified = certified;
        }
    }


    private List<SPService> services;
    private String phone;
    private String address;



    // Necessary for DB
    public ServiceProvider() {

    }

    public ServiceProvider(String email, String username, String password) {
        super(email, username, password, UserTypes.SERVICEPROVIDER);
        services = new LinkedList<>();
        SPAddress = "";
        SPPhoneNumber = "";
        SPCompanyName = "";
        SPBiography = "";
    }

    public void addService(String serviceName, boolean certified) {
        services.add(new SPService(serviceName, certified));
    }

    public void removeService(String serviceName) {
        services.remove(serviceName);
    }

    public List<SPService> getServiceList() {
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
