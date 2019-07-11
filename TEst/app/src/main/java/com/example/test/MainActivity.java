package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    EditText editTextEmail, editTextPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btn);
        btnLogin.setOnClickListener(this);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPass = findViewById(R.id.editTextPassword);


    }

    @Override
    public void onClick(View view) {
        Log.i("App", "Click en login");
        String email = editTextEmail.getText().toString();
        String password = editTextPass.getText().toString();

        if(!email.isEmpty() && !password.isEmpty()){

            Intent intentLogin = new Intent(this, SecondActivity.class);
            intentLogin.putExtra("email", email);
            startActivity(intentLogin);
        }else{
            editTextEmail.setError("Complete el email y contraseña");
        }
    }
}
