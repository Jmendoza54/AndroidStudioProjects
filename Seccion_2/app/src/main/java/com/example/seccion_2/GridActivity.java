package com.example.seccion_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity {

    private GridView gridView;
    private List<String> names;
    private int counter = 0;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        gridView = (GridView) findViewById(R.id.gridView);

        names = new ArrayList<String>();

        names.add("Jair");
        names.add("Alejandro");
        names.add("Jorge");
        names.add("Francisco");


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GridActivity.this, "Clicked " + names.get(position),Toast.LENGTH_SHORT).show();
            }
        });

        //Enlazamos con nuestro adaptador personalizado
        myAdapter = new MyAdapter(this, R.layout.grid_item, names);
        gridView.setAdapter(myAdapter);

        registerForContextMenu(gridView);
    }

    //Inflamos el menu de opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    //Manejamos eventos click del menu de opciones
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                this.names.add("Added " + (++counter));
                this.myAdapter.notifyDataSetChanged();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    //Inflamos el layout del context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(this.names.get(info.position));
        inflater.inflate(R.menu.context_menu, menu);
    }

    //Manejamos eventos click en el context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.delete_item:
                this.names.remove(info.position);
                this.myAdapter.notifyDataSetChanged();
                return true;

            default:
                return super.onContextItemSelected(item);
        }

    }
}
