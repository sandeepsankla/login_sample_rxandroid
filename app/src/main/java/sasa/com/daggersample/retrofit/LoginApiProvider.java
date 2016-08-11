package sasa.com.daggersample.retrofit;

import com.google.gson.JsonElement;


import rx.Observable;

/**
 * Created by Kabir on 09-08-2016.
 */
public class LoginApiProvider implements LoginApi {


    @Override public Observable<JsonElement> getProfileDetails() {
        RetrofitAPi loginApi = RestClient.getInstance().getApiInterface();
        Observable<JsonElement> data =loginApi.getLoginDetails();
        return data ;

    }
}
