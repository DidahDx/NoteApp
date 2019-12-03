package com.didahdx.notebook.Databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDbAsyncTask(instance).execute();
        }
    };

    //background thread to populate the database on initial creation
    private static class populateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;

        private populateDbAsyncTask(NoteDatabase noteDatabase){
            noteDao=noteDatabase.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title One","Descrption one",1));
            noteDao.insert(new Note("Title Two","Descrption Two",2));
            noteDao.insert(new Note("Title Three","Descrption Three",3));
            return null;
        }
    }
}
