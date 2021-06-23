package com.hungryshark.notes.data;

import com.hungryshark.notes.CardNote;

public interface CardsSource {
    CardNote getCardNote(int position);

    int size();

    void deleteData(int position);

    void updateData(int position, CardNote cardNote);

    void addData(CardNote cardNote);

    void clearData();
}

