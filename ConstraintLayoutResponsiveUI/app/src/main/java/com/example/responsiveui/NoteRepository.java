package com.example.responsiveui;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.responsiveui.db.NoteRoomDatabase;
import com.example.responsiveui.db.dao.NoteDao;
import com.example.responsiveui.db.entity.NoteEntity;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<NoteEntity>> allNotes;
    private LiveData<List<NoteEntity>> allFavNotes;


    public NoteRepository(Application application){
        NoteRoomDatabase db = NoteRoomDatabase.getDatabase(application);
        noteDao = db.noteDao();
        allNotes = noteDao.getAll();
        allFavNotes = noteDao.getFav();
    }

    public LiveData<List<NoteEntity>> getAll(){
       return  allNotes;
    }

    public LiveData<List<NoteEntity>> getAllFav(){
        return  allFavNotes;
    }

    public void insert(NoteEntity note){
        new insertAsyncTask(noteDao).execute(note);
    }

    private static class insertAsyncTask extends AsyncTask<NoteEntity, Void, Void>{
        private NoteDao noteDaoAsyncTask;

        insertAsyncTask(NoteDao dao){
            noteDaoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDaoAsyncTask.insert(noteEntities[0]);
            return null;
        }
    }

    public void update(NoteEntity note){
        new updateAsyncTask(noteDao).execute(note);
    }

    private static class updateAsyncTask extends AsyncTask<NoteEntity, Void, Void>{
        private NoteDao noteDaoAsyncTask;

        updateAsyncTask(NoteDao dao){
            noteDaoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDaoAsyncTask.update(noteEntities[0]);
            return null;
        }
    }
}
