package sasa.com.daggersample;

import dagger.Component;


/**
 * Created by Sandeep on 04-08-2016.
 */
@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = {LoginModule.class}
)

public interface LoginComponent {
    void inject(LoginActivity activity);
}
