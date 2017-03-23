package tinkoff.androidcourse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by goto1134
 * on 23.03.2017.
 */

public abstract class MenuActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            Intent intent = new Intent("tinkoff.androidcourse.ABOUT_APP");
            this.startActivity(intent);
        } else return super.onOptionsItemSelected(item);
        return true;
    }
}
