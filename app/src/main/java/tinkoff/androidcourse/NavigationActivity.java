package tinkoff.androidcourse;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import tinkoff.androidcourse.dialog.DialogFragment;
import tinkoff.androidcourse.dialoglist.DialogListFragment;
import tinkoff.androidcourse.model.db.DialogItem;

import static tinkoff.androidcourse.Constants.LOGIN_KEY;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DialogListFragment.DialogListListener,
        CreateDialogDialog.CreateDialogListener, LoaderManager.LoaderCallbacks<DialogItem> {

    private final static int MENU_DIALOGS = 0;
    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final int ADD_DIALOG_LOADER = 2;
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
                Fragment fragment = SettingsFragment.newInstance();
                setFragment(fragment);
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
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager.getBackStackEntryCount() > 0)
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        supportFragmentManager.beginTransaction()
                              .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                              .replace(R.id.content_navigation, fragment)
                              .commit();
    }

    private void addFragmentOnBackStack(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                                   .setCustomAnimations(R.anim.slide_from_right, R.anim.fade_out,
                                           R.anim.fade_in, R.anim.slide_to_left)
                                   .replace(R.id.content_navigation, fragment)
                                   .addToBackStack(null)
                                   .commit();
    }

    @Override
    public void onDialogTouched(DialogItem dialogItem) {
        DialogFragment fragment = DialogFragment.newInstance(dialogItem);
        addFragmentOnBackStack(fragment);
    }

    @Override
    public void onDialogCreateCalled() {
        new CreateDialogDialog().show(getSupportFragmentManager(), null);
    }

    @Override
    public void onCreateDialog(String title, String description) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(DESCRIPTION, description);
        getSupportLoaderManager().restartLoader(ADD_DIALOG_LOADER, bundle, this);
    }

    @Override
    public Loader<DialogItem> onCreateLoader(int id, Bundle args) {
        String title = args.getString(TITLE);
        String description = args.getString(DESCRIPTION);
        DialogItem dialogItem = new DialogItem(title, description);
        return new CreateDialogLoader(this, dialogItem);
    }

    @Override
    public void onLoadFinished(Loader<DialogItem> loader, DialogItem dialogItem) {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof DialogListFragment) {
                ((DialogListFragment) fragment).addDialog(dialogItem);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<DialogItem> loader) {

    }
}
