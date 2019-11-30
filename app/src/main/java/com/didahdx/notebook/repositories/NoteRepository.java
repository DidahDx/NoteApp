package com.didahdx.notebook.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.didahdx.notebook.Dao.NoteDao;
import com.didahdx.notebook.Databases.NoteDatabase;
import com.didahdx.notebook.Models.Note;

import java.util.List;

/**
 * Access point/API for database
 *
 * Class is called to communicate with the database for CRUD operations
 * */

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){
        NoteDatabase noteDatabase=NoteDatabase.getInstance(application);
        noteDao=noteDatabase.noteDao();
        allNotes=noteDao.getAllNotes();
    }

    //used to insert a note
    public void insert(Note note){
       new InsertNoteAsyncTask(noteDao).execute(note);
    }

    //used to update a note
    public void update(Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    //used to delete a note
    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    //used to delete all notes
    public void deleteAllNotes(){
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    // //used to get all the notes
    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

      /**
       * running in background thread to perform the operations
       *
       * static to avoid memory leaks
       * **/
    private static class InsertNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao){
            this.noteDao=noteDao;
        }

          @Override
          protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
              return null;
          }
      }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao){
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao){
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void,Void,Void>{

        private NoteDao noteDao;

        private DeleteAllNotesAsyncTask(NoteDao noteDao){
            this.noteDao=noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }


}
