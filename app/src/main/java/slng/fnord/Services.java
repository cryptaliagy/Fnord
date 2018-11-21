package slng.fnord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Services {

    public Services() {

    }

    private HashMap<String, Service> services = new HashMap<>();

    public Double getServiceRate(String service) {
        return services.get(service).getServiceRate();
    }

    //method to add a service
    public void addService(String service, double rate) {
        services.put(Common.makeMD5(service), new Service(service, rate));
    }

    public void addService(Service service) {
        services.put(Common.makeMD5(service.getServiceName()), service);
    }

    //method to delete a service
    public void deleteService(String service) {
        services.remove(Common.makeMD5(service));
    }

    public boolean hasService(String service) {
        return services.containsKey(Common.makeMD5(service));
    }

    public ArrayList<String> asArrayList() {
        ArrayList<Service> serviceList = new ArrayList<>(services.values());
        ArrayList<String> names = new ArrayList<>();
        for (Service service : serviceList) {
            names.add(service.getServiceName());
        }

        return names;
    }

    public int size() {
        return services.size();
    }

    public Service getService(String id) {
        return services.get(id);
    }

    public HashMap<String, Service> getServices() {
        return services;
    }

    public void setServices(HashMap<String, Service> services) {
        this.services = services;
    }
}
