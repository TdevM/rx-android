package tdevm.rxplayground;
import android.util.Log;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by Tridev on 09-09-2017.
 */

public class RxHelper {
    private static final String TAG = "RxHelper";

     static void ObservableJust(){
        Observable.just("Hello World").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG,s);
            }
        });
    }

    public static void ObservableJustFilter(){
        Observable.just(1,2,3,4,5).filter(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {

                return integer%2==0;
            }
        }).subscribe();

    }

    public static void basicNameObservable(String[] names){
        Observable<String> obs = Observable.fromArray(names);
        output(obs);
    }




    private static <T> void output(Observable<T> obs) {

        obs.subscribe(new Observer<T>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(@NonNull T t) {
                Log.d(TAG, "onNext(" + t + ")");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError(" + e.getMessage() + ")");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete()");
            }
        });
    }


    private static <T> void outputL(Flowable<T> flow) {

        flow.subscribe(new Subscriber<T>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.i(TAG, "onSubscribe"+ s);
            }

            @Override
            public void onNext(T t) {
                Log.i(TAG, "onNext(" + t + ")");
            }

            @Override
            public void onError(Throwable t) {
                Log.i(TAG, "onError(" + t.getMessage() + ")");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete()");
            }
        });
    }
}
