package com.rafal.movielibrary.viewmodel.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rafal.movielibrary.viewmodel.AddMovieViewModel;

public class AddMovieViewModelFactory implements ViewModelProvider.Factory {
    private Context context;
    private long movieId;

    public AddMovieViewModelFactory(Context context, long movieId) {
        this.context = context;
        this.movieId = movieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddMovieViewModel(context, movieId);
    }
}
