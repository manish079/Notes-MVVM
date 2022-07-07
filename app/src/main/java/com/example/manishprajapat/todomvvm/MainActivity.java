package com.example.manishprajapat.todomvvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.manishprajapat.todomvvm.Activity.InsertNoteActivity;
import com.example.manishprajapat.todomvvm.Adapter.NoteAdapter;
import com.example.manishprajapat.todomvvm.Model.Note;
import com.example.manishprajapat.todomvvm.Model.NoteViewModel;
import com.example.manishprajapat.todomvvm.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;
    NoteViewModel noteViewModel;
    NoteAdapter noteAdapter;

    String filter, lowToHigh, highToLow;
    List<Note> filterNotesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());


        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        mainBinding.tvNoFilter.setBackgroundResource(R.drawable.text_view_design_main_top);

        mainBinding.tvNoFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(0);
                mainBinding.tvNoFilter.setBackgroundResource(R.drawable.text_view_design_main_top);
                mainBinding.tvLtoHigh.setBackgroundResource(R.drawable.text_view_main_no_border);
                mainBinding.tvHToLow.setBackgroundResource(R.drawable.text_view_main_no_border);
            }
        });
        mainBinding.tvLtoHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(1);
                mainBinding.tvNoFilter.setBackgroundResource(R.drawable.text_view_main_no_border);
                mainBinding.tvLtoHigh.setBackgroundResource(R.drawable.text_view_design_main_top);
                mainBinding.tvHToLow.setBackgroundResource(R.drawable.text_view_main_no_border);

            }
        });
        mainBinding.tvHToLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(2);
                mainBinding.tvNoFilter.setBackgroundResource(R.drawable.text_view_main_no_border);
                mainBinding.tvLtoHigh.setBackgroundResource(R.drawable.text_view_main_no_border);
                mainBinding.tvHToLow.setBackgroundResource(R.drawable.text_view_design_main_top);
            }
        });


        mainBinding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InsertNoteActivity.class));
            }
        });

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        noteViewModel.allNotes.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                setAdapterOnRecyclerView(notes);
                filterNotesList = notes;
            }
        });

    }

    private void loadData(int i) {
        if (i == 0) {  //We want No filter data
            noteViewModel.allNotes.observe(this, new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    setAdapterOnRecyclerView(notes);
                    filterNotesList = notes;
                }
            });
        } else if (i == 2) {
            noteViewModel.mHighToLow.observe(this, new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    setAdapterOnRecyclerView(notes);
                    filterNotesList = notes;
                }
            });
        } else if (i == 1) {
            noteViewModel.mLowToHigh.observe(this, new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    setAdapterOnRecyclerView(notes);
                    filterNotesList = notes;
                }
            });
        }

    }

    public void setAdapterOnRecyclerView(List<Note> notes) {

        mainBinding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        noteAdapter = new NoteAdapter(MainActivity.this, notes);
        mainBinding.recyclerView.setAdapter(noteAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setQueryHint("Search notes...");
        searchView.isSubmitButtonEnabled();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                notesFilter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void notesFilter(String newText) {        //show note as we search
//        Log.d("search", "notesFilter: "+s+"\n");
        ArrayList<Note> FilterNames = new ArrayList<>();   //store similar notes word in arraylist. ex search sirohi s -> sirohi, sir etc;
        for (Note note : this.filterNotesList) {
            if (note.notesTitles.contains(newText) || note.subTitle.contains(newText)) {
                FilterNames.add(note);
            }
        }
        noteAdapter.filterSearch(FilterNames);
    }
}