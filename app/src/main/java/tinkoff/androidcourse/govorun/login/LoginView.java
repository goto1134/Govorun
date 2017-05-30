package tinkoff.androidcourse.govorun.login;

import android.net.Uri;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * @author Sergey Boishtyan
 */
interface LoginView extends MvpView {

    void goToNavigationScreen(String displayName, Uri photoUrl);

    void showUnSuccessAuthorization();

    void signIn();

    void setLoadingState();
}
