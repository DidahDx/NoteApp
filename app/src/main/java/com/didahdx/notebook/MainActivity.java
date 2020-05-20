package com.didahdx.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.didahdx.notebook.Adapters.NoteAdapter;
import com.didahdx.notebook.Models.Note;
import com.didahdx.notebook.ViewModel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private NoteViewModel noteViewModel;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FindIds();

        floatingActionButton.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.submitList(notes);
            }
        });

        //used to handle delete on swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note at position " + viewHolder.getAdapterPosition() + " deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        noteAdapter.setOnItemClickListener(new NoteAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getName());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescrptiom());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    //collect the ids
    private void FindIds() {
        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.button_add_note);
    }


    @Override
    public void onClick(View v) {
        if (v == floatingActionButton) {
            startActivityForResult(new Intent(MainActivity.this, AddEditNoteActivity.class), ADD_NOTE_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //used to insert a new note
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK  && data!= null) {

            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

            noteViewModel.insert(new Note(title, description, priority));

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        }
        //used to update notes
        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK && data!= null) {


            int id=data.getIntExtra(AddEditNoteActivity.EXTRA_ID,-1);

            if (id!=-1){

                String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
                String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
                int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);
                Note note=new Note(title,description,priority);
                note.setId(id);

                noteViewModel.update(note);

                Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this, "Note can not be updated", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "Deleted all Notes", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
