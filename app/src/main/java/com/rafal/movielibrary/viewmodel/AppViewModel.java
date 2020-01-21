package com.rafal.movielibrary.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.rafal.movielibrary.model.DataRepository;
import com.rafal.movielibrary.model.Movie;

public class AppViewModel extends AndroidViewModel {

    private DataRepository repository;

    //private LiveData<List<Movie>> allMovies;

    public AppViewModel (Application application) {
        super(application);
        //repository = new DataRepository(application);
        //allMovies = repository.getAllMovies();
    }

    //LiveData<List<Movie>> getAllMovies() { return allMovies; }

    public void insertMovies(Movie... movies) { repository.insertMovies(movies); }
}
