package com.kaps.siliconstack.model.Callback;

import com.kaps.siliconstack.model.Note;

import java.util.List;

public interface NoteCallback {
    void onSuccess(List<Note> noteList);
    void onError(String sMessage);
}
