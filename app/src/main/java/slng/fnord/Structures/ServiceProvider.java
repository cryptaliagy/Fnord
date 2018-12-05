package slng.fnord.Structures;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import slng.fnord.Helpers.Common;
import slng.fnord.Helpers.Enums.UserTypes;
import slng.fnord.Helpers.Pair;

public class ServiceProvider extends User {
    private HashMap<String, Boolean> services;
    private String phone;
    private String address;
    private String company;
    private String biography;
    private HashMap<String, Pair<Integer, Integer>> availability;
    private ArrayList<Ratings> ratings;
    private float averageRating;
    private int totalNumberOfRatings;

    // Necessary for DB
    public ServiceProvider() {

    }

    public ServiceProvider(String email, String password) {
        super(email, password, UserTypes.SERVICEPROVIDER);
        services = new HashMap<>();
        availability = Common.makeBlankAvail();
        ratings = new ArrayList<Ratings>();
        totalNumberOfRatings = 0;
        averageRating = 0;
    }

    public void addRating(Ratings rating){
        //if ratings arraylist is less than 5, add it
        //if ratings arraylist is greater than 5,
        if(ratings.size() < 5){
            ratings.add(rating);
        } else {
            int indexToAdd = totalNumberOfRatings % 5;
            ratings.set(indexToAdd,rating);
        }
        averageRating = (rating.getRatingValue() + (averageRating*totalNumberOfRatings))/(totalNumberOfRatings+1);
        totalNumberOfRatings++;
    }

    public ArrayList<Ratings> getRatings() {
        return ratings;
    }

    public float getAverageRating(){
        return averageRating;
    }


    public void addService(String serviceName, boolean certified) {
        if (services == null) {
            services = new HashMap<>();
        }
        services.put(serviceName, certified);
    }

    public void addService(String serviceName) {
        addService(serviceName, false);
    }

    public void removeService(String id) { services.remove(id); }

    public List<String> getServiceList() {
        if (services == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(services.keySet());
    }

    public HashMap<String, Boolean> getServices() {
        return services;
    }

    public void updateCertified(String serviceName, boolean certified) {
        services.put(serviceName, certified);
    }

    public boolean isCertified(String serviceName) {
        if (!services.containsKey(serviceName)) {
            return false;
        }

        return services.get(serviceName);
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

    public void setAvailability(HashMap<String, Pair<Integer, Integer>> availability) {
        this.availability = availability;
    }

    public boolean providesService(String name) {
        return services.containsKey(name);
    }

    public HashMap<String, Pair<Integer, Integer>> getAvailability() {
        return this.availability;
    }

    public Pair<Integer, Integer> getDayAvailability(String day) {
        return availability.get(day);
    }

    public void setDayAvailability(String day, Pair<Integer, Integer> availability) {
        this.availability.put(day, availability);
    }


}
