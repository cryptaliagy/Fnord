package slng.fnord.Structures;

import java.util.HashMap;

import slng.fnord.Helpers.Pair;

public class ServiceProviderMeta extends ServiceProviderInfo {

    private HashMap<String, Pair<Integer, Integer>> availabilities;
    private boolean isCertified;
    private float averageRating;

    public ServiceProviderMeta() {

    }

    public ServiceProviderMeta(ServiceProvider serviceProvider, boolean isCertified) {
        super(serviceProvider);
        this.availabilities = serviceProvider.getAvailability();
        this.isCertified = isCertified;
        this.averageRating = serviceProvider.getAverageRating();
    }

    public boolean isCertified() {
        return isCertified;
    }

    public HashMap<String, Pair<Integer, Integer>> getAvailabilities() {
        return availabilities;
    }

    public float getAverageRating() {
        return averageRating;
    }
}
