package slng.fnord.Helpers.Interfaces;

import java.util.ArrayList;
import java.util.Optional;

import io.reactivex.Observable;
import slng.fnord.Structures.Service;
import slng.fnord.Structures.User;

public interface Database {
    Observable<Optional<User>> getUser(String email);

    void addUser(User user);

    void updateUser(User user);

    Observable<Optional<Service>> getService(String name);

    void addService(Service service);

    void updateService(Service service);

    void removeService(String name);

    Observable<Optional<ArrayList<String>>> getAllServiceNames();

    Observable<Optional<ArrayList<Service>>> getAllServices();

}
