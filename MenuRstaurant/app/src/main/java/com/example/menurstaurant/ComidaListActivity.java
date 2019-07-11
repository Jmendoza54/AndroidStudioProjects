package com.example.menurstaurant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menurstaurant.dummy.DummyContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComidaListActivity extends AppCompatActivity {

    private static final String PATH_FOOD = "food";
    private static final String PATH_PROFILE = "profile";
    private static final String PATH_CODE = "code";


    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPrice)
    EditText etPrice;
    @BindView(R.id.btnSave)
    Button btnSave;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comida_list);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(PATH_FOOD);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DummyContent.Comida comida = dataSnapshot.getValue(DummyContent.Comida.class);
                comida.setId(dataSnapshot.getKey());

                if (!DummyContent.ITEMS.contains(comida)) {
                    DummyContent.addItem(comida);
                }

                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DummyContent.Comida comida = dataSnapshot.getValue(DummyContent.Comida.class);
                comida.setId(dataSnapshot.getKey());

                if (DummyContent.ITEMS.contains(comida)) {
                    DummyContent.updateItem(comida);
                }

                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                DummyContent.Comida comida = dataSnapshot.getValue(DummyContent.Comida.class);
                comida.setId(dataSnapshot.getKey());

                if (DummyContent.ITEMS.contains(comida)) {
                    DummyContent.deleteItem(comida);
                }

                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Toast.makeText(ComidaListActivity.this, "Moved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ComidaListActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btnSave)
    public void onViewClicked() {
        String name = etName.getText().toString().trim();
        String price = etPrice.getText().toString().trim();

        DummyContent.Comida comida = new DummyContent.Comida(name, price);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(PATH_FOOD);
        reference.push().setValue(comida);


        etName.setText("");
        etPrice.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_info:
                final TextView tvCode = new TextView(this);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                );

                tvCode.setLayoutParams(params);
                tvCode.setGravity(Gravity.CENTER_HORIZONTAL);
                tvCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference(PATH_PROFILE).child(PATH_CODE);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        tvCode.setText(dataSnapshot.getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(ComidaListActivity.this, "Error " + databaseError, Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(R.string.comidaList_dialog_title).setPositiveButton(R.string.comidaList_dialog_ok, null);
                builder.setView(tvCode);
                builder.show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ComidaListActivity mParentActivity;
        private final List<DummyContent.Comida> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyContent.Comida item = (DummyContent.Comida) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ComidaDetailFragment.ARG_ITEM_ID, item.getId());
                    ComidaDetailFragment fragment = new ComidaDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ComidaDetailActivity.class);
                    intent.putExtra(ComidaDetailFragment.ARG_ITEM_ID, item.getId());

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ComidaListActivity parent,
                                      List<DummyContent.Comida> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comida_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {


            holder.mIdView.setText("$" + mValues.get(position).getPrecio());
            holder.mContentView.setText(mValues.get(position).getNombre());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);

            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference(PATH_FOOD);

                    reference.child(mValues.get(position).getId()).removeValue();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;
            @BindView(R.id.btnDelete)
            Button btnDelete;

            ViewHolder(View view) {
                super(view);

                ButterKnife.bind(this,view);

                mIdView = view.findViewById(R.id.id_text);
                mContentView = view.findViewById(R.id.nombre);
            }
        }
    }
}
