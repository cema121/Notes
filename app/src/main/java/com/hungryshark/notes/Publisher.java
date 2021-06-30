package com.hungryshark.notes;

import com.hungryshark.notes.data.CardNote;
import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private List<Observer> observers;

    public Publisher() {
        observers = new ArrayList<>();
    }

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notifySingle(CardNote note) {
        for (Observer observer : observers) {
            observer.updateNote(note);
            unsubscribe(observer);
        }
    }
}