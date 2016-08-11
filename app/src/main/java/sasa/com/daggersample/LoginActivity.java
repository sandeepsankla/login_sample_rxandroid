package sasa.com.daggersample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action1;
import rx.functions.Func1;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @Inject LoginPresenter presenter;

    @Bind(R.id.user_name) EditText userName;
    @Bind(R.id.user_email) EditText userEmail;
    @Bind(R.id.user_password) EditText userPassword;
    @Bind(R.id.progress) ProgressBar progressBar;
    @Bind(R.id.submit_btn) Button submitBtn;


    @OnClick(R.id.submit_btn) void submitCLicked() {
        if (presenter != null) {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            presenter.submitData();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        DaggerLoginComponent.builder()
                            .appComponent(DaggerApplication.get(this).
                                    getAppComponent())
                            .loginModule(new LoginModule(this))
                            .build()
                            .inject(this);
        onTextChangeListener();

    }

    private void onTextChangeListener() {
        Observable<Boolean> userNameText = WidgetObservable.text(userName)
                                                           .flatMap(new Func1<OnTextChangeEvent, Observable<String>>() {
                                                               @Override public Observable<String>
                                                               call(
                                                                       OnTextChangeEvent
                                                                               onTextChangeEvent) {
                                                                   return Observable
                                                                           .just(userName.getText
                                                                                   ().toString());
                                                               }
                                                           }).flatMap(new Func1<String, Observable<Boolean>>() {
                    @Override public Observable<Boolean> call(String s) {
                        return Observable.just(s.length() > 5);
                    }
                }).debounce(800, TimeUnit.MILLISECONDS).subscribeOn(
                        AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()
                );

        userNameText.subscribe(new Action1<Boolean>() {
            @Override public void call(Boolean aBoolean) {
                if (!aBoolean)
                    userName.setError(getString(R.string.user_name_error));
                else
                    userName.setError(null);
            }
        });


        Observable<Boolean> userEmailText = WidgetObservable.text(userEmail).flatMap(
                new Func1<OnTextChangeEvent, Observable<String>>() {
                    @Override public Observable<String> call(OnTextChangeEvent onTextChangeEvent) {
                        return Observable.just(userEmail.getText().toString());
                    }
                }).flatMap(new Func1<String, Observable<Boolean>>() {
            @Override public Observable<Boolean> call(String s) {
                return Observable.just(Patterns.EMAIL_ADDRESS.matcher(s).matches());
            }
        }).debounce(800, TimeUnit.MILLISECONDS).subscribeOn(
                AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());

        userEmailText.subscribe(new Action1<Boolean>() {
            @Override public void call(Boolean aBoolean) {
                if (!aBoolean)
                    userEmail.setError(getString(R.string.email_error));
                else
                    userEmail.setError(null);
            }
        });


        Observable<Boolean> userPasswordText = WidgetObservable.text(userPassword)
                                                               .map(t -> t.text())
                                                               .map(e -> e.length() > 6)
                                                               .debounce(800, TimeUnit
                                                                       .MILLISECONDS)
                                                               .subscribeOn(AndroidSchedulers
                                                                                    .mainThread())
                                                               .observeOn(AndroidSchedulers
                                                                                  .mainThread());

        userPasswordText.subscribe(new Action1<Boolean>() {
            @Override public void call(Boolean aBoolean) {
                if (!aBoolean)
                    userPassword.setError(getString(R.string.password_error));
                else
                    userPassword.setError(null);
            }
        });

        Observable<Boolean> submitEnable = Observable
                .combineLatest(userNameText, userEmailText, userPasswordText, (a, b, c) -> a &&
                        b && c);
        submitEnable.subscribe(enable -> submitBtn.setEnabled(enable));
    }


    @Override public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }


    @Override public void showResult() {
        Toast.makeText(LoginActivity.this, R.string.success_result, Toast.LENGTH_SHORT).show();
    }

    @Override public void showFailure(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        presenter.onActivityDestroy();
    }
}
