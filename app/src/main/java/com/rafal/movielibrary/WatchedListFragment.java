package com.rafal.movielibrary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.rafal.movielibrary.adapters.WatchedListAdapter;
import com.rafal.movielibrary.databinding.FragmentWatchedListBinding;
import com.rafal.movielibrary.model.Movie;
import com.rafal.movielibrary.viewmodel.WatchedListViewModel;
import com.rafal.movielibrary.viewmodel.util.WatchedListViewModelFactory;


public class WatchedListFragment extends Fragment {

    private WatchedListViewModel watchedListViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentWatchedListBinding binding =
                FragmentWatchedListBinding.inflate(inflater, container, false);

        WatchedListAdapter adapter = new WatchedListAdapter();
        binding.watchedList.setAdapter(adapter);

        watchedListViewModel = ViewModelProviders.of(this,
                new WatchedListViewModelFactory(getActivity()))
                .get(WatchedListViewModel.class);

        subscribeUI(adapter, binding);

        adapter.updateMovieRating.observe(this, ratingObserver);

        return binding.getRoot();
    }

    private void subscribeUI(WatchedListAdapter adapter, FragmentWatchedListBinding binding) {
        watchedListViewModel.movies.observe(this, movies -> {
            binding.setHasMoviesWatched(!movies.isEmpty());
            adapter.submitList(movies);
        });
    }

    private Observer<Movie> ratingObserver = movie -> {
            if(movie == null) return;
            watchedListViewModel.updateMovies(movie);
        };
}
