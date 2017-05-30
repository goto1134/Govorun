package tinkoff.androidcourse.govorun.login;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * @author Sergey Boishtyan
 */
interface LoginView extends MvpView {

    void goToNavigationScreen();

    void showUnSuccessAuthorization();
}
