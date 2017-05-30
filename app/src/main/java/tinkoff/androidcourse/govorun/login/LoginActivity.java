package tinkoff.androidcourse.govorun.login;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import tinkoff.androidcourse.govorun.NavigationActivity;
import tinkoff.androidcourse.govorun.R;

import static com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder;
import static com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN;
import static tinkoff.androidcourse.govorun.Constants.KEY_LOGIN;
import static tinkoff.androidcourse.govorun.Constants.KEY_USER_PHOTO;

public class LoginActivity extends MvpActivity<LoginView, LoginPresenter>
        implements LoginView {

    private static final int GOOGLE_SIGN_IN_REQUEST_CODE = 9001;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton signInButton;
    private ProgressBar progressBar;

    @Override
    public void goToNavigationScreen(String displayName, Uri photoUrl) {
        Intent intent = new Intent(this, NavigationActivity.class);
        intent.putExtra(KEY_LOGIN, displayName);
        intent.putExtra(KEY_USER_PHOTO, photoUrl);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showUnSuccessAuthorization() {
        hideProgress();
        new LoginActivity.MyDialogFragment().show(getSupportFragmentManager(), null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signInButton = ((SignInButton) findViewById(R.id.sign_in_button));
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onSignInButtonPressed();
            }
        });

        progressBar = ((ProgressBar) findViewById(R.id.progress_bar));

        GoogleSignInOptions gso = new Builder(DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, getPresenter())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            getPresenter().onAuthorizationResult(result);
        }
    }

    @Override
    public void setLoadingState() {
        showProgress();
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.GONE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
        signInButton.setVisibility(View.VISIBLE);
    }

    public static class MyDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity()).setTitle("У тебя проблемы")
                                                         .setPositiveButton("Повторить ввод",
                                                                 new DialogInterface.OnClickListener() {
                                                                     @Override
                                                                     public void onClick(DialogInterface dialog, int which) {
                                                                         dismiss();
                                                                     }
                                                                 })
                                                         .create();
        }
    }
}

