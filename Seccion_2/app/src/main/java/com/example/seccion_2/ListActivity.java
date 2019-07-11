package com.example.seccion_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.widget.AdapterView.*;

public class ListActivity extends AppCompatActivity {

    private ListView lview;
    private List<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lview = (ListView)findViewById(R.id.listview);

        names = new ArrayList<String>();

        names.add("Jair");
        names.add("Alejandro");
        names.add("Jorge");
        names.add("Francisco");
        names.add("Jair");
        names.add("Alejandro");
        names.add("Jorge");
        names.add("Francisco");
        names.add("Jair");
        names.add("Alejandro");
        names.add("Jorge");
        names.add("Francisco");

        //Adaptador, la forma visual en que mostramos los datos
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, names);
        //ENlazamos el adaptador con nuestro listview
        //lview.setAdapter(adapter);

        lview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListActivity.this, "Clicked " + names.get(position),Toast.LENGTH_SHORT).show();
            }
        });

        //Enlazamos con nuestro adaptador personalizado
        MyAdapter myAdapter = new MyAdapter(this, R.layout.list_item, names);
        lview.setAdapter(myAdapter);
    }
}

