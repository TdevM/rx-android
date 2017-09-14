package tdevm.rxplayground.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tridev on 14-09-2017.
 */

// This class provides network dependencies(Retrofit & GSON instances)
@Module
public class NetworkModule {

    private String urlPath;

    public NetworkModule(String urlPath) {
        this.urlPath = urlPath;
    }

    private static Retrofit retrofit = null;

    @Singleton
    @Provides
    public Gson provideGson(){
        GsonBuilder builder = new GsonBuilder();
        return builder.create();
    }

    @Singleton
    @Provides
    public RxJava2CallAdapterFactory providesRxJavaCallAdapterFactory(){
        return  RxJava2CallAdapterFactory.create();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(Gson gson,RxJava2CallAdapterFactory callAdapter) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(urlPath)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(callAdapter)
                    .build();
        }
        return retrofit;

    }
}
