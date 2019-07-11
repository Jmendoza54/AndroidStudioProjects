package com.example.navigationdrawertest.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.navigationdrawertest.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Tools extends Fragment implements View.OnClickListener, DialogInterface.OnClickListener{

    private FloatingActionButton fab;
    private TextView textViewTitle;

    private AlertDialog.Builder builder;
    private Switch switchBtn;

    public Tools() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tools, container, false);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        textViewTitle = view.findViewById(R.id.textTitle);

        return view;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            if (switchBtn.isChecked()) {
                textViewTitle.setText("Alerts Enabled");
            } else {
                textViewTitle.setText("Alerts Disabled");
            }
        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
            dialogInterface.cancel();
        }
    }

    @Override
    public void onClick(View view) {
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Alert");
        builder.setMessage("Change the option");

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_fragment_dialog, null);

        switchBtn = dialogView.findViewById(R.id.switchDialog);

        builder.setView(dialogView);

        // Set up the buttons
        builder.setPositiveButton("OK", this);
        builder.setNegativeButton("Cancel", this);
        builder.show();
    }
}
