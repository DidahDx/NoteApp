package com.didahdx.notebook.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.didahdx.notebook.Models.Note;
import com.didahdx.notebook.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{

    private List<Note> notes= new ArrayList<>();

    //this class holds the views
    class NoteViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView descrption;
        private TextView priority;

        public NoteViewHolder(View itemView){
            super(itemView);

            title=itemView.findViewById(R.id.text_view_title);
            descrption=itemView.findViewById(R.id.text_view_descrption);
            priority=itemView.findViewById(R.id.text_view_priority);

        }
    }


    public void setNotes(List<Note> notes){
        this.notes=notes;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.note_item,parent,false);

        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote=notes.get(position);
        holder.title.setText(currentNote.getName());
        holder.descrption.setText(currentNote.getDescrptiom());
        holder.priority.setText(String.valueOf(currentNote.getPriority()));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


}
