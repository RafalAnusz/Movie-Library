package com.rafal.movielibrary.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.rafal.movielibrary.R;
import com.rafal.movielibrary.model.DataRepository;
import com.rafal.movielibrary.model.Movie;


public class MovieDetailViewModel extends ViewModel {

    private DataRepository repository;
    public final LiveData<Movie> movie;

    public MovieDetailViewModel(Context context, long movieId) {
        super();
        repository = DataRepository.getRepository(context);
        movie = repository.getMovie(movieId);
    }

    public void deleteMovie() {
        if(movie.getValue() == null) return;
        repository.deleteMovies(movie.getValue());
    }

    private void updateMovie(Movie mMovie) {
        if(mMovie == null) return;
        repository.updateMovies(mMovie);
    }

    public View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(movie.getValue() == null) return;
            Movie mMovie = movie.getValue();
            mMovie.setStatus(Movie.Status.Watched);
            updateMovie(mMovie);
        }
    };

    public MaterialButtonToggleGroup.OnButtonCheckedListener toggleRatingListener = new MaterialButtonToggleGroup.OnButtonCheckedListener() {
        @Override
        public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
            if(movie.getValue() == null) return;
            Movie mMovie = movie.getValue();
            switch (checkedId) {
                case R.id.star1:
                    mMovie.setRating(1);
                    break;
                case R.id.star2:
                    mMovie.setRating(2);
                    break;
                case R.id.star3:
                    mMovie.setRating(3);
                    break;
                case R.id.star4:
                    mMovie.setRating(4);
                    break;
                case R.id.star5:
                    mMovie.setRating(5);
                    break;
            }
            updateMovie(mMovie);
        }
    };
}
