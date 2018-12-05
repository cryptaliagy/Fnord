package slng.fnord.Database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import slng.fnord.Helpers.Interfaces.Database;
import slng.fnord.Helpers.Interfaces.Identifiable;
import slng.fnord.Structures.Administrator;
import slng.fnord.Structures.Booking;
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
        Disposable disposable = getService(name).subscribe(service -> {
            if (service.isPresent()) {
                updateFromPath("services/" + service.get().getId(), null);
            }
        });
    }

    public void updateService(Service service) {
        updateFromPath("services/"+service.getId(), service);
    }

    public String addBooking(Booking booking) { return addGenericGetID("bookings", booking); }

    public void removeBooking(String id) { updateFromPath("bookings/" + id, null); }

    public Observable<Optional<Booking>> getBooking(String id) {
        return Observable.create(source -> FirebaseDatabase.getInstance()
                .getReference("bookings").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Optional<Booking> optionalBooking;
                        if (dataSnapshot.exists()) {
                            Booking result = dataSnapshot.getValue(Booking.class);
                            optionalBooking = Optional.ofNullable(result);
                        } else {
                            optionalBooking = Optional.empty();
                        }
                        source.onNext(optionalBooking);
                        source.onComplete();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        source.onError(databaseError.toException());
                    }
                }));
    }

    private <T> void updateFromPath(String path, T object) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child(path).setValue(object);
    }

    private <T extends Identifiable> String addGenericGetID(String node, T object) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(node).push();
        String id = dbRef.getKey();

        object.setId(id);

        dbRef.setValue(object);
        return id;
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

    @Override
    public Observable<Booking> getAllBookings() {
        return Observable.create(source -> FirebaseDatabase.getInstance()
                .getReference("bookings")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                source.onNext(child.getValue(Booking.class));
                            }
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
    public Observable<ServiceProvider> getAllServiceProviders() {
        return Observable.create(source -> FirebaseDatabase.getInstance()
                .getReference("users")
                .orderByChild("type").equalTo("SERVICEPROVIDER")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                source.onNext(child.getValue(ServiceProvider.class));
                            }
                        }
                        source.onComplete();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        source.onError(databaseError.toException());
                    }
                }));
    }
}
