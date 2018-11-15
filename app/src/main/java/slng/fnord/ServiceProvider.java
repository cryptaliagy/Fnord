package slng.fnord;

import java.util.LinkedList;
import java.util.List;

public class ServiceProvider extends User {
    private List<String> services;

    public ServiceProvider(String email, String username, String password) {
        super(email, username, password, UserTypes.SERVICEPROVIDER);
        services = new LinkedList<>();
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
}
