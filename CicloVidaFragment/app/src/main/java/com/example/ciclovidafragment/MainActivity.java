package com.example.ciclovidafragment;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "Mensaje desde Activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
