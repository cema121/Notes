package com.hungryshark.notes.data;

import android.content.res.Resources;

import com.hungryshark.notes.CardNote;
import com.hungryshark.notes.R;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;

public class CardsCardsSourceImpl implements CardsSource, Source {

    private final List<CardNote> dataSource;
    private final Resources resources; // ресурсы приложения

    public CardsCardsSourceImpl(Resources resources) {
        dataSource = new ArrayList<>();
        this.resources = resources;
    }

    public CardsCardsSourceImpl init() {
        String[] titles = resources.getStringArray(R.array.notes);
        String[] dates = resources.getStringArray(R.array.date);
        for (int i = 0; i < titles.length; i++) {
            dataSource.add(new CardNote(titles[i], dates[i]));
        }
        return this;
    }

    @Override
    public CardNote getCardNote(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public void deleteData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateData(int position, CardNote cardNote) {
        dataSource.set(position, cardNote);
    }

    @Override
    public void addData(CardNote cardNote) {
        dataSource.add(cardNote);
    }

    @Override
    public void clearData() {
        dataSource.clear();
    }

    @Override
    public void setSystemId(String systemId) {

    }

    @Override
    public String getSystemId() {
        return null;
    }
}