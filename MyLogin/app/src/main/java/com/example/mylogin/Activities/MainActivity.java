package com.example.mylogin.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mylogin.R;
import com.example.mylogin.Utils.Util;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                logOut();
                return true;

            case R.id.menu_forget_log:
                Util.removeSharedPref(prefs);
                logOut();
                return true;

             default:
                 return super.onOptionsItemSelected(item);
        }

    }

    private void logOut(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}
