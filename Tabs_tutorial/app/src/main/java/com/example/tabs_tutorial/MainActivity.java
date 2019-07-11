package com.example.tabs_tutorial;

import android.os.Bundle;
import android.widget.Toolbar;;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    private void setSupportActionBar(Toolbar myToolbar) {
    }


}
