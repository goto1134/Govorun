package tinkoff.androidcourse;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import static tinkoff.androidcourse.Constants.LOGIN_KEY;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DialogListFragment.DialogListListener {

    private final static int MENU_DIALOGS = 0;
    private ActionBarDrawerToggle toggle;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_dialogs:
                Fragment dialogsFragment = DialogListFragment.newInstance();
                setFragment(dialogsFragment);
                break;
            case R.id.nav_settings:
                StubFragment settingsFragment = StubFragment.newInstance("Настройки");
                setFragment(settingsFragment);
                break;
            case R.id.nav_about:
                Fragment aboutFragment = AboutFragment.newInstance();
                setFragment(aboutFragment);
                break;
            case R.id.nav_exit:
                finish();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!getIntent().hasExtra(LOGIN_KEY))
            throw new IllegalStateException("The initial intent has not LOGIN extra");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        drawer.addDrawerListener(toggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView loginView = (TextView) navigationView.getHeaderView(0)
                                                      .findViewById(R.id.nav_login);
        loginView.setText(getIntent().getStringExtra(LOGIN_KEY));

        if (savedInstanceState == null) {
            navigationView.getMenu()
                          .getItem(MENU_DIALOGS)
                          .setChecked(true);
            onNavigationItemSelected(navigationView.getMenu()
                                                   .getItem(MENU_DIALOGS));
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction = fragmentTransaction.replace(R.id.content_navigation, fragment);
        fragmentTransaction.commit();
    }

    private void addFragmentOnBackStack(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.content_navigation, fragment)
                                   .addToBackStack(null)
                                   .commit();
    }

    @Override
    public void onDialogTouched(int position) {
        DialogFragment fragment = DialogFragment.newInstance();
        addFragmentOnBackStack(fragment);
    }
}
