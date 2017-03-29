package tinkoff.androidcourse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mehdi.sakout.aboutpage.AboutPage;

/**
 * Created by goto1134
 * on 23.03.2017.
 */
public class AboutFragment extends Fragment {

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new AboutPage(getContext()).setDescription(getString(R.string.app_description))
                                          .addGroup(getString(R.string.label_about_me))
                                          .addEmail(getString(R.string.developer_email), getString(R.string.label_email))
                                          .addGitHub(getString(R.string.developer_github), getString(R.string.label_github))
                                          .create();
    }
}
