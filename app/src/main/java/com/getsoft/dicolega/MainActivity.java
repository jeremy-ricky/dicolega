package com.getsoft.dicolega;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MenuItem menuSetting;
    DictionnairekFragment dictionnairekFragment;
    BookmarkFragment bookmarkFragment;
    AproposFragment aproposFragment;
    AcceuilFragment acceuilFragment;
    Toolbar toolbar;
    EditText edit_search;

    DBHelper dbHelper;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pour le link vers le web
        //TextView tv = findViewById(R.id.textViewLink);
        //tv.setMovementMethod(LinkMovementMethod.getInstance());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dictionnairekFragment = new DictionnairekFragment();
        bookmarkFragment = new BookmarkFragment();
        aproposFragment = new AproposFragment();
        acceuilFragment = new AcceuilFragment();
        goToFragment(dictionnairekFragment, true);

        dictionnairekFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void onItemClick(String value) {
                String id = Global.getState(MainActivity.this, "dic_type");
                int dicType = id == null? R.id.action_kil_fr:Integer.valueOf(id);
                goToFragment(DetailFragment.getNewInstance(value, dbHelper, dicType), false);
            }
        });

        bookmarkFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void onItemClick(String value) {
                String id = Global.getState(MainActivity.this, "dic_type");
                int dicType = id == null? R.id.action_kil_fr:Integer.valueOf(id);
                goToFragment(DetailFragment.getNewInstance(value, dbHelper, dicType), false);
            }
        });

        // Partie pour la recherche de filtrage
        edit_search = findViewById(R.id.edit_search);
        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //dictionnairekFragment.filterValue(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dictionnairekFragment.filterValue(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //dictionnairekFragment.filterValue(s.toString());
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menuSetting = menu.findItem(R.id.action_settings);

        String id = Global.getState(this, "dic_type");
        if (id != null) {
            onOptionsItemSelected(menu.findItem(Integer.valueOf(id)));
        }else {
            ArrayList<String> source = dbHelper.getWord(R.id.action_kil_fr);
            dictionnairekFragment.resetDataSource(source);
        }

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            if (R.id.action_settings == id) return true;

            Global.saveState(this, "dic_type", String.valueOf(id));
            ArrayList<String> source = dbHelper.getWord(id);

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_kil_fr) {
                dictionnairekFragment.resetDataSource(source);
                menuSetting.setIcon(getDrawable(R.drawable.kf));
            }else if(id == R.id.action_fr_kil){
                dictionnairekFragment.resetDataSource(source);
                menuSetting.setIcon(getDrawable(R.drawable.ka));
            }else if(id == R.id.action_ang_kil){
                dictionnairekFragment.resetDataSource(source);
                menuSetting.setIcon(getDrawable(R.drawable.fk));
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String activeFragment;

        switch (id){
            case R.id.nav_bookmark:
                activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName();
                if (!activeFragment.equals(BookmarkFragment.class.getSimpleName())){
                    goToFragment(bookmarkFragment, false);
                }
                break;
            case R.id.nav_a_propos:
                activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName();
                if (!activeFragment.equals(BookmarkFragment.class.getSimpleName())){
                    goToFragment(aproposFragment, false);
                }
                break;
            case R.id.nav_home:
                activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName();
                if (!activeFragment.equals(BookmarkFragment.class.getSimpleName())){
                    goToFragment(acceuilFragment, false);
                }
                break;
            default:
                Toast.makeText(getApplicationContext(), "Probleme de menu !!!", Toast.LENGTH_LONG).show();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goToFragment(Fragment fragment, Boolean isTop){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if (!isTop)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName();
        if (activeFragment.equals(BookmarkFragment.class.getSimpleName())){
            menuSetting.setVisible(false);
            toolbar.findViewById(R.id.edit_search).setVisibility(View.GONE);
            toolbar.setTitle("Bookmark");
        }else{
            menuSetting.setVisible(true);
            toolbar.findViewById(R.id.edit_search).setVisibility(View.VISIBLE);
            toolbar.setTitle("");
        }
        return true;
    }
}
