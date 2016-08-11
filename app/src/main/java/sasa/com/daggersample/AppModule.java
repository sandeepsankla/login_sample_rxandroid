package sasa.com.daggersample;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sandeep on 04-08-2016.
 */
@Module
public class AppModule {

    DaggerApplication app;


    public AppModule (DaggerApplication application){
        this.app =  application;
    }

    @Provides
    @Singleton
    Application provideApplication (){
       return  app;
   }
}
