package com.didahdx.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "EXTRA_PRIORITY";

    EditText editTextTitle;
    EditText editTextDescrption;
    NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        CollectIds();

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        if (getIntent() != null) {
            Intent intent = getIntent();
            if (intent.hasExtra(EXTRA_ID)) {
                setTitle("Edit Note");
                editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
                editTextDescrption.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
                numberPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
            }else {
                setTitle("Add Note");
            }

        }

        if (getIntent()==null){
            setTitle("Add Note");
        }
    }

    private void CollectIds() {
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescrption = findViewById(R.id.editTextDescription);
        numberPicker = findViewById(R.id.number_priority);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                SaveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void SaveNote() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescrption.getText().toString().trim();
        int priority = numberPicker.getValue();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}
