package com.example.mensajessnackbarandtoast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnToast, btnSnack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSnack = findViewById(R.id.buttonSnack);
        btnToast = findViewById(R.id.buttonToast);

        btnToast.setOnClickListener(this);
        btnSnack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonToast){
            Toast.makeText(this, "Mensaje de Toast", Toast.LENGTH_LONG).show();
        }else if(view.getId() == R.id.buttonSnack){
            Snackbar.make(view, "Mensaje desde SnackBar", Snackbar.LENGTH_LONG).show();
        }
    }
}
