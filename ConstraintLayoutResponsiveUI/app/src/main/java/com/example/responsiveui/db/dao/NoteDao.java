package com.example.responsiveui.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.responsiveui.db.entity.NoteEntity;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(NoteEntity note);

    @Update
    void update(NoteEntity note);

    @Query("DELETE FROM notes")
    void deleteAll();

    @Query("DELETE FROM notes where id = :idNote")
    void deleteById(int idNote);

    @Query("SELECT * FROM notes ORDER BY title ASC")
    LiveData<List<NoteEntity>> getAll();

    @Query("SELECT * FROM notes WHERE favorite LIKE 'true'")
    LiveData<List<NoteEntity>> getFav( );

}
