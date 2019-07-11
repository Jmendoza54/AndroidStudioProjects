package com.example.animationautomatics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = findViewById(R.id.textViewInfo);
    }

    public void cambiarVisibilidad(View view) {
        if(tvInfo.getVisibility() == View.VISIBLE){
            tvInfo.setVisibility(View.GONE);
        }else{
            tvInfo.setVisibility(View.VISIBLE);
        }
    }
}
