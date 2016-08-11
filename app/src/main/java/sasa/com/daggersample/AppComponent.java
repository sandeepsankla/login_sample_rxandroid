package sasa.com.daggersample;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;



/**
 * Created by Sandeep on 04-08-2016.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    Application application();
}
