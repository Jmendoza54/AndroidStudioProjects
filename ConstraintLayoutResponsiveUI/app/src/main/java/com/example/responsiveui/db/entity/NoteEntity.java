package com.example.responsiveui.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")

public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String contenido;
    public boolean favorite;
    public String color;

    public NoteEntity(String title, String contenido, boolean favorite, String color) {

        this.title = title;
        this.contenido = contenido;
        this.favorite = favorite;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
