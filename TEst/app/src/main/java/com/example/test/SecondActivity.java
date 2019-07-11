package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lista;
    List<String> androidVersionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle extras = getIntent().getExtras();
        String emailUser = extras.getString("email");

        setTitle(emailUser);

        lista = findViewById(R.id.listView);
        androidVersionList = new ArrayList<>();
        androidVersionList.add("Pie");
        androidVersionList.add("Oreo");
        androidVersionList.add("Nougat");
        androidVersionList.add("Marshmallow");
        androidVersionList.add("Lollipop");
        androidVersionList.add("Litkat");
        androidVersionList.add("Y mas");

        ArrayAdapter adapterVersionAndroid = new ArrayAdapter(this, android.R.layout.simple_list_item_1, androidVersionList);
        lista.setAdapter(adapterVersionAndroid);

        lista.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String androidVer = androidVersionList.get(position);
        Log.i("App" ," CLick en " + androidVer);
    }
}
