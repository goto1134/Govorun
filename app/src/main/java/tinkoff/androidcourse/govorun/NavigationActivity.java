package tinkoff.androidcourse.govorun;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import tinkoff.androidcourse.govorun.dialog.DialogFragment;
import tinkoff.androidcourse.govorun.dialoglist.DialogListFragment;
import tinkoff.androidcourse.govorun.login.LoginActivity;
import tinkoff.androidcourse.govorun.model.db.DialogItem;

import static tinkoff.androidcourse.govorun.Constants.KEY_USER_PHOTO;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DialogListFragment.DialogListListener {

    public static final String DIALOG_FRAGMENT_NAME = "dialog";
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
                Fragment fragment = SettingsFragment.newInstance();
                setFragment(fragment);
                break;
            case R.id.nav_about:
                Fragment aboutFragment = AboutFragment.newInstance();
                setFragment(aboutFragment);
                break;
            case R.id.nav_exit:
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (!intent.hasExtra(Constants.KEY_LOGIN)) {
            throw new IllegalStateException("The initial intent has not LOGIN extra");
        }
        if (!intent.hasExtra(KEY_USER_PHOTO)) {
            throw new IllegalStateException("The initial intent has no USER_PHOTO extra");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        drawer.addDrawerListener(toggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String login = intent.getStringExtra(Constants.KEY_LOGIN);
        Uri photoURL = intent.getParcelableExtra(KEY_USER_PHOTO);

        View headerView = navigationView.getHeaderView(0);
        TextView loginView = (TextView) headerView.findViewById(R.id.nav_login);
        ImageView photoView = (ImageView) headerView.findViewById(R.id.nav_photo);
        loginView.setText(login);
        Picasso.with(this)
               .load(photoURL)
               .transform(new CropCircleTransformation())
               .into(photoView);
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
        if (supportFragmentManager.getBackStackEntryCount() > 0) {
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        supportFragmentManager.beginTransaction()
                              .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                              .replace(R.id.content_navigation, fragment)
                              .commit();
    }

    private void addFragmentOnBackStack(Fragment fragment, String name) {
        getSupportFragmentManager().beginTransaction()
                                   .setCustomAnimations(R.anim.slide_from_right, R.anim.fade_out,
                                           R.anim.fade_in, R.anim.slide_to_left)
                                   .replace(R.id.content_navigation, fragment)
                                   .addToBackStack(name)
                                   .commit();
    }

    @Override
    public void onDialogTouched(DialogItem dialogItem) {
        DialogFragment fragment = DialogFragment.newInstance(dialogItem);
        addFragmentOnBackStack(fragment, DIALOG_FRAGMENT_NAME);
    }
}
