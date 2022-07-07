package com.example.manishprajapat.todomvvm.Repo;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.RoomDatabase;

import com.example.manishprajapat.todomvvm.Dao.NotesDao;
import com.example.manishprajapat.todomvvm.Database.NoteRoomDatabase;
import com.example.manishprajapat.todomvvm.Model.Note;

import java.util.List;

public class NotesRepository {

    private NotesDao mWordDao;          //accesing data from sqlite
    private LiveData<List<Note>> mGetAllNotes;   //get all data
    public LiveData<List<Note>> mHighToLow;
    public LiveData<List<Note>> mLowToHigh;

    public NotesRepository(Application application) {
        NoteRoomDatabase db = NoteRoomDatabase.getDatabase(application);
        mWordDao = db.notesDao();
        mGetAllNotes = mWordDao.getAllNotes();
        mHighToLow = mWordDao.getHighToLow();
        mLowToHigh = mWordDao.getLowToHigh();

    }

    //wrapper method return live data from cache
    public LiveData<List<Note>> getmGetAllNotes() {
        return mGetAllNotes;
    }


    //inseting and deletinh can be haviourso we do in background
    public void insert(Note note) {
        new InsertNote(mWordDao).execute(note);
    }

    public void update(Note note) {
        new UpdateNote(mWordDao).execute(note);
    }

    public void delete(int id) {
        new DeleteNote(mWordDao).execute(id);
    }

    private static class InsertNote extends AsyncTask<Note, Void, Void> {

        private NotesDao asyncNoteDao;

        InsertNote(NotesDao dao) {
            asyncNoteDao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            asyncNoteDao.insertNotes(notes[0]);
            return null;
        }
    }

    private static class DeleteNote extends AsyncTask<Integer, Void, Void> {

        private NotesDao asyncNoteDao;

        DeleteNote(NotesDao dao) {
            asyncNoteDao = dao;
        }

        @Override
        protected Void doInBackground(Integer... id) {
            asyncNoteDao.deleteNotes(id[0]);
            return null;
        }
    }

    private static class UpdateNote extends AsyncTask<Note, Void, Void> {

        private NotesDao asyncNoteDao;

        UpdateNote(NotesDao dao) {
            asyncNoteDao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            asyncNoteDao.updateNotes(notes[0]);
            return null;
        }
    }
}
