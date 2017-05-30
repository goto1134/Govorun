package tinkoff.androidcourse.govorun;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

import tinkoff.androidcourse.govorun.model.PrefManager;


/**
 * @author Sergey Boishtyan
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PrefManager.newInstance(this);
        FlowManager.init(this);
    }
}
