package com.example.responsiveui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.responsiveui.db.entity.NoteEntity;

import java.util.List;

public class NewNoteDialogViewModel extends AndroidViewModel {

    private LiveData<List<NoteEntity>> allNotes;
    private NoteRepository noteRepository;

    public NewNoteDialogViewModel(Application application){
        super(application);

        noteRepository = new NoteRepository(application);
        allNotes  = noteRepository.getAll();
    }

    //El Fragmento que necesita recibir la nueva lista de datos
    public LiveData<List<NoteEntity>> getAllNotes(){
        return allNotes;
    }

    //Elfragmento que inserte una nueva nota debera comunicarlo a este ViewModel
    public void insertNote(NoteEntity newNoteEntity){
        noteRepository.insert(newNoteEntity);
    }

    public void updateNote(NoteEntity noteActualizarEntity){
        noteRepository.update(noteActualizarEntity);
    }
}
