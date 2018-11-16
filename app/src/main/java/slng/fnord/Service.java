package slng.fnord;

import java.util.ArrayList;
import java.util.HashMap;

public class Service {
    private String serviceName;
    private double serviceRate;

    // HashMap holds 'true' if provider is certified, 'false' if not
    private HashMap<String, Boolean> providers;

    public Service(String serviceName, double serviceRate) {
        this.serviceName = serviceName;
        this.serviceRate = serviceRate;
    }

    public void addProvider(String name, boolean verified) {
        providers.put(name, verified);
    }

    public void deleteProvider(String name) {
        providers.remove(name);
    }

    public boolean isProvider(String name) {
        Boolean checked = providers.get(name);
        if (checked == null) {
            return false;
        }
        return true;
    }

    public boolean providerIsCertified(String name) {
        if (!isProvider(name)) {
            return false;
        }

        return providers.get(name);
    }

    public ArrayList<String> providerList() {
        return null;
    }
}