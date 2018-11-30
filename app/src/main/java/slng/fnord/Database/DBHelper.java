package slng.fnord.Database;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import io.reactivex.Completable;
import io.reactivex.Observable;
import slng.fnord.Helpers.Common;
import slng.fnord.Structures.Services;
import slng.fnord.Structures.User;

public class DBHelper {
    public static Observable<DataSnapshot> makeObservableFromPath(final String path) {
        return Observable.create(emitter -> {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    emitter.onNext(dataSnapshot);
                    emitter.onComplete();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    emitter.onError(databaseError.toException());

                }
            };

            ref.addListenerForSingleValueEvent(listener);
        });
    }

    public static Completable makeCompletableFromPath(final String path, final Object value) {
        return Completable.create(emitter -> {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
            ref.setValue(value)
                    .addOnSuccessListener(e -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    private static void updateFromPath(String path, Object object) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        dbRef.child(path).setValue(object);
    }

    public static void updateUser(User user) {
        updateFromPath("users/" + user.getId(), user);
    }

    public static void updateServices(Services services) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> children = new HashMap<>();

        for (String key : services.getServices().keySet()) {
            children.put(key, services.getService(key));
        }

        dbRef.child("services").updateChildren(children);
    }

    public static void deleteService(String service) {
        String id = Common.makeMD5(service);

        FirebaseDatabase.getInstance().getReference("services/" + id).removeValue();
    }

    public static <T> void getData(String type, String id, Class<T> dataClass, Consumer<T> callback) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(type + "/" + id);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    callback.accept(null);
                }
                else {
                    T data = dataSnapshot.getValue(dataClass);
                    callback.accept(data);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.accept(null);
            }
        });
    }
}
