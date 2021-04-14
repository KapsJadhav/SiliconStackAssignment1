package com.kaps.siliconstack.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kaps.siliconstack.R;
import com.kaps.siliconstack.databinding.ActivityMainBinding;
import com.kaps.siliconstack.model.Callback.EditDeleteCallback;
import com.kaps.siliconstack.model.Note;
import com.kaps.siliconstack.model.helper.AppConstants;
import com.kaps.siliconstack.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements EditDeleteCallback {

    private ActivityMainBinding activityMainBinding;
    private RelativeLayout relativeLayoutSearchBar;
    private ImageView imageViewCancel;
    private EditText editTextTitle;
    private List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setViewModel(new MainViewModel(this));
        activityMainBinding.executePendingBindings();
        relativeLayoutSearchBar = findViewById(R.id.relativeLayoutSearchBar);
        imageViewCancel = findViewById(R.id.imageViewCancel);
        editTextTitle = findViewById(R.id.editTextTitle);
        activityMainBinding.getViewModel().getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteList = notes;
                NoteAdapter noteAdapter = new NoteAdapter(MainActivity.this, notes, MainActivity.this);
                activityMainBinding.recyclerViewNotes.setAdapter(noteAdapter);
            }
        });

        activityMainBinding.bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        final int previousItem = activityMainBinding.bottomNavigationView.getSelectedItemId();
                        final int nextItem = item.getItemId();
                        if (previousItem != nextItem) {
                            switch (nextItem) {
                                case R.id.Home:
                                    relativeLayoutSearchBar.setVisibility(View.GONE);
                                    activityMainBinding.getViewModel().fetchNotes();
                                    break;
                                case R.id.Search:
                                    relativeLayoutSearchBar.setVisibility(View.VISIBLE);
                                    break;
                                case R.id.Profile:

                                    break;
                                case R.id.Settings:

                                    break;
                            }
                        }
                        return true;
                    }
                }
        );

        editTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Note> filteredNotes = new ArrayList<>();
                for (int i = 0; i < noteList.size(); i++) {
                    Note note = noteList.get(i);
                    if (note.getTitle().toLowerCase().contains(s.toString().toLowerCase())) {
                        filteredNotes.add(note);
                    }
                }
                NoteAdapter noteAdapter = new NoteAdapter(MainActivity.this, filteredNotes, MainActivity.this);
                activityMainBinding.recyclerViewNotes.setAdapter(noteAdapter);
                if (filteredNotes.size() == 0) {
                    AppConstants.showToastMessage(MainActivity.this, "No Task Found");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                editTextTitle.setText("");
                relativeLayoutSearchBar.setVisibility(View.GONE);
//                activityMainBinding.getViewModel().getAllNotes();
                activityMainBinding.bottomNavigationView.getMenu().getItem(0).setChecked(true);
            }
        });

    }

    @Override
    public void onEdit(Note note) {
        activityMainBinding.getViewModel().onEditNote(note);
    }

    @Override
    public void onDelete(Note note) {
        activityMainBinding.getViewModel().onDeleteNote(note);
    }
}