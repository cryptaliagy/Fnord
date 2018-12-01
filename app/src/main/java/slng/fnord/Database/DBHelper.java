package slng.fnord.Database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

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

    public Observable<User> getUser(String email) {
        return Observable.create(source -> FirebaseDatabase.getInstance()
                .getReference("users").orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
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

                            source.onNext(user);
                        } else {
                            source.onNext(null); // No such users?
                        }
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

    public Observable<Service> getService(String name) {
        return Observable.create(source -> FirebaseDatabase.getInstance()
                .getReference("services").orderByChild("name").equalTo(name)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            DataSnapshot result = dataSnapshot.getChildren().iterator().next();
                            Service service = result.getValue(Service.class);
                            source.onNext(service);
                        } else {
                            source.onNext(null);
                        }
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
            if (service != null) {
                updateFromPath("services/" + service.getId(), null);
            }
        }).dispose();
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

    public Observable<ArrayList<String>> getAllServiceNames() {
        return Observable.create(source -> FirebaseDatabase.getInstance()
                .getReference("services")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            ArrayList<String> services = new ArrayList<>();
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                services.add(child.child("name").getValue(String.class));
                            }

                            source.onNext(services);
                        } else {
                            source.onNext(null);
                        }
                        source.onComplete();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        source.onError(databaseError.toException());

                    }
                }));
    }

    @Override
    public Observable<ArrayList<Service>> getAllServices() {
        return Observable.create(source -> FirebaseDatabase.getInstance()
                .getReference("services")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            ArrayList<Service> services = new ArrayList<>();
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                services.add(child.getValue(Service.class));
                            }
                            source.onNext(services);
                        } else {
                            source.onNext(null);
                        }
                        source.onComplete();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }));
    }
}
