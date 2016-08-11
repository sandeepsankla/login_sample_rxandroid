package sasa.com.daggersample;

import android.app.Application;
import android.content.Context;

/**
 * Created by Sandeep on 04-08-2016.
 */
public class DaggerApplication extends Application {

    private  DaggerApplication instance;
    private  AppComponent appComponent;



    @Override public void onCreate() {
        super.onCreate();
        instance = this;
        initAppComponents();
    }
    public static DaggerApplication get(Context context) {
        return (DaggerApplication) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }


    public void initAppComponents() {
        appComponent = DaggerAppComponent.builder()
                                         .appModule(new AppModule(this))
                                         .build();

    }
}
