package com.example.seccion_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private TextView text;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Activar flecha atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        text = (TextView) findViewById(R.id.textViewMain);
        btnNext = (Button) findViewById(R.id.btnThird);

        //Tomar los datos del intent
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
           String greeter = bundle.getString("greeter");
            Toast.makeText(SecondActivity.this, greeter , Toast.LENGTH_LONG).show();
            text.setText(greeter);
        }else{
            Toast.makeText(SecondActivity.this, "it is empty" , Toast.LENGTH_LONG).show();
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });
    }


}
