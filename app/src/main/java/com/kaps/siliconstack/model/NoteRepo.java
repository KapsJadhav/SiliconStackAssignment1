package com.kaps.siliconstack.model;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepo {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepo(Context context) {
        DatabaseHelper database = DatabaseHelper.getInstance(context);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }
    public void update(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }
    public void delete(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }
    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }


    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> { //static : doesnt have reference to the
        // repo itself otherwise it could cause memory leak!
        private NoteDao noteDao;
        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) { // ...  is similar to array
            noteDao.Insert(notes[0]); //single note
            return null;
        }
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;
        private UpdateNoteAsyncTask(NoteDao noteDao) { //constructor as the class is static
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.Update(notes[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;
        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.Delete(notes[0]);
            return null;
        }
    }
    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;
        private DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.DeleteAllNotes();
            return null;
        }
    }

}
