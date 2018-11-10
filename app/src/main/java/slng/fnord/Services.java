package slng.fnord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Services {

    private HashMap<String, Double> services = new HashMap<>();

    public Double getServiceRate(String service) {
        return services.get(service);
    }

    public void updateService(String service, double newRate) {
        services.put(service, newRate);
    }

    //method to add a service
    public void addService(String service, double rate){
        services.put(service, rate);
    }

    //method to delete a service
    public void deleteService(String service){
        services.remove(service);
    }

    public boolean hasService(String service){
        return services.containsKey(service);
    }

    public ArrayList<String> asArrayList() {
        return new ArrayList<>(services.keySet());
    }

    public int size() {
        return services.size();
    }
}
