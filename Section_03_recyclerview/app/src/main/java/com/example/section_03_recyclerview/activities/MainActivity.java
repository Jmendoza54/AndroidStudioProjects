package com.example.section_03_recyclerview.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.section_03_recyclerview.R;
import com.example.section_03_recyclerview.adapters.MyAdapter;
import com.example.section_03_recyclerview.models.Development;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Development> develops;
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        develops = this.getAllDevelops();

        myRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        myLayoutManager = new LinearLayoutManager(this);
        myLayoutManager = new GridLayoutManager(this, 1);
        //myLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        myAdapter = new MyAdapter(develops, R.layout.recycler_view_item, new MyAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Development develop, int position) {
                //Toast.makeText(MainActivity.this, name + "-" + position, Toast.LENGTH_SHORT).show();
                deleteDevelop(position);
            }
        });

        //myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_name:
                this.addDevelop(0);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private List<Development> getAllDevelops(){
        return new ArrayList<Development>(){{
            add(new Development("Sky tulum", R.drawable.sky));
            add(new Development("Saskab", R.drawable.saskab));
            add(new Development("Icono", R.drawable.icono));
        }};
    }

    private void addDevelop(int position){

        develops.add(position, new Development("New Development" + (++counter),R.drawable.newdev));
        myAdapter.notifyItemInserted(position);
        myLayoutManager.scrollToPosition(position);
    }

    private void deleteDevelop(int position){
        develops.remove(position);
        myAdapter.notifyItemRemoved(position);

    }
}
