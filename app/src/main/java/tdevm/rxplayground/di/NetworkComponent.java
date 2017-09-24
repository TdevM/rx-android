package tdevm.rxplayground.di;

import javax.inject.Singleton;

import dagger.Component;
import tdevm.rxplayground.MainActivity;

/**
 * Created by Tridev on 14-09-2017.
 */
@Singleton
@Component(modules = {AppModule.class,NetworkModule.class})
public interface NetworkComponent {
    void inject(MainActivity activity);
}
