package sasa.com.daggersample;

import android.util.Log;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import sasa.com.daggersample.retrofit.LoginApi;

/**
 * Created by Sandeep on 04-08-2016.
 */
public class LoginPresenterImpl implements LoginPresenter{

    private LoginView  loginView;
    private LoginApi loginApi;
    private CompositeSubscription _subscription;



    public LoginPresenterImpl(LoginView view,LoginApi interactor) {
        this.loginView =  view;
        this.loginApi = interactor;
        _subscription = new CompositeSubscription();
    }

    @Override public void submitData() {

        loginView.showProgress();

     Observable<JsonElement> loginData =  loginApi.getProfileDetails();


        Subscription   subscription = loginData.subscribeOn(Schedulers.io())
                                             .observeOn(AndroidSchedulers.mainThread())
                                             .subscribe(new Action1<JsonElement>() {
                                                 @Override public void call(JsonElement element)
                                                 { JsonObject data= null;
                                                     if(element.isJsonObject()) {
                                                          data = element.getAsJsonObject();
                                                     }
                                                   String name =   data.get("name").toString();
                                                     Log.i("tag",  name);
                                                     loginView.hideProgress();
                                                      loginView.showResult();
                                                 }
                                             }, new Action1<Throwable>() {
                                                 @Override public void call(Throwable e) {
                                                     loginView.hideProgress();
                                                     String msg;
                                                     if(e instanceof HttpException){
                                                        msg = e.getMessage().toString();
                                                     }else {
                                                         msg = e.getMessage().toString();
                                                         Log.i("tag",msg);
                                                     }
                                                     loginView.showFailure(msg);
                                                 }
                                             });
        _subscription.add(subscription);
    }


    @Override public void onActivityDestroy() {
        _subscription.unsubscribe();
    }
}
