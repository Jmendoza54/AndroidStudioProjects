package com.example.realm.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.realm.R;
import com.example.realm.adapters.BoardAdapter;
import com.example.realm.models.Board;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class BoardActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, RealmChangeListener<RealmResults<Board>> {

    private Realm realm;

    private FloatingActionButton fab;
    private ListView listView;
    private BaseAdapter adapter;
    private RealmResults<Board> boards;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        //DB Realm
        realm = Realm.getDefaultInstance();
        boards = realm.where(Board.class).findAll();
        boards.addChangeListener(this);


        adapter = new BoardAdapter(this, boards, R.layout.list_view_board_item);
        listView = findViewById(R.id.listViewBoard);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        fab = findViewById(R.id.fabAddBoard);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertForCreatingBoard("Add New Board","Set Name");
            }
        });

        registerForContextMenu(listView);
    }

    //CRUD -> Create
    private void createNewBoard(String boardName) {
        // Asi en caso de ser accion pequeña
        realm.beginTransaction();
        Board board = new Board(boardName);
        realm.copyToRealm(board);
        realm.commitTransaction();

        //EN caso de ser accion grande y se pone final en la variable boardName en este metodo
        /*realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Board board = new Board(boardName);
                realm.copyToRealm(board);
            }
        });*/
    }

    private void editBoard(String newName, Board board){
        realm.beginTransaction();
        board.setTitle(newName);
        realm.copyToRealmOrUpdate(board);
        realm.commitTransaction();
    }

    private void deleteBoard(Board board){
        realm.beginTransaction();
        board.deleteFromRealm();
        realm.commitTransaction();
    }

    private void deleteAll(){
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    //Dialogs
    private void showAlertForCreatingBoard(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (title != null) builder.setTitle(title);
        if (message != null) builder.setMessage(message);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_board,null);
        builder = builder.setView(viewInflated);

        final EditText input = viewInflated.findViewById(R.id.editTextNewBoard);

        builder.setPositiveButton("add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String boardName = input.getText().toString().trim();
                if(boardName.length() > 0)
                    createNewBoard(boardName);
                else
                    Toast.makeText(getApplicationContext(),"the name is required to create a new Board", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog =  builder.create();
        dialog.show();

    }

    private void showAlertForEditingBoard(String title, String message, final Board board){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (title != null) builder.setTitle(title);
        if (message != null) builder.setMessage(message);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_board,null);
        builder = builder.setView(viewInflated);

        final EditText input = viewInflated.findViewById(R.id.editTextNewBoard);
        input.setText(board.getTitle());

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String boardName = input.getText().toString().trim();
                if(boardName.length() == 0)
                    Toast.makeText(getApplicationContext(), "the name is required to edit the current Board", Toast.LENGTH_SHORT).show();
                else if(boardName.equals(board.getTitle()))
                    Toast.makeText(getApplicationContext(),"the name is the same than it was before", Toast.LENGTH_SHORT).show();
                else
                    editBoard(boardName, board);



            }
        });

        AlertDialog dialog =  builder.create();
        dialog.show();

    }

    //Events

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_board_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.delete_all:
                deleteAll();
                return true;

             default:
                 return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(boards.get(info.position).getTitle());
        getMenuInflater().inflate(R.menu.context_menu_board_activity, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.delete_board:
                deleteBoard(boards.get(info.position));
                return true;

            case R.id.edit_board:
                showAlertForEditingBoard("Edit Board", "Change the name of the board", boards.get(info.position));
                return true;

            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(BoardActivity.this,NoteActivity.class);
        intent.putExtra("id", boards.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onChange(RealmResults<Board> boards) {
        adapter.notifyDataSetChanged();
    }
}
