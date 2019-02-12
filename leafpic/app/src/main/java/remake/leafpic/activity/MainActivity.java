package remake.leafpic.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import remake.leafpic.R;
import remake.leafpic.view.NavigationDrawer;

public class MainActivity extends AppCompatActivity {

    NavigationDrawer navigationDrawerView;

    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    NavigationDrawer mLeftNavigationMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupNavigation();
    }

    private void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private void setupNavigation() {
        navigationDrawerView = findViewById(R.id.home_navigation_drawer);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mLeftNavigationMenu = findViewById(R.id.home_navigation_drawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }
}
