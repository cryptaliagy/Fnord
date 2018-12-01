package slng.fnord.Database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

import io.reactivex.Observable;
import slng.fnord.Helpers.Interfaces.Database;
import slng.fnord.Helpers.Interfaces.Identifiable;
import slng.fnord.Structures.Administrator;
import slng.fnord.Structures.HomeOwner;
import slng.fnord.Structures.Service;
import slng.fnord.Structures.ServiceProvider;
import slng.fnord.Structures.User;
import slng.fnord.Helpers.Enums.UserTypes;

public class DBHelper implements Database {

    public Observable<Optional<User>> getUser(String email) {
        return Observable.create(source -> FirebaseDatabase.getInstance()
                .getReference("users").orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Optional<User> optionalUser;
                        if (dataSnapshot.exists()) { // User exists
                            DataSnapshot result = dataSnapshot.getChildren().iterator().next();

                            UserTypes type = result.child("type").getValue(UserTypes.class);
                            User user = null;
                            switch (type) {
                                case HOMEOWNER:
                                    user = result.getValue(HomeOwner.class);
                                    break;
                                case SERVICEPROVIDER:
                                    user = result.getValue(ServiceProvider.class);
                                    break;
                                case ADMIN:
                                    user = result.getValue(Administrator.class);
                                    break;
                            }


                            optionalUser = Optional.ofNullable(user);
                        } else {
                            optionalUser = Optional.empty();
                        }
                        source.onNext(optionalUser);
                        source.onComplete();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                            source.onError(databaseError.toException());
                    }
                }));
    }

    public void addUser(User user) {
        addNewGeneric("users", user);
    }

    public void updateUser(User user) {
        updateFromPath("users/" + user.getId(), user);
    }

    public Observable<Optional<Service>> getService(String name) {
        return Observable.create(source -> FirebaseDatabase.getInstance()
                .getReference("services").orderByChild("serviceName").equalTo(name)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Optional<Service> optionalService;
                        if (dataSnapshot.exists()) {
                            DataSnapshot result = dataSnapshot.getChildren().iterator().next();
                            Service service = result.getValue(Service.class);
                            optionalService = Optional.ofNullable(service);
                        } else {
                            optionalService = Optional.empty();
                        }
                        source.onNext(optionalService);
                        source.onComplete();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        source.onError(databaseError.toException());
                    }
                })

        );
    }
    public void addService(Service service) {
        addNewGeneric("services", service);
    }

    public void removeService(String name) {
        getService(name).subscribe(service -> {
            if (service.isPresent()) {
                updateFromPath("services/" + service.get().getId(), null);
            }
        });
    }

    public void updateService(Service service) {
        updateFromPath("services/"+service.getId(), service);

    }

    private <T> void updateFromPath(String path, T object) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child(path).setValue(object);
    }

    private <T extends Identifiable> void addNewGeneric(String node, T object) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(node).push();
        object.setId(dbRef.getKey());

        dbRef.setValue(object);
    }

    public Observable<Optional<ArrayList<String>>> getAllServiceNames() {
        return Observable.create(source -> FirebaseDatabase.getInstance()
                .getReference("services")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Optional<ArrayList<String>> optionalStrings;
                        if (dataSnapshot.exists()) {
                            ArrayList<String> services = new ArrayList<>();
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                services.add(child.child("serviceName").getValue(String.class));
                            }

                            optionalStrings = Optional.ofNullable(services);
                        } else {
                            optionalStrings = Optional.empty();
                        }
                        source.onNext(optionalStrings);
                        source.onComplete();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        source.onError(databaseError.toException());

                    }
                }));
    }

    @Override
    public Observable<Optional<ArrayList<Service>>> getAllServices() {
        return Observable.create(source -> FirebaseDatabase.getInstance()
                .getReference("services")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Optional<ArrayList<Service>> optionalServices;
                        if (dataSnapshot.exists()) {
                            ArrayList<Service> services = new ArrayList<>();
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                services.add(child.getValue(Service.class));
                            }
                            optionalServices = Optional.ofNullable(services);
                        } else {
                            optionalServices = Optional.empty();
                        }

                        source.onNext(optionalServices);
                        source.onComplete();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }));
    }
}
