package com.example.amantewary.scrawl.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amantewary.scrawl.Handlers.NoteHandler;
import com.example.amantewary.scrawl.R;
import com.example.amantewary.scrawl.ViewNotesActivity;
import com.l4digital.fastscroll.FastScroller;

import java.util.List;

public class FilteredNotesListAdapter extends RecyclerView.Adapter<ViewHolder> implements FastScroller.SectionIndexer {

    private Context context;
    private List<NoteHandler> notesList;

    public FilteredNotesListAdapter(Context context, List<NoteHandler> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NoteHandler notes = notesList.get(position);
        holder.title.setText(notes.getTitle());
        holder.label.setText(notes.getLabel_name());
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewNotesActivity.class);
                intent.putExtra("noteid", notes.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.notesList.size();
    }

    @Override
    public String getSectionText(int position) {
        if (notesList.get(position).getTitle().equals("") || notesList.get(position).getTitle() == null) {
            return "a";
        } else {
            return String.valueOf(notesList.get(position).getTitle().charAt(0));
        }
    }
}
