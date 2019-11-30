package com.didahdx.notebook.Databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.didahdx.notebook.Dao.NoteDao;
import com.didahdx.notebook.Models.Note;

/**
 * Used  for creating the database and versioning it
 *
 * tables can be passed in as an array if many
 *
 *returns the instance of the database
 * */

@Database(entities = {Note.class} , version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if (instance == null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
