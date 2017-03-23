package tinkoff.androidcourse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import mehdi.sakout.aboutpage.AboutPage;

/**
 * Created by goto1134
 * on 23.03.2017.
 */

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .addGroup("Connect with us")
                .addEmail("1134togo@gmail.com")
                .addGitHub("goto1134")
                .create();
        setContentView(aboutPage);
    }
}
