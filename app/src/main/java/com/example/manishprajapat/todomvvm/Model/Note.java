package com.example.manishprajapat.todomvvm.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_database")
public class Note {

    @ColumnInfo(name = "notes_titles")
    public String notesTitles;

    @ColumnInfo(name = "notes_descriptions")
    public String notesDescription;

    @ColumnInfo(name = "note_subTitle")
    public String subTitle;

    @ColumnInfo(name = "note_priority")
    public String priority;

    @ColumnInfo(name = "note_date")
    public String noteDate;

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String getNotesTitles() {
        return notesTitles;
    }

    public void setNotesTitles(String notesTitles) {
        this.notesTitles = notesTitles;
    }

    public String getNotesDescription() {
        return notesDescription;
    }

    public void setNotesDescription(String notesDescription) {
        this.notesDescription = notesDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }
}
