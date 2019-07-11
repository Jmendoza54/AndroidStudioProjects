package com.example.firebase;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String PATH_START = "start";
    private static final String PATH_MESSAGE = "message";
    @BindView(R.id.editTextMessage)
    EditText editTextMessage;
    @BindView(R.id.btnSend)
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final TextView tvMessage = findViewById(R.id.tvMessage);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference(PATH_START).child(PATH_MESSAGE);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvMessage.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error al consultar en Firebase " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btnSend)
    public void onViewClicked() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference(PATH_START).child(PATH_MESSAGE);

        reference.setValue(editTextMessage.getText().toString());
        editTextMessage.setText("");
    }
}
