package slng.fnord.Managers;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;
import slng.fnord.Helpers.Interfaces.Database;
import slng.fnord.Structures.Service;
import slng.fnord.Structures.Services;

public class ServicesManager {
    private Database database;


    /**
     * Constructor; initializes a new db helper instance
     */
    public ServicesManager(Database database) {
        this.database = database;
    }

    /**
     * Requests a service from the db helper, creating one if none were found and passing
     * null if one with the same name already exists
     * @param name name of the new service
     * @param rate rate of the new service
     * @param callback the callback receiving the service
     */
    public void makeService(String name, Double rate, Consumer<Service> callback) {
        database.getService(name).map(service -> {
            if (service == null) {
                Service ser = new Service(name, rate);
                database.addService(ser);
                return ser;
            } else {
                return null;
            }
        }).subscribe(callback).dispose();

    }

    /**
     * Requests that the db helper update a service record
     * @param service the service to be updated
     */
    public void updateService(Service service) {
        database.updateService(service);
    }

    /**
     * Requests that the db helper remove a service record
     * @param name the name of the service to be removed
     */
    public void removeService(String name) {
        database.removeService(name);
    }

    /**
     * Requests a service from the db helper and passes it to the callback
     * @param name the name of the service
     * @param callback the callback receiving the service
     */
    public void getService(String name, Consumer<Service> callback) {
        database.getService(name).subscribe(callback).dispose();
    }


    /**
     * Requests from the db helper an arraylist with the names of all services
     * @param callback the function to receive the arraylist
     */
    public void getServiceNamesArrayList(Consumer<ArrayList<String>> callback) {
        database.getAllServiceNames().subscribe(callback).dispose();
    }

    /**
     * Requests from the db helper an arraylist of all services
     * @param callback
     */

    public void getServicesArrayList(Consumer<ArrayList<Service>> callback) {
        database.getAllServices().subscribe(callback).dispose();
    }

    /**
     * Requests from the db helper a service and returns its rate
     * @param serviceName the name of the service to query
     * @return the rate of the service
     */

    public Double getServiceRate(String serviceName) {
        return database.getService(serviceName).blockingSingle().getServiceRate();
    }


}
