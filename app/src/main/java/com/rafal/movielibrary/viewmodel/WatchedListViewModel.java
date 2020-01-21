package com.rafal.movielibrary.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.rafal.movielibrary.model.DataRepository;
import com.rafal.movielibrary.model.Movie;

import java.util.List;

public class WatchedListViewModel extends ViewModel {

    private DataRepository repository;
    public final LiveData<List<Movie>> movies;

    public WatchedListViewModel(Context context) {
        super();
        repository = DataRepository.getRepository(context);
        movies = repository.getMoviesWatched();
    }

    public void updateMovies(Movie... movies) {
        repository.updateMovies(movies);
    }
}
