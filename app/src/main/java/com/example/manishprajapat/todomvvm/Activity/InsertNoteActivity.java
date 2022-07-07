package com.example.manishprajapat.todomvvm.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Toast;

import com.example.manishprajapat.todomvvm.Model.Note;
import com.example.manishprajapat.todomvvm.Model.NoteViewModel;
import com.example.manishprajapat.todomvvm.R;
import com.example.manishprajapat.todomvvm.databinding.ActivityInsertNoteBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InsertNoteActivity extends AppCompatActivity {

    ActivityInsertNoteBinding activityInsertNoteBinding;
    String title, subTitle, desc;
    NoteViewModel noteViewModel;


    String priority = "1";  // 1 bcoz if user not select any priority then bydefault green

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInsertNoteBinding = ActivityInsertNoteBinding.inflate(getLayoutInflater());
        setContentView(activityInsertNoteBinding.getRoot());

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);


        activityInsertNoteBinding.fabNotesDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = activityInsertNoteBinding.etTitle.getText().toString().trim();
                subTitle = activityInsertNoteBinding.etSubTitle.getText().toString().trim();
                desc = activityInsertNoteBinding.etMore.getText().toString().trim();

                if(!title.isEmpty() && !subTitle.isEmpty()){
                    createsNotes(title, subTitle, desc);

                }
                else{
                    Toast.makeText(InsertNoteActivity.this, "Please add note...", Toast.LENGTH_SHORT).show();
                }

            }
        });

        activityInsertNoteBinding.greenPrio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priority = "1";
                activityInsertNoteBinding.greenPrio.setImageResource(R.drawable.ic_done);
                activityInsertNoteBinding.yellowPrio.setImageResource(0);
                activityInsertNoteBinding.redPrio.setImageResource(0);
            }
        });
        activityInsertNoteBinding.yellowPrio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priority = "2";
                activityInsertNoteBinding.greenPrio.setImageResource(0);
                activityInsertNoteBinding.yellowPrio.setImageResource(R.drawable.ic_done);
                activityInsertNoteBinding.redPrio.setImageResource(0);
            }
        });
        activityInsertNoteBinding.redPrio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priority = "3";
                activityInsertNoteBinding.greenPrio.setImageResource(0);
                activityInsertNoteBinding.yellowPrio.setImageResource(0);
                activityInsertNoteBinding.redPrio.setImageResource(R.drawable.ic_done);
            }
        });

    }

    private void createsNotes(String title, String subTitle, String desc) {

        Note note = new Note();
        note.notesTitles = title;
        note.subTitle = subTitle;
        note.notesDescription = desc;


        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        String currentDate = sdf.format(new Date());

        note.noteDate = currentDate;  //insert date in note

        note.priority = priority;

        noteViewModel.insertNote(note);
        Toast.makeText(this, "Notes Added!!", Toast.LENGTH_SHORT).show();

        finish();

    }
}