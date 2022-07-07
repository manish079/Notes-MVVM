package com.example.manishprajapat.todomvvm.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manishprajapat.todomvvm.Adapter.NoteAdapter;
import com.example.manishprajapat.todomvvm.Database.NoteRoomDatabase;
import com.example.manishprajapat.todomvvm.MainActivity;
import com.example.manishprajapat.todomvvm.Model.Note;
import com.example.manishprajapat.todomvvm.Model.NoteViewModel;
import com.example.manishprajapat.todomvvm.R;
import com.example.manishprajapat.todomvvm.Repo.NotesRepository;
import com.example.manishprajapat.todomvvm.databinding.ActivityUpdateNoteBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateNoteActivity extends AppCompatActivity {

    BottomSheetDialog dialog;
    ActivityUpdateNoteBinding updateNoteBinding;
    String priority = "1";
    NoteViewModel noteViewModel;
    String uTitle, uSubTile, uDesc, uPriority;
    int uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateNoteBinding = ActivityUpdateNoteBinding.inflate(getLayoutInflater());
        setContentView(updateNoteBinding.getRoot());

        //Get Data from NoteAdapter class
        uId = getIntent().getIntExtra("id", 0);//default value add if integer id value not get then default passed
        uTitle = getIntent().getStringExtra("title");
        uSubTile = getIntent().getStringExtra("subTitle");
        uPriority = getIntent().getStringExtra("prio");
        uDesc = getIntent().getStringExtra("description");

        updateNoteBinding.etTitle.setText(uTitle);
        updateNoteBinding.etSubTitle.setText(uSubTile);
        updateNoteBinding.etUDesc.setText(uDesc);


        if (uPriority.equals("1")) {
            updateNoteBinding.greenPrio.setImageResource(R.drawable.ic_done);
        } else if (uPriority.equals("2")) {
            updateNoteBinding.yellowPrio.setImageResource(R.drawable.ic_done);
        } else if (uPriority.equals("3")) {
            updateNoteBinding.redPrio.setImageResource(R.drawable.ic_done);
        }


        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);  //for accessing data

        updateNoteBinding.fabNotesDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String title = updateNoteBinding.etTitle.getText().toString();
                String subTitle = updateNoteBinding.etSubTitle.getText().toString();
                String desc = updateNoteBinding.etUDesc.getText().toString();

                if(!title.isEmpty() && !subTitle.isEmpty()){
                    updateNotes(title, subTitle, desc);
                    startActivity(new Intent(UpdateNoteActivity.this, MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(UpdateNoteActivity.this, "Please add note...", Toast.LENGTH_SHORT).show();
                }



            }
        });


        updateNoteBinding.greenPrio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priority = "1";
                updateNoteBinding.greenPrio.setImageResource(R.drawable.ic_done);
                updateNoteBinding.yellowPrio.setImageResource(0);
                updateNoteBinding.redPrio.setImageResource(0);
            }
        });
        updateNoteBinding.yellowPrio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priority = "2";
                updateNoteBinding.greenPrio.setImageResource(0);
                updateNoteBinding.yellowPrio.setImageResource(R.drawable.ic_done);
                updateNoteBinding.redPrio.setImageResource(0);
            }
        });
        updateNoteBinding.redPrio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priority = "3";
                updateNoteBinding.greenPrio.setImageResource(0);
                updateNoteBinding.yellowPrio.setImageResource(0);
                updateNoteBinding.redPrio.setImageResource(R.drawable.ic_done);
            }
        });


    }

    private void updateNotes(String title, String subTitle, String desc) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        String updatedates = dateFormat.format(new Date());

        Note upNote = new Note();
        upNote.notesTitles = title;
        upNote.id = uId;
        upNote.subTitle = subTitle;
        upNote.notesDescription = desc;
        upNote.noteDate = updatedates;
        upNote.priority = priority;

        noteViewModel.updateNote(upNote);
        Toast.makeText(this, "Notes Updated!!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.app_bar_delete) {
            dialog = new BottomSheetDialog(this);
            View view = getLayoutInflater().inflate(R.layout.delete_note_layout, findViewById(R.id.linearLayout));
            dialog.setContentView(view);

            TextView yes, no;

            yes = view.findViewById(R.id.tvYes);
            no = view.findViewById(R.id.tvNo);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //delete notes
                    noteViewModel.deleteNote(uId);
                    Toast.makeText(UpdateNoteActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        }
        return super.onOptionsItemSelected(item);
    }

}