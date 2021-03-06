package tdevm.rxplayground;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.reactivestreams.Subscription;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import tdevm.rxplayground.di.NetworkComponent;
import tdevm.rxplayground.di.NetworkModule;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Inject
    Retrofit retrofit;

    Button button, subscribeButton, networkRequest,dagger;
    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn_start);
        subscribeButton = findViewById(R.id.btn_subscribe);
        networkRequest =  findViewById(R.id.btn_network);
        dagger = findViewById(R.id.btn_dagger);
        //Inject in onCreate();
        ((MyApplication)getApplication()).getNetworkComponent().inject(MainActivity.this);

        dagger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final API api = retrofit.create(API.class);
                api.getMyGist("c54a5f3bfa53d40191ff13319e69ed77")
                        .subscribeOn(Schedulers.io())
                          .observeOn(AndroidSchedulers.mainThread())
                           .subscribe(new Observer<Object>() {
                               @Override
                               public void onSubscribe(@NonNull Disposable d) {
                                   Log.d(TAG, "onSubscribe");
                               }

                               @Override
                               public void onComplete() {
                                   Log.i(TAG, "onComplete()");
                               }

                               @Override
                               public void onNext(@NonNull Object o) {
                                   Log.d(TAG, "onNext(" + o + ")");
                               }

                               @Override
                               public void onError(@NonNull Throwable e) {
                                   Log.d(TAG,"stringObserver onError" + e);

                               }
                           });
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxHelper.ObservableJust();
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
                        .observeOn(AndroidSchedulers.mainThread())        //Observer runs on main thread
                        .subscribe(observer);
            }
        });

        networkRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final API api = Network.getClient().create(API.class);
                api.getMyGist("8b77204dabd4e8044df6b07eff4fcac8")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Object>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                Log.d(TAG, "onSubscribe");

                            }

                            @Override
                            public void onNext(@NonNull Object o) {
                                Log.d(TAG, "onNext(" + o + ")");
                            }

                            @Override
                            public void onComplete() {
                                Log.i(TAG, "onComplete()");

                            }
                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d(TAG, "onError(" + e.getMessage() + ")");

                            }
                        });
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }
}
