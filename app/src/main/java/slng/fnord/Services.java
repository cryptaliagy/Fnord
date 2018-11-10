package slng.fnord;

import java.util.ArrayList;

public class Services {
    ArrayList<String> services = new ArrayList<>();
    ArrayList<String> serviceRates = new ArrayList<>();

    public Services() {
    }

    public ArrayList<String> getServices() {
        return services;
    }

    public ArrayList<String> getServiceRates() {
        return serviceRates;
    }

    //method to add a service
    public void addService(String service){
        services.add(service);
    }

    //method to add a service rate
    public void addServiceRate(String rate){
        serviceRates.add(rate);
    }

    //method to delete a service
    public void deleteService(String service){
        int indexToDelete = services.indexOf(service);
        services.remove(indexToDelete);
        serviceRates.remove(indexToDelete);
    }
}
