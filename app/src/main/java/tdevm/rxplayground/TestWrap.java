package tdevm.rxplayground;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Tridev on 10-09-2017.
 */

public class TestWrap {

    public static final String TAG = "TestWrap";
    public static  void wrapTest() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5);

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG,"onSubscribe "+String.valueOf(d));
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG,"onNext "+String.valueOf(integer));

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"onError "+String.valueOf(e));

            }

            @Override
            public void onComplete() {
                Log.d(TAG,"onComplete ");

            }
        };
            observable.subscribe(observer);
    }
}
