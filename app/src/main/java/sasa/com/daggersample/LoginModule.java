package sasa.com.daggersample;

import dagger.Module;
import dagger.Provides;
import sasa.com.daggersample.retrofit.LoginApi;
import sasa.com.daggersample.retrofit.LoginApiProvider;

/**
 * Created by Sandeep on 04-08-2016.
 */
@Module
public class LoginModule {

    private LoginView loginView;

    public LoginModule(LoginView view) {
        this.loginView = view;
    }

    @Provides
     LoginPresenter provideLoginPresenter(LoginApi interactor) {

        return new LoginPresenterImpl(loginView,interactor);
    }

    @Provides
    LoginApi provideLoginInteractor() {
        return new LoginApiProvider();
    }
}
