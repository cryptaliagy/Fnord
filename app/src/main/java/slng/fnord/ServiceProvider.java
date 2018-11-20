package slng.fnord;


import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ServiceProvider extends User {
    private HashMap<String, Pair<Boolean, String>> services;
    private String phone;
    private String address;
    private String company;
    private String biography;



    // Necessary for DB
    public ServiceProvider() {

    }

    public ServiceProvider(String email, String username, String password) {
        super(email, username, password, UserTypes.SERVICEPROVIDER);
        services = new HashMap<>();
    }

    public void addService(String serviceName, boolean certified) {
        services.put(serviceName, new Pair<>(certified, ""));
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


}
