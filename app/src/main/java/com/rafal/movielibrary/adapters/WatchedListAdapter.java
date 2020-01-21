package com.rafal.movielibrary.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.rafal.movielibrary.R;
import com.rafal.movielibrary.ViewPagerFragmentDirections;
import com.rafal.movielibrary.databinding.ListItemMovieWatchedBinding;
import com.rafal.movielibrary.model.Movie;

public class WatchedListAdapter extends ListAdapter<Movie, WatchedListAdapter.WatchedListViewHolder> {

    public static MutableLiveData<Movie> updateMovieRating = new MutableLiveData<>(null);

    public WatchedListAdapter() {super(PlannedListAdapter.MovieDiffCallback);}

    @NonNull
    @Override
    public WatchedListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemMovieWatchedBinding binding =
                ListItemMovieWatchedBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);

        return new WatchedListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchedListViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static final class WatchedListViewHolder extends RecyclerView.ViewHolder {
        private final ListItemMovieWatchedBinding binding;

        final void bind(Movie item) {
            binding.setMovie(item);
            binding.executePendingBindings();
        }

        WatchedListViewHolder(ListItemMovieWatchedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setClickListener(view -> {
                Movie movie = binding.getMovie();
                if(movie != null) {
                    NavDirections direction = ViewPagerFragmentDirections.actionViewPagerFragmentToMovieDetailFragment(movie.getId());
                    Navigation.findNavController(view).navigate(direction);
                }
            });

            ((MaterialButtonToggleGroup)binding.toggleRating).addOnButtonCheckedListener(toggleRatingListener);
        }

        private MaterialButtonToggleGroup.OnButtonCheckedListener toggleRatingListener = new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                Movie movie = new Movie(binding.getMovie());
                switch (checkedId) {
                    case R.id.star1:
                        movie.setRating(1);
                        break;
                    case R.id.star2:
                        movie.setRating(2);
                        break;
                    case R.id.star3:
                        movie.setRating(3);
                        break;
                    case R.id.star4:
                        movie.setRating(4);
                        break;
                    case R.id.star5:
                        movie.setRating(5);
                        break;
                }
                updateMovieRating.setValue(movie);
            }
        };
    }
}
