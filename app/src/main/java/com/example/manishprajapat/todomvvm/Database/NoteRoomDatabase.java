package com.example.manishprajapat.todomvvm.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.manishprajapat.todomvvm.Dao.NotesDao;
import com.example.manishprajapat.todomvvm.Model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteRoomDatabase extends RoomDatabase {

    public abstract NotesDao notesDao();

    public static NoteRoomDatabase INSTANCE;

    public static synchronized NoteRoomDatabase getDatabase(final Context context) { //sybchronized used for thread safe data access one by one
        if (INSTANCE == null) {
            synchronized (NoteRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NoteRoomDatabase.class, "notes_database")
                            .fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
