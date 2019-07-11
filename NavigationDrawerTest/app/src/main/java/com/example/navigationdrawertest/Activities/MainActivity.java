package com.example.navigationdrawertest.Activities;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.example.navigationdrawertest.Fragments.Gallery;
import com.example.navigationdrawertest.Fragments.Home;
import com.example.navigationdrawertest.Fragments.Tools;
import com.example.navigationdrawertest.R;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        setFragmentByDefault();

        Switch switchBtn = (Switch) navigationView.getMenu().findItem(R.id.switch_in_nav_options).getActionView();
        switchBtn.setOnCheckedChangeListener(this);
    }

    @Override
    public void onBackPressed() {
         drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        boolean fragmentTransaction = false;
        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragment = new Home();
            fragmentTransaction = true;
        } else if (id == R.id.nav_gallery) {
            fragment = new Gallery();
            fragmentTransaction = true;
        } else if (id == R.id.nav_slideshow) {
            fragment = new Home();
            fragmentTransaction = true;
        } else if (id == R.id.nav_tools) {
            fragment = new Tools();
            fragmentTransaction = true;
        } else if (id == R.id.nav_share) {
            fragment = new Gallery();
            fragmentTransaction = true;
        } else if (id == R.id.nav_send) {
            fragment = new Tools();
            fragmentTransaction = true;
        }

        if(fragmentTransaction){
            changeFragment(fragment, item);
            drawer.closeDrawer(GravityCompat.START);
        }

        return true;
    }

    private void setToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setFragmentByDefault(){
        changeFragment(new Home(), navigationView.getMenu().getItem(0));
    }

    private void changeFragment(Fragment fragment, MenuItem item){
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        if (checked) {
            Toast.makeText(MainActivity.this, "The option is checked", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "The option is unchecked", Toast.LENGTH_SHORT).show();
        }
    }
}
