package remake.leafpic.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import remake.leafpic.R;
import remake.leafpic.data.MediaHelper;
import remake.leafpic.fragment.AlbumFragment;
import remake.leafpic.fragment.MediaFragment;
import remake.leafpic.view.NavigationDrawer;
import remake.leafpic.view.NavigationEntry;

public class MainActivity extends AppCompatActivity {

    NavigationDrawer navigationDrawerView;

    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    NavigationDrawer mLeftNavigationMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSingletonInstance();

        setupToolbar();
        setupNavigation();


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        Fragment fragment = new MediaFragment();
        transaction.replace(R.id.content, fragment);
        transaction.commit();


    }

    private void initSingletonInstance() {
        MediaHelper.getInstance().init(this.getApplicationContext());
    }

    private void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private void setupNavigation() {
        navigationDrawerView = findViewById(R.id.home_navigation_drawer);
        mDrawerLayout = findViewById(R.id.main_activity);
        mLeftNavigationMenu = findViewById(R.id.home_navigation_drawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationDrawer.OnEntrySelectedListener onEntrySelectedListener = new NavigationDrawer.OnEntrySelectedListener() {
            @Override
            public void onEntrySelected(int id) {
                onNavEntrySelected(id);
            }
        };

        mLeftNavigationMenu.setOnEntrySelectedListener(onEntrySelectedListener);
    }

    private void onNavEntrySelected(int id) {
        switch (id){
            case R.id.navigation_item_albums:
                break;

            case R.id.navigation_item_all_media:
                break;

            case R.id.navigation_item_timeline:
                break;

            case R.id.navigation_item_about:{
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}

