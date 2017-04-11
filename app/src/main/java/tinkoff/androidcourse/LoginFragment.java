package tinkoff.androidcourse;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * @author Sergey Boishtyan
 */
public class LoginFragment extends Fragment {

    public static final String TAG = "LoginFragment";
    private Boolean success;
    private Progress progress;
    private LoginListener loginListener;

    private enum Progress {
        READY, PROCESSING, FINISHED
    }

    public static LoginFragment getInstance(FragmentManager fragmentManager) {
        LoginFragment loginFragment = (LoginFragment) fragmentManager.findFragmentByTag(LoginFragment.TAG);
        if (loginFragment == null) {
            loginFragment = new LoginFragment();
            fragmentManager.beginTransaction()
                           .add(loginFragment, TAG)
                           .commit();
        }
        return loginFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginListener) {
            loginListener = (LoginListener) context;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (progress == Progress.FINISHED) {
            loginListener.onResult(success);
            success = null;
            progress = Progress.READY;
        } else if (progress == Progress.PROCESSING) {
            loginListener.onInProgress();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Для того, чтобы Fragment сохранялся при пересоздании Activity.
        setRetainInstance(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loginListener = null;
    }

    public void onTaskStarted() {
        progress = Progress.PROCESSING;
    }

    public void setSuccess(Boolean success) {
        if (loginListener != null) {
            loginListener.onResult(success);
            progress = Progress.READY;
        } else {
            this.success = success;
            progress = Progress.FINISHED;
        }
    }

    public interface LoginListener {

        void onInProgress();

        void onResult(Boolean success);
    }
}
