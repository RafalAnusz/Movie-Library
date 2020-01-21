package com.rafal.movielibrary.model.util;

import androidx.room.TypeConverter;

import com.rafal.movielibrary.model.Movie;

import static com.rafal.movielibrary.model.Movie.Status.Planned;
import static com.rafal.movielibrary.model.Movie.Status.Watched;

public class StatusConverter {

    @TypeConverter
    public static Movie.Status toStatus(int status) {
        switch (status) {
            case 0:
                return Planned;
            case 1:
                return Watched;
            default:
                throw new IllegalArgumentException("Could not recognize status");
        }
    }

    @TypeConverter
    public static int toInteger(Movie.Status status) {
        return status.getNum();
    }
}
