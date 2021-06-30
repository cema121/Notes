package com.hungryshark.notes.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class CardNote implements Parcelable{
    private String id;
    private String title;
    private String description;
    private int picture;
    private boolean like;
    private Date date;

    public CardNote(String title, String description, int picture, boolean like, Date date){
        this.title = title;
        this.description=description;
        this.picture=picture;
        this.like=like;
        this.date = date;
    }
    protected CardNote(Parcel in) {
        title = in.readString();
        description = in.readString();
        picture = in.readInt();
        like = in.readByte() != 0;
        date = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(picture);
        dest.writeByte((byte) (like ? 1 : 0));
        dest.writeLong(date.getTime());
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

    public String getDescription() {
        return description;
    }

    public int getPicture() {
        return picture;
    }

    public boolean isLike() {
        return like;
    }

    public Date getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}