package sasa.com.daggersample.retrofit;

import com.google.gson.JsonElement;

import org.json.JSONObject;

import rx.Observable;

/**
 * Created by Kabir on 09-08-2016.
 */
public interface LoginApi {

    Observable<JsonElement> getProfileDetails();
}
