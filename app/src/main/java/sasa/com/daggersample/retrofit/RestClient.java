package sasa.com.daggersample.retrofit;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Divum on 28-10-2015.
 */


public class RestClient {

    private static RestClient _instance = null;
    private RetrofitAPi service;
    public static String BASEURL = "https://api.github.com/";
    Retrofit restAdapter;

    private RestClient() {

        restAdapter = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        service = restAdapter.create(RetrofitAPi.class);
    }

    public RetrofitAPi getApiInterface() {
        return service;
    }

    public static RestClient getInstance() {
        if (_instance == null) {
            _instance = new RestClient();
        }
        return _instance;
    }

}

