package com.rafal.movielibrary.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rafal.movielibrary.ViewPagerFragmentDirections;
import com.rafal.movielibrary.databinding.ListItemMoviePlannedBinding;
import com.rafal.movielibrary.model.Movie;

import static com.rafal.movielibrary.model.Movie.Status.Watched;


public class PlannedListAdapter extends ListAdapter<Movie, PlannedListAdapter.MovieViewHolder> {

    public static MutableLiveData<Movie> updateMovie = new MutableLiveData<>(null);

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemMoviePlannedBinding binding =
                ListItemMoviePlannedBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);

        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public PlannedListAdapter() {
        super(MovieDiffCallback);
    }

    static final class MovieViewHolder extends RecyclerView.ViewHolder {
        private final ListItemMoviePlannedBinding binding;

        final void bind(Movie item) {
            binding.setMovie(item);
            binding.executePendingBindings();
        }

        MovieViewHolder(ListItemMoviePlannedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Movie movie = binding.getMovie();
                    if(movie != null) {
                        NavDirections direction = ViewPagerFragmentDirections.actionViewPagerFragmentToMovieDetailFragment(movie.getId());
                        Navigation.findNavController(view).navigate(direction);
                    }
                }
            });
            this.binding.setButtonListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Movie movie = binding.getMovie();
                    movie.setStatus(Watched);
                    updateMovie.setValue(movie);
                }
            });
        }
    }

    static final DiffUtil.ItemCallback<Movie> MovieDiffCallback = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.equals(newItem);
        }
    };
}
