package tdevm.rxplayground;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    Button button, subscribeButton;
    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.btn_start);
        subscribeButton = (Button)findViewById(R.id.btn_subscribe);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                RxHelper.ObservableJust();
                  String[] strings = {"x","y","z"};
                  RxHelper.basicNameObservable(strings);
                  RxHelper.ObservableJustFilter();

                  TestWrap.wrapTest();
            }
        });

        //The basic three step process to create an async reactive programming pattern.

        // 1.create an observable
        final Observable<String> stringObservable  = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("Say Hello!");
                e.onComplete();

            }
        });

        // 2.create an observer
        final Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG,"stringObserver onSubscribe");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d(TAG,"stringObserver onNext: "+ s);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"stringObserver onError");

            }

            @Override
            public void onComplete() {
                Log.d(TAG,"stringObserver onComplete");

            }
        };

        // 3.Join them together. i.e Subscribe

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 stringObservable.subscribeOn(Schedulers.io())             //Observable runs on io thread
                        .observeOn(AndroidSchedulers.mainThread())//Observer runs on main thread
                        .subscribe(observer);
            }
        });

    }
}
