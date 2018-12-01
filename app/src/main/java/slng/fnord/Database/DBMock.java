package slng.fnord.Database;

import java.util.ArrayList;

import io.reactivex.Observable;
import slng.fnord.Helpers.Interfaces.Database;
import slng.fnord.Structures.Service;
import slng.fnord.Structures.User;

public class DBMock implements Database {
    @Override
    public void addService(Service service) {

    }

    @Override
    public Observable<Service> getService(String name) {
        return null;
    }

    @Override
    public Observable<User> getUser(String email) {
        return null;
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public void removeService(String name) {

    }

    @Override
    public void updateService(Service service) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public Observable<ArrayList<String>> getAllServiceNames() {
        return null;
    }

    @Override
    public Observable<ArrayList<Service>> getAllServices() {
        return null;
    }
}
