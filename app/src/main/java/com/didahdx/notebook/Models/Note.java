package com.didahdx.notebook.Models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/**
 * This is the table structure with the columns
 * */
@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String descrptiom;
    private int priority;

    public Note(String name, String descrptiom, int priority) {
        this.name = name;
        this.descrptiom = descrptiom;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescrptiom() {
        return descrptiom;
    }

    public int getPriority() {
        return priority;
    }



}
