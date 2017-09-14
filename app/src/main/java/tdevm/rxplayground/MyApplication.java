package tdevm.rxplayground;

import android.app.Application;

import tdevm.rxplayground.di.DaggerNetworkComponent;
import tdevm.rxplayground.di.NetworkComponent;
import tdevm.rxplayground.di.NetworkModule;

/**
 * Created by Tridev on 13-09-2017.
 */

public class MyApplication extends Application {
    public static final String BASE_URL = "https://api.github.com/";

    private NetworkComponent networkComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        networkComponent = DaggerNetworkComponent.builder().
                networkModule(new NetworkModule(BASE_URL))
                .build();

    }

    public NetworkComponent getNetworkComponent(){
        return networkComponent;
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
