package com.hungryshark.notes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private boolean isLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMenus();
        initStart();
    }

    private void initMenus() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initStart() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.note_container);
        if (isLandscape && fragment instanceof NoteFragment) {
            fragmentManager.popBackStack();
        } else {
            fragmentTransaction.replace(R.id.note_container, new NotesFragment())
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = getVisibleFragment(fragmentManager);

        if (isLandscape) {
            menu.findItem(R.id.action_back).setVisible(false);
        }
        if (!isLandscape && fragment instanceof NotesFragment) {
            menu.findItem(R.id.action_favorite).setVisible(true);
            menu.findItem(R.id.action_add).setVisible(true);
            menu.findItem(R.id.action_send).setVisible(true);
            menu.findItem(R.id.action_photo).setVisible(true);
            menu.findItem(R.id.action_back).setVisible(false);
        }
        if (!isLandscape && fragment instanceof NoteFragment) {
            menu.findItem(R.id.action_favorite).setVisible(false);
            menu.findItem(R.id.action_add).setVisible(false);
            menu.findItem(R.id.action_send).setVisible(false);
            menu.findItem(R.id.action_photo).setVisible(false);
            menu.findItem(R.id.action_back).setVisible(true);
        }

        MenuItem search = menu.findItem(R.id.action_search); // поиск пункта меню поиска
        SearchView searchText = (SearchView) search.getActionView(); // строка поиска
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // реагирует на конец ввода поиска
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query,
                        Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_back) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Fragment getVisibleFragment(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        int countFragments = fragments.size();
        for (int i = countFragments - 1; i >= 0; i--) {
            Fragment fragment = fragments.get(i);
            if (fragment.isVisible())
                return fragment;
        }
        return null;
    }
}