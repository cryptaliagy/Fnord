package slng.fnord.Managers;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Optional;

import io.reactivex.functions.Consumer;
import slng.fnord.Database.DBHelper;
import slng.fnord.Helpers.Common;
import slng.fnord.Database.DBObserver;
import slng.fnord.Database.Interfaces.Database;
import slng.fnord.Structures.Service.Service;
import slng.fnord.Structures.User.ServiceProvider;

public class ServicesManager {
    private Database database;
    private static ServicesManager instance = new ServicesManager();

    /**
     * Singleton instance retriever (with proper connection to the database)
     * @return singleton instance of the Manager object
     */
    public static ServicesManager getInstance() {
        return instance;
    }

    /**
     * Constructor; necessary to prevent instantiating Manager and set up proper DB object
     */
    private ServicesManager() {
        this.database = DBHelper.getInstance();
    }

    /**
     * Constructor; initializes the object with the specified database instance
     * Kept for testing purposes.
     */
    @Deprecated
    public ServicesManager(Database database) {
        this.database = database;
    }

    /**
     * Requests a service from the db helper, creating one if none were found and passing
     * null if one with the same name already exists
     *
     * @param name     name of the new service
     * @param rate     rate of the new service
     * @param callback the callback receiving the service
     */
    public void makeService(String name, Double rate, Consumer<Service> callback) {
        database.getService(name).map(service -> {
            Optional<Service> optionalService;
            if (!service.isPresent()) {
                Service ser = new Service(name, rate);
                database.addService(ser);
                optionalService = Optional.ofNullable(ser);
            } else {
                optionalService = Optional.empty();
            }

            return optionalService;
        }).subscribe(new DBObserver<Optional<Service>>() {
            @Override
            public void onNext(Optional<Service> optionalService) {
                Service service = Common.extractOptional(optionalService);
                try {
                    callback.accept(service);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Requests that the db helper update a service record
     *
     * @param service the service to be updated
     */
    public void updateService(Service service) {
        database.updateService(service);
    }

    /**
     * Requests that the db helper remove a service record
     *
     * @param name the name of the service to be removed
     */
    public void removeService(String name) {
        database.removeService(name);
        database.getAllServiceProviders()
                .filter(provider -> provider.providesService(name))
                .subscribe(new DBObserver<ServiceProvider>() {
                    @Override
                    public void onNext(ServiceProvider serviceProvider) {
                        serviceProvider.removeService(name);
                        database.updateUser(serviceProvider);
                    }
                });
    }

    /**
     * Requests a service from the db helper and passes it to the callback
     *
     * @param name     the name of the service
     * @param callback the callback receiving the service
     */
    public void getService(String name, Consumer<Service> callback) {
        database.getService(name).subscribe(new DBObserver<Optional<Service>>() {
            @Override
            public void onNext(Optional<Service> optionalService) {
                Service service = Common.extractOptional(optionalService);
                try {
                    callback.accept(service);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Requests from the db helper an arraylist with the names of all services
     *
     * @param callback the function to receive the arraylist
     */
    public void getServiceNamesArrayList(Consumer<ArrayList<String>> callback) {
        database.getAllServiceNames().subscribe(new DBObserver<Optional<ArrayList<String>>>() {
            @Override
            public void onNext(Optional<ArrayList<String>> strings) {
                ArrayList<String> extracted = Common.extractOptional(strings);
                try {
                    callback.accept(extracted);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Requests from the db helper a service and returns its rate
     *
     * @param serviceName the name of the service to query
     * @return the rate of the service
     */

    public void getServiceRateForView(String serviceName, TextView view) {
        database.getService(serviceName).subscribe(new DBObserver<Optional<Service>>() {
            @Override
            public void onNext(Optional<Service> optionalService) {
                view.setText(String.format("%.2f", optionalService.get().getServiceRate()));
            }
        });
    }


}
