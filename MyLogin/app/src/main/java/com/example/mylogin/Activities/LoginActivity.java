package com.example.mylogin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mylogin.R;
import com.example.mylogin.Utils.Util;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    private EditText editTextEmail;
    private EditText editTextPass;
    private Switch switchRem;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindUI();

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        setCredentialsIfExists();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPass.getText().toString();
                if(login(email, password)){
                    goToMain();
                    saveOnPref(email, password);
                }
            }
        });
    }

    private void bindUI(){
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPass = findViewById(R.id.editTextPassword);
        switchRem = findViewById(R.id.switchRemember);
        btnLogin = findViewById(R.id.buttonLogin);

    }

    private void setCredentialsIfExists(){
        String email = Util.getUserMailPrefs(prefs);
        String pass = Util.getUserpassPrefs(prefs);

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
            editTextEmail.setText(email);
            editTextPass.setText(pass);
            switchRem.setChecked(true);
        }
    }

    private boolean login(String email, String password){

        if(!isValidEmail(email)){
            Toast.makeText(this,"Email is not valid", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isValidPassword(password)){
            Toast.makeText(this,"Password is not valid", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private void saveOnPref(String email, String password){
        if(switchRem.isChecked()){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", email);
            editor.putString("pass", password);
            editor.apply();
        }
    }

    private boolean isValidEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password){
        return password.length() >= 4;
    }

    private void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
