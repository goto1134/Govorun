package tinkoff.androidcourse;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * @author Sergey Boishtyan
 */
class LoginTask extends AsyncTask<String[], Void, Boolean> {

    private WeakReference<LoginFragment> loginFragment;

    LoginTask(LoginFragment loginFragment) {
        this.loginFragment = new WeakReference<>(loginFragment);
    }

    @Override
    protected Boolean doInBackground(String[]... credentials) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPreExecute() {
        loginFragment.get()
                     .onTaskStarted();
    }

    @Override
    protected void onPostExecute(Boolean success) {
        LoginFragment loginFragment = this.loginFragment.get();
        if (loginFragment != null) {
            loginFragment.setSuccess(success);
            Log.d("LoginTask", "onPostExecute " + loginFragment.toString());
        }
    }
}
