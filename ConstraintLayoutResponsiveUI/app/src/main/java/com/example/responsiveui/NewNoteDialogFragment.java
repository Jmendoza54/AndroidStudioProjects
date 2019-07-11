package com.example.responsiveui;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.example.responsiveui.db.entity.NoteEntity;

public class NewNoteDialogFragment extends DialogFragment {

    public static NewNoteDialogFragment newInstance() {
        return new NewNoteDialogFragment();
    }

    private View view;
    private EditText etTitle, etContain;
    private RadioGroup rgColor;
    private Switch switchFvav;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Nueva Nota");
        builder.setMessage("Introduzca los datos")
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String titulo = etTitle.getText().toString();
                        String contain = etContain.getText().toString();
                        String color = "azul";

                        switch (rgColor.getCheckedRadioButtonId()){
                            case R.id.radioButtonRojo:
                               color = "rojo"; break;
                            case R.id.radioButtonVerde:
                                color = "verde"; break;
                        }

                        boolean isFav = switchFvav.isChecked();

                        //Comunicar al viewmodel la nueva nota
                        NewNoteDialogViewModel mViewModel = ViewModelProviders.of(getActivity()).get(NewNoteDialogViewModel.class);
                        mViewModel.insertNote(new NoteEntity(titulo, contain, isFav, color));
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       dialog.dismiss();
                    }
                });

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.new_note_dialog_fragment, null);

        etTitle = view.findViewById(R.id.etTitle);
        etContain = view.findViewById(R.id.etContain);
        rgColor = view.findViewById(R.id.radioGroupColor);
        switchFvav = view.findViewById(R.id.switchFav);

        builder.setView(view);

        // Create the AlertDialog object and return it
        return builder.create();
    }

}
