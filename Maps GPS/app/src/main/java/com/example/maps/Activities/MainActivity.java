package com.example.maps.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.maps.Fragments.MapFragment;
import com.example.maps.Fragments.WelcomeFragment;
import com.example.maps.R;

public class MainActivity extends AppCompatActivity {

    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            currentFragment = new WelcomeFragment();
            changeFragment(currentFragment);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.menuWelcome:
                currentFragment = new WelcomeFragment();
                break;

            case R.id.menuMap:
                currentFragment = new MapFragment();
                break;

        }
        changeFragment(currentFragment);
        return super.onOptionsItemSelected(item);

    }

    private void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }
}
