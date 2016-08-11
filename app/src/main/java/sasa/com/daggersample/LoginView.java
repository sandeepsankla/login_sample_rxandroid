package sasa.com.daggersample;

/**
 * Created by import android.preference.PreferenceManager;
 on 04-08-2016.
 */
public interface LoginView {
    void showProgress();
    void hideProgress();
    void showResult();
    void showFailure(String message);
}
