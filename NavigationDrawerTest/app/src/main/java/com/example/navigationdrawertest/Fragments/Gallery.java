package com.example.navigationdrawertest.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.navigationdrawertest.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Gallery extends Fragment implements View.OnClickListener, DialogInterface.OnClickListener {

    private FloatingActionButton fab;
    private TextView textViewTitle;

    private AlertDialog.Builder builder;
    private EditText editTextMail;


    public Gallery() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.gallery, container, false);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        textViewTitle = view.findViewById(R.id.textTitle);

        return view;
    }

    @Override
    public void onClick(View view) {
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("EMAIL");
        builder.setMessage("Type your email address to be displayed in the middle of the screen");

        // Crear Input dentro del dialog
        editTextMail = new EditText(getContext());
        editTextMail.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(editTextMail);

        // Set up the buttons
        builder.setPositiveButton("OK", this);
        builder.setNegativeButton("Cancel", this);
        builder.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {

            String email = editTextMail.getText().toString();
            if (!email.isEmpty()) {
                textViewTitle.setText(email);
            }

        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
            dialogInterface.cancel();
        }
    }
}
