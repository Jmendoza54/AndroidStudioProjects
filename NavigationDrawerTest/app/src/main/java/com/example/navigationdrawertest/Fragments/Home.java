package com.example.navigationdrawertest.Fragments;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navigationdrawertest.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Home extends Fragment implements View.OnClickListener {

    private FloatingActionButton fab;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home, container, false);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        return view;
    }

    public void onClick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Information");
        builder.setMessage("This alert dialog is just a show normal");
        builder.setNeutralButton("Go it", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
