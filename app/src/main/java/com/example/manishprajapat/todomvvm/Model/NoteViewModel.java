package com.example.manishprajapat.todomvvm.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.manishprajapat.todomvvm.Repo.NotesRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    NotesRepository notesRepository;
    public LiveData<List<Note>> allNotes;
    public LiveData<List<Note>> mHighToLow;
    public LiveData<List<Note>> mLowToHigh;


    public NoteViewModel(@NonNull Application application) {
        super(application);

        notesRepository = new NotesRepository(application); //application is subcontext of context
        allNotes = notesRepository.getmGetAllNotes();
        mLowToHigh = notesRepository.mLowToHigh;
        mHighToLow = notesRepository.mHighToLow;


    }

    public LiveData<List<Note>> getNote(Note note) {
        return allNotes;
    }

    public void insertNote(Note note) {
        notesRepository.insert(note);
    }

    public void deleteNote(int id) {
        notesRepository.delete(id);

    }

    public void updateNote(Note note) {
        notesRepository.update(note);
    }


}
