package sasa.com.daggersample;

import android.support.annotation.NonNull;

/**
 * Created by Sandeep on 04-08-2016.
 */
public interface LoginInteractor {

    void getUserCredentials(@NonNull String email,@NonNull String password,OnInfoCompleteListener listener);

}
