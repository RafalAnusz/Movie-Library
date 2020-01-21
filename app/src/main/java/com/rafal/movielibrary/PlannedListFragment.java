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

import com.rafal.movielibrary.adapters.PlannedListAdapter;
import com.rafal.movielibrary.databinding.FragmentPlannedListBinding;
import com.rafal.movielibrary.model.Movie;
import com.rafal.movielibrary.viewmodel.PlannedListViewModel;
import com.rafal.movielibrary.viewmodel.util.PlannedListViewModelFactory;


public class PlannedListFragment extends Fragment {

    private PlannedListViewModel plannedListViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentPlannedListBinding binding =
                FragmentPlannedListBinding.inflate(inflater, container, false);

        PlannedListAdapter adapter = new PlannedListAdapter();
        binding.plannedList.setAdapter(adapter);

        plannedListViewModel = ViewModelProviders.of(this,
                new PlannedListViewModelFactory(getActivity()))
                .get(PlannedListViewModel.class);

        subscribeUI(adapter, binding);

        adapter.updateMovie.observe(this, updateMovie);

        return binding.getRoot();
    }

    private void subscribeUI(PlannedListAdapter adapter, FragmentPlannedListBinding binding) {
        plannedListViewModel.movies.observe(this, movies -> {
            binding.setHasMoviesPlanned(!movies.isEmpty());
            adapter.submitList(movies);
        });
    }

    private Observer<Movie> updateMovie = movie -> {
            plannedListViewModel.updateMovie(movie);
        };
}
