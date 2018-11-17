package slng.fnord;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class DBObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable disp) {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }
}

