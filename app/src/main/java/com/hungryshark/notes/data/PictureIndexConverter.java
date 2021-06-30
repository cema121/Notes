package com.hungryshark.notes.data;

import com.hungryshark.notes.R;

import java.util.Random;


public class PictureIndexConverter {

    private static final Random rnd = new Random();

    private static final Object syncObj = new Object();

    private static final int[] picIndex = {
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
    };

    public static int randomPictureIndex() {
        synchronized (syncObj) {
            return rnd.nextInt(picIndex.length);
        }
    }

    public static int getPictureByIndex(int index) {
        if (index < 0 || index >= picIndex.length) {
            index = 0;
        }
        return picIndex[index];
    }

    public static int getIndexByPicture(int picture) {
        for (int i = 0; i < picIndex.length; i++) {
            if (picIndex[i] == picture) {
                return i;
            }
        }
        return 0;
    }
}
