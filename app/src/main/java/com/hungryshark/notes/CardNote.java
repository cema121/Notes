package com.hungryshark.notes;

import android.os.Parcel;
import android.os.Parcelable;

public class CardNote implements Parcelable {

    final String title;
    final String date;

    public CardNote(String title, String date) {
        this.title = title;
        this.date = date;
    }

    protected CardNote(Parcel in) {
        title = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CardNote> CREATOR = new Creator<CardNote>() {
        @Override
        public CardNote createFromParcel(Parcel in) {
            return new CardNote(in);
        }

        @Override
        public CardNote[] newArray(int size) {
            return new CardNote[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}