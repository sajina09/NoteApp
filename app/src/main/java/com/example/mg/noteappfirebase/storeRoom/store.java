package com.example.mg.noteappfirebase.storeRoom;

import java.text.SimpleDateFormat;

public class store {

    public static String getReadAbleDate(Long value) {
        return new SimpleDateFormat("EEE dd, MMM - yyyy 'at' hh:mm a").format(value);
    }

}
