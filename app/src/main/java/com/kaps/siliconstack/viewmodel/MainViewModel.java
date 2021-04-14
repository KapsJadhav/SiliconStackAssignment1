package com.kaps.siliconstack.viewmodel;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kaps.siliconstack.R;
import com.kaps.siliconstack.model.Callback.AddUpdateNoteCallback;
import com.kaps.siliconstack.model.Callback.NoteCallback;
import com.kaps.siliconstack.model.Note;
import com.kaps.siliconstack.model.NoteRepo;
import com.kaps.siliconstack.model.helper.AppConstants;
import com.kaps.siliconstack.model.repository.SiliconStackApiInstance;

import java.util.List;

public class MainViewModel extends ViewModel implements NoteCallback, AddUpdateNoteCallback {

    private NoteRepo repository;
    private LiveData<List<Note>> allNotes;
    private Context context;
    private String sUserId = "1";

    public MainViewModel(Context context) {
        this.context = context;
        repository = new NoteRepo(context);
        allNotes = repository.getAllNotes();
        fetchNotes();
    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    Dialog dialogAddNote;

    public void onAddNoteButton() {
        dialogAddNote = new Dialog(context, android.R.style.Theme_Translucent);
        dialogAddNote.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogAddNote.getWindow().setGravity(Gravity.CENTER);
        dialogAddNote.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAddNote.setCancelable(false);
        dialogAddNote.setContentView(R.layout.layout_dialog_add_update_note);
        ImageView imageViewBack = dialogAddNote.findViewById(R.id.imageViewBack);
        ImageView imageViewSave = dialogAddNote.findViewById(R.id.imageViewSave);
        TextView textViewTitle = dialogAddNote.findViewById(R.id.textViewTitle);
        EditText editTextTitle = dialogAddNote.findViewById(R.id.editTextTitle);
        EditText editTextBody = dialogAddNote.findViewById(R.id.editTextBody);
        EditText editTextNote = dialogAddNote.findViewById(R.id.editTextNote);
        EditText editTextStatus = dialogAddNote.findViewById(R.id.editTextStatus);
        textViewTitle.setText(context.getString(R.string.add_task));
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddNote.cancel();
            }
        });
        imageViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sTitle = editTextTitle.getText().toString().trim();
                String sBody = editTextBody.getText().toString().trim();
                String sNote = editTextNote.getText().toString().trim();
                String sStatus = editTextStatus.getText().toString().trim();
                if (sTitle.isEmpty() || sBody.isEmpty() || sNote.isEmpty() || sStatus.isEmpty()) {
                    AppConstants.showToastMessage(context, "Please enter all details");
                } else {
                    addNote(sTitle, sBody, sNote, sStatus);
                }
            }
        });
        dialogAddNote.show();
    }

    private void addNote(String sTitle, String sBody, String sNote, String sStatus) {
        boolean isConnected = AppConstants.isConnected(context);
        if (isConnected) {
            SiliconStackApiInstance.getInstance().addNote(sUserId, sTitle, sBody, sNote, sStatus, this, context);
        } else {
            onError("No internet connection...");
        }
    }


    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void fetchNotes() {
        boolean isConnected = AppConstants.isConnected(context);
        if (isConnected) {
            SiliconStackApiInstance.getInstance().getAllNotes(this, context);
        } else {
            onError("No internet connection...");
        }
    }

    @Override
    public void onSuccess(List<Note> noteList) {
        deleteAllNotes();
        for (int i = 0; i < noteList.size(); i++) {
            insert(noteList.get(i));
        }
    }

    @Override
    public void onSuccessAddUpdateNote(Note note) {
        insert(note);
        if (dialogAddNote != null) {
            dialogAddNote.cancel();
        }
        fetchNotes();
    }

    @Override
    public void onError(String sMessage) {
        AppConstants.showToastMessage(context, "" + sMessage);
    }

    public void onEditNote(Note note) {
        sUserId = note.getUser_id();
        dialogAddNote = new Dialog(context, android.R.style.Theme_Translucent);
        dialogAddNote.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogAddNote.getWindow().setGravity(Gravity.CENTER);
        dialogAddNote.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAddNote.setCancelable(false);
        dialogAddNote.setContentView(R.layout.layout_dialog_add_update_note);
        ImageView imageViewBack = dialogAddNote.findViewById(R.id.imageViewBack);
        ImageView imageViewSave = dialogAddNote.findViewById(R.id.imageViewSave);
        TextView textViewTitle = dialogAddNote.findViewById(R.id.textViewTitle);
        EditText editTextTitle = dialogAddNote.findViewById(R.id.editTextTitle);
        EditText editTextBody = dialogAddNote.findViewById(R.id.editTextBody);
        EditText editTextNote = dialogAddNote.findViewById(R.id.editTextNote);
        EditText editTextStatus = dialogAddNote.findViewById(R.id.editTextStatus);
        editTextTitle.setText(note.getTitle());
        editTextBody.setText(note.getBody());
        editTextNote.setText(note.getNote());
        editTextStatus.setText(note.getStatus());
        textViewTitle.setText(context.getString(R.string.edit_task));
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddNote.cancel();
            }
        });
        imageViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sTitle = editTextTitle.getText().toString().trim();
                String sBody = editTextBody.getText().toString().trim();
                String sNote = editTextNote.getText().toString().trim();
                String sStatus = editTextStatus.getText().toString().trim();
                if (sTitle.isEmpty() || sBody.isEmpty() || sNote.isEmpty() || sStatus.isEmpty()) {
                    AppConstants.showToastMessage(context, "Please enter all details");
                } else {
                    boolean isConnected = AppConstants.isConnected(context);
                    if (isConnected) {
                        SiliconStackApiInstance.getInstance().updateNote(note.getId(), sUserId, sTitle, sBody, sNote, sStatus, MainViewModel.this, context);
                    } else {
                        onError("No internet connection...");
                    }
                }
            }
        });
        dialogAddNote.show();
    }


    public void onDeleteNote(Note note) {
        boolean isConnected = AppConstants.isConnected(context);
        if (isConnected) {
            SiliconStackApiInstance.getInstance().deleteNote(note.getId(), note.getUser_id(), this, context);
        } else {
            onError("No internet connection...");
        }
    }
}
