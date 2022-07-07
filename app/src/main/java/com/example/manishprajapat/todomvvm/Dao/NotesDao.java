package com.example.manishprajapat.todomvvm.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.manishprajapat.todomvvm.Model.Note;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertNotes(Note note);

    @Update
    public void updateNotes(Note notes);

    //    @Query("select * from notes_database Where id = :id")
    @Query("Delete from notes_database WHERE id=:id")
    public void deleteNotes(int id);   //delete note according ID's

    @Query("Select * from notes_database")
    public LiveData<List<Note>> getAllNotes();

    @Query("Select * from notes_database Order By note_priority DESC ")
    public LiveData<List<Note>> getHighToLow();

    @Query("Select * from notes_database Order By note_priority ASC")
    public LiveData<List<Note>> getLowToHigh();

}
