package com.rafal.movielibrary.viewmodel.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rafal.movielibrary.viewmodel.WatchedListViewModel;

public class WatchedListViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public WatchedListViewModelFactory(Context context) {this.context = context;}

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new WatchedListViewModel(context);
    }
}