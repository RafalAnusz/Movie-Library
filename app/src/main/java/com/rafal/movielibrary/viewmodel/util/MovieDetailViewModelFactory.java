package com.rafal.movielibrary.viewmodel.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rafal.movielibrary.viewmodel.MovieDetailViewModel;

public class MovieDetailViewModelFactory implements ViewModelProvider.Factory {
    private Context context;
    private long movieId;

    public MovieDetailViewModelFactory(Context context, long movieId) {
        this.context = context;
        this.movieId = movieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new MovieDetailViewModel(context, movieId);
    }
}
