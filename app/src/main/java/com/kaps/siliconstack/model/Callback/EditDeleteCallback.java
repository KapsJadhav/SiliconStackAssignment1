package com.kaps.siliconstack.model.Callback;

import com.kaps.siliconstack.model.Note;

public interface EditDeleteCallback {
    void onEdit(Note note);
    void onDelete(Note note);
}
