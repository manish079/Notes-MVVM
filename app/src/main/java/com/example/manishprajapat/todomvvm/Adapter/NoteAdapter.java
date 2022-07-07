package com.example.manishprajapat.todomvvm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manishprajapat.todomvvm.Activity.UpdateNoteActivity;
import com.example.manishprajapat.todomvvm.Model.Note;
import com.example.manishprajapat.todomvvm.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    Context context;
    List<Note> noteList;  //This list get from observer from Main Activity

    List<Note> allNotesForSearch;

    public NoteAdapter() {
    }

    public NoteAdapter(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;

        this.allNotesForSearch = new ArrayList<>(noteList);
    }

    public void filterSearch(List<Note> filterNotesByName) {
        this.noteList = filterNotesByName;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);

        try {
            if (note.priority.equals("1")) {
                holder.prio.setBackgroundResource(R.drawable.green_shape);
            } else if (note.priority.equals("2")) {
                holder.prio.setBackgroundResource(R.drawable.yellow_shape);

            } else {
                holder.prio.setBackgroundResource(R.drawable.red_shape);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.title.setText(noteList.get(position).getNotesTitles());
        holder.subTitle.setText(noteList.get(position).getSubTitle());
        holder.date.setText(noteList.get(position).noteDate);

        //Set notes card view random background-- This is Working Code
//        Random random=new Random();
//        int color= Color.argb(255,random.nextInt(255),random.nextInt(255),random.nextInt(256));
//        holder.cardView.setBackgroundColor(color);
//        holder.cardView.setPadding(20,20,20,20);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateNoteActivity.class);
                intent.putExtra("id", note.getId());
                intent.putExtra("title", note.getNotesTitles());
                intent.putExtra("subTitle", note.getSubTitle());
                intent.putExtra("description", note.getNotesDescription());
                intent.putExtra("prio", note.priority);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView title, subTitle, date;
        View prio;
        CardView cardView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            subTitle = itemView.findViewById(R.id.tvSubTitle);
            date = itemView.findViewById(R.id.tvDate);
            prio = itemView.findViewById(R.id.notesPriority);
            cardView = itemView.findViewById(R.id.listCardView);
        }
    }
}
