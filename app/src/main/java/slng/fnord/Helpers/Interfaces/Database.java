package slng.fnord.Helpers.Interfaces;

import java.util.ArrayList;

import io.reactivex.Observable;
import slng.fnord.Structures.Service;
import slng.fnord.Structures.User;

public interface Database {
    Observable<User> getUser(String email);

    void addUser(User user);

    void updateUser(User user);

    Observable<Service> getService(String name);

    void addService(Service service);

    void updateService(Service service);

    void removeService(String name);

    Observable<ArrayList<String>> getAllServiceNames();

    Observable<ArrayList<Service>> getAllServices();

}
