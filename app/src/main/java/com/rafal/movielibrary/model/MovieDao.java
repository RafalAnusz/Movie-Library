package com.rafal.movielibrary.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies WHERE id = :id")
    LiveData<Movie> getMovie(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovies(Movie ... movies);

    @Update//Matches primary key
    void updateMovies(Movie ... movies);

    @Delete
    void deleteMovies(Movie ... movies);

    @Query("DELETE FROM movies")
    void deleteAll();

    @Query("SELECT * FROM movies ORDER BY year ASC")
    LiveData<List<Movie>> loadChronologicalMovies();

    @Query("SELECT * FROM movies WHERE status=:i ORDER BY title ASC")
    LiveData<List<Movie>> loadAlphabetizedMovies(int i);
}
