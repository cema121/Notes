package com.hungryshark.notes;

import com.hungryshark.notes.data.CardNote;

public interface Observer {
    void updateNote(CardNote note);
}
