package com.hungryshark.notes.data;

import android.content.res.Resources;
import android.content.res.TypedArray;

import com.hungryshark.notes.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CardsSourceImpl implements CardsSource {
    private List<CardNote> dataSource;
    private Resources resources;    // ресурсы приложения

    public CardsSourceImpl(Resources resources) {
        dataSource = new ArrayList<>(7);
        this.resources = resources;
    }

    public CardsSource init(CardsSourceResponse cardsSourceResponse){
        String[] titles = resources.getStringArray(R.array.titles);
        String[] descriptions = resources.getStringArray(R.array.descriptions);
        int[] pictures = getImageArray();
        for (int i = 0; i < descriptions.length; i++) {
            dataSource.add(new CardNote(titles[i], descriptions[i], pictures[i], false, Calendar.getInstance().getTime()));
        }
        if (cardsSourceResponse != null){
            cardsSourceResponse.initialized(this);
        }
        return this;
    }

    private int[] getImageArray(){
        TypedArray pictures = resources.obtainTypedArray(R.array.pictures);
        int length = pictures.length();
        int[] answer = new int[length];
        for(int i = 0; i < length; i++){
            answer[i] = pictures.getResourceId(i, 0);
        }
        return answer;
    }

    public CardNote getCardData(int position) {
        return dataSource.get(position);
    }

    public int size(){
        return dataSource.size();
    }

    public void deleteCardData(int position) {
        dataSource.remove(position);
    }

    public void updateCardData(int position, CardNote cardData) {
        dataSource.set(position, cardData);
    }

    public void addCardData(CardNote cardData) {
        dataSource.add(cardData);
    }

    public void clearCardData() {
        dataSource.clear();
    }
}