package com.rafal.movielibrary.model.util;

import android.net.Uri;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

public class UriConverter {

    @TypeConverter
    public static Uri toUri(String uri) {
        if(uri == null) return null;
        return Uri.parse(uri);
    }

    @TypeConverter
    public static String toString(Uri uri) {
        if(uri == null) return null;
        return uri.toString();
    }
}
