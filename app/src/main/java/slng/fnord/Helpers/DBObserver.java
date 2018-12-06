package slng.fnord.Helpers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class DBObserver<T> implements Observer<T> {
    Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        disposable.dispose();
    }

    @Override
    public void onComplete() {
        disposable.dispose();
    }
}
