package tinkoff.androidcourse.govorun.login;

import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

/**
 * @author Sergey Boishtyan
 */
class LoginPresenter extends MvpBasePresenter<LoginView>
        implements GoogleApiClient.OnConnectionFailedListener, OnCompleteListener<AuthResult> {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private AuthResult authResult;

    @Override
    public void attachView(LoginView view) {
        super.attachView(view);
        if (authResult != null) {
            showResult(authResult);
            authResult = null;
        }
    }


    void onAuthorizationResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            firebaseAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this);
            getView().setLoadingState();
        } else {
            getView().showUnSuccessAuthorization();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        getView().showUnSuccessAuthorization();
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AuthResult result = task.getResult();

        if (isViewAttached()) {
            showResult(result);
        } else {
            this.authResult = result;
        }
    }

    private void showResult(AuthResult result) {
        FirebaseUser user = result.getUser();
        if (user != null) {
            getView().goToNavigationScreen(user.getDisplayName(), user.getPhotoUrl());
        } else {
            getView().showUnSuccessAuthorization();
        }
    }

    void onSignInButtonPressed() {
        getView().signIn();
    }
}
