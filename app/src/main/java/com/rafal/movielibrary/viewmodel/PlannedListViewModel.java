package com.rafal.movielibrary.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.rafal.movielibrary.model.DataRepository;
import com.rafal.movielibrary.model.Movie;

import java.util.List;

public class PlannedListViewModel extends ViewModel {

    private DataRepository repository;
    public final LiveData<List<Movie>> movies;

    public PlannedListViewModel(Context context) {
        super();
        repository = DataRepository.getRepository(context);
        movies = repository.getMoviesPlanned();

    }

    public void updateMovie(Movie movie) {
        if(movie == null) return;
        repository.updateMovies(movie);
    }
}
