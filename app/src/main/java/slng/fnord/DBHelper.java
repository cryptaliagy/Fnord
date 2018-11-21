package slng.fnord;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class DBHelper {
    public static Observable<DataSnapshot> makeObservableFromPath(final String path) {
        return Observable.create(new ObservableOnSubscribe<DataSnapshot>() {
            @Override
            public void subscribe(final ObservableEmitter<DataSnapshot> emitter) {
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
            }
        });
    }

    public static Completable makeCompletableFromPath(final String path, final Object value) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(final CompletableEmitter emitter) throws Exception {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
                ref.setValue(value).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        emitter.onComplete();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        emitter.onError(e);
                    }
                });
            }
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

        FirebaseDatabase.getInstance().getReference("services/"+id).removeValue();
    }
}
