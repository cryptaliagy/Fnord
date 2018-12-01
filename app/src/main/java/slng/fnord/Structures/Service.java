package slng.fnord.Structures;

import java.util.ArrayList;
import java.util.HashMap;

public class Service {
    private String serviceName;
    private double serviceRate;

    // HashMap holds 'true' if provider is certified, 'false' if not
    private HashMap<String, ServiceProviderMeta> providers;

    public Service() {

    }

    public Service(String serviceName, double serviceRate) {
        this.serviceName = serviceName;
        this.serviceRate = serviceRate;
        providers = new HashMap<>();
    }

    public void addProvider(ServiceProvider provider) {
        providers.put(provider.getCompany(), new ServiceProviderMeta(provider, provider.isCertified(serviceName)));
    }

    public void deleteProvider(String name) {
        providers.remove(name);
    }

    public boolean isProvider(String name) {
        if (!providers.containsKey(name)){
            return false;
        }

        return true;
    }

    public boolean providerIsCertified(String name) {
        if (!isProvider(name)) {
            return false;
        }

        return providers.get(name).isCertified();
    }

    public ArrayList<String> providerList() {
        return new ArrayList<>(providers.keySet());
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(double serviceRate) {
        this.serviceRate = serviceRate;
    }

    public HashMap<String, ServiceProviderMeta> getProviders() {
        return providers;
    }

    public void setProviders(HashMap<String, ServiceProviderMeta> providers) {
        this.providers = providers;
    }
}
