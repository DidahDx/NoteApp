package com.didahdx.notebook.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.didahdx.notebook.Models.Note;
import com.didahdx.notebook.R;

import java.util.ArrayList;
import java.util.List;


/***
 * ListAdapter has logic to compare to list in background thread
 *
 * ***/
public class NoteAdapter extends ListAdapter<Note,NoteAdapter.NoteViewHolder> {

//    private List<Note> notes = new ArrayList<>();
    private onItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK=new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId()== newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getDescrptiom().equals(newItem.getDescrptiom()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    //this class holds the views
    class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView descrption;
        private TextView priority;

        public NoteViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.text_view_title);
            descrption = itemView.findViewById(R.id.text_view_descrption);
            priority = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }

                }
            });

        }
    }

    //returns the note at a specific position
    public Note getNoteAt(int positon) {
        return getItem(positon);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);

        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.title.setText(currentNote.getName());
        holder.descrption.setText(currentNote.getDescrptiom());
        holder.priority.setText(String.valueOf(currentNote.getPriority()));

    }

    public interface onItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

}
