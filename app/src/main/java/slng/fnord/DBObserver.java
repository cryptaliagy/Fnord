package slng.fnord;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class DBObserver<T> implements Observer<T> {
    HashMap<String, Object> data = new HashMap<>();
    @Override
    public void onSubscribe(Disposable disp) {

    }

    @Override
    public void onError(Throwable t) {

    }

    public HashMap<String, Object> getData() {
        return data;
    }
}

