package com.example.fragments.Fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fragments.R;

public class DataFragment extends Fragment {

    private EditText textData;
    private Button btnSend;
    private DataListener callback;

    public DataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            callback = (DataListener) context;
        } catch (Exception e){
            throw new ClassCastException(context.toString() + "should implement DataListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_data, container, false);

        //Logica para capturar elementos de la UI
        textData = view.findViewById(R.id.editTextData);
        btnSend = view.findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textsend = textData.getText().toString();
                callback.sendData(textsend);
            }
        });

        return view;
    }


    public interface DataListener{
        void sendData(String text);
    }
}
