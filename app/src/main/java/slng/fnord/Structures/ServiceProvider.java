package slng.fnord.Structures;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import slng.fnord.Helpers.Common;
import slng.fnord.Helpers.Pair;

public class ServiceProvider extends User {
    private HashMap<String, Pair<Boolean, String>> services;
    private String phone;
    private String address;
    private String company;
    private String biography;
    private HashMap<String, Pair<String, String>> availability;

    // Necessary for DB
    public ServiceProvider() {

    }

    public ServiceProvider(String email, String username, String password) {
        super(email, username, password, UserTypes.SERVICEPROVIDER);
        services = new HashMap<>();
        availability = Common.makeBlankAvail();
    }

    public void addService(String serviceName, boolean certified) {
        if (services == null) {
            services = new HashMap<>();
        }
        services.put(serviceName, new Pair<>(certified, ""));
    }

    public void addService(String serviceName) {
        addService(serviceName, false);
    }

    public void removeService(String serviceName) {
        services.remove(serviceName);
    }

    public List<String> getServiceList() {
        if (services == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(services.keySet());
    }

    public HashMap<String, Pair<Boolean, String>> getServices() {
        return services;
    }

    public void updateCertified(String serviceName, boolean certified) {
        Pair<Boolean, String> info = services.get(serviceName);

        if (info != null) {
            Pair<Boolean, String> newInfo = new Pair<>(certified, info.second);
        }
    }

    public boolean isCertified(String serviceName) {
        if (!services.containsKey(serviceName)) {
            return false;
        }

        return services.get(serviceName).first;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBio() {
        return biography;
    }

    public void setBio(String biography) {
        this.biography = biography;
    }

    public void setAvailability(HashMap<String, Pair<String, String>> availability) {
        this.availability = availability;
    }

    public HashMap<String, Pair<String, String>> getAvailability() {
        return this.availability;
    }

    public Pair<String, String> getDayAvailability(String day) {
        return availability.get(day);
    }


}
