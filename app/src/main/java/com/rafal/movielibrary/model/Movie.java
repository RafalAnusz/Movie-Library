package com.rafal.movielibrary.model;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.rafal.movielibrary.model.util.StatusConverter;
import com.rafal.movielibrary.model.util.UriConverter;

@Entity(tableName = "movies")
public class Movie {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "year")
    private int year;

    @NonNull
    @ColumnInfo(name = "status")
    @TypeConverters(StatusConverter.class)
    private Status status;

    @ColumnInfo(name = "rating")
    private int rating;

    @ColumnInfo(name = "cover")
    @TypeConverters(UriConverter.class)
    private Uri cover;

    @NonNull
    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    public void setStatus(@NonNull Status status) {
        this.status = status;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Uri getCover() {
        return cover;
    }

    public void setCover(Uri cover) {
        this.cover = cover;
    }

    public Movie(String title, int year, Status status, int rating, Uri cover) {
        this.title = title;
        this.year = year;
        this.status = status;
        this.rating = rating;
        this.cover = cover;
    }

    public Movie(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.year = movie.getYear();
        this.status = movie.getStatus();
        this.rating = movie.getRating();
        this.cover = movie.getCover();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        Movie movie = (Movie) obj;
        if(this.title.equals(movie.title)
                && this.year == movie.year
                && this.status == movie.status
                && this.rating == movie.rating
                && this.cover == movie.cover)
            return true;
        return false;
    }

    public enum Status {
        Planned(0),
        Watched(1);

        private int num;

        Status(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }
    }
}
