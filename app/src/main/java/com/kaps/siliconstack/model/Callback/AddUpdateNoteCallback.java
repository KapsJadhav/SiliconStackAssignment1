package com.kaps.siliconstack.model.Callback;

import com.kaps.siliconstack.model.Note;


public interface AddUpdateNoteCallback {
    void onSuccessAddUpdateNote(Note note);
    void onError(String sMessage);
}
