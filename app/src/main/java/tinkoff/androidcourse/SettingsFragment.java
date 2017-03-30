package tinkoff.androidcourse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * Created by goto1134
 * on 30.03.2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat {
    public static Fragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }
}
