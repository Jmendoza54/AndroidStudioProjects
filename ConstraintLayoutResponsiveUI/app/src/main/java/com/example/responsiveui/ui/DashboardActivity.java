package com.example.responsiveui.ui;

import android.os.Bundle;

import com.example.responsiveui.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;

public class DashboardActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment f = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    f = new NoteFragment();
                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
            }

            if(f != null){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, f)
                        .commit();
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new NoteFragment())
                .commit();
    }

}
