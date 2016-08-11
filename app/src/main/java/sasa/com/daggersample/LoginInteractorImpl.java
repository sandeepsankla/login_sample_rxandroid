package sasa.com.daggersample;

import android.os.Handler;
import android.support.annotation.NonNull;

/**
 * Created by Sandeep on 04-08-2016.
 */
public class LoginInteractorImpl implements LoginInteractor {

    private OnInfoCompleteListener listener;

    public LoginInteractorImpl() {
    }


    @Override
    public void getUserCredentials(@NonNull String email, @NonNull String password,  final OnInfoCompleteListener listener) {
        new Handler().postDelayed(new Runnable() {
                                      @Override public void run() {
                                          listener.onSuccess();
                                      }
                                  }, 2000

        );
    }
}
