package com.rafal.movielibrary.viewmodel;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.rafal.movielibrary.R;
import com.rafal.movielibrary.model.DataRepository;
import com.rafal.movielibrary.model.Movie;


import static com.rafal.movielibrary.AddMovieActivity.RESULT_MOVIE_ADDED;
import static com.rafal.movielibrary.AddMovieActivity.RESULT_MOVIE_MODIFIED;
import static com.rafal.movielibrary.model.Movie.Status.Planned;
import static com.rafal.movielibrary.model.Movie.Status.Watched;

public class AddMovieViewModel extends ViewModel {

    private DataRepository repository;
    private LiveData<Movie> movie;

    public final MutableLiveData<String> movieTitle = new MutableLiveData<String>("");
    public final MutableLiveData<Integer> movieYear = new MutableLiveData<Integer>();
    public final MutableLiveData<Boolean> movieStatus = new MutableLiveData<>(false);
    public final MutableLiveData<Uri> movieCover = new MutableLiveData<>(null);
    public final MutableLiveData<Integer> toggleRating = new MutableLiveData<>(0);

    public final MutableLiveData<Integer> movieAddedModified = new MutableLiveData<>();


    public AddMovieViewModel(Context context, long movieId) {
        super();
        repository = DataRepository.getRepository(context);
        movieStatus.observeForever(movieStatusObserver);

        if(movieId != -1) {
            movie = repository.getMovie(movieId);
            movie.observeForever(getOldValues);
        }
    }

    @Override
    protected void onCleared() {
        movieStatus.removeObserver(movieStatusObserver);
        if(movie != null) {
            movie.removeObserver(getOldValues);
        }
        super.onCleared();
    }

    public View.OnClickListener addMovie = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(movieYear.getValue() != null && !movieTitle.getValue().isEmpty()) {
                if(movie != null) {
                    Movie newMovie = new Movie(movie.getValue());
                    newMovie.setTitle(movieTitle.getValue().replace('\n', ' '));
                    newMovie.setYear(movieYear.getValue());
                    newMovie.setStatus(movieStatus.getValue() ? Watched : Planned);
                    newMovie.setCover(movieCover.getValue());
                    newMovie.setRating(toggleRating.getValue());
                    repository.updateMovies(newMovie);
                    movieAddedModified.setValue(RESULT_MOVIE_MODIFIED);
                } else {
                    repository.insertMovies(
                            new Movie(movieTitle.getValue().replace('\n', ' '),
                            movieYear.getValue(), movieStatus.getValue() ? Watched : Planned,
                            toggleRating.getValue(), movieCover.getValue()));
                    movieAddedModified.setValue(RESULT_MOVIE_ADDED);
                }
            } else {
                movieAddedModified.setValue(0);
            }
        }
    };

    public MaterialButtonToggleGroup.OnButtonCheckedListener
            toggleRatingListener = (group, checkedId, isChecked) -> {
        switch (checkedId) {
            case R.id.star1:
                toggleRating.setValue(1);
                break;
            case R.id.star2:
                toggleRating.setValue(2);
                break;
            case R.id.star3:
                toggleRating.setValue(3);
                break;
            case R.id.star4:
                toggleRating.setValue(4);
                break;
            case R.id.star5:
                toggleRating.setValue(5);
                break;
        }
    };

    private Observer<Boolean> movieStatusObserver = aBoolean -> {
        if(!aBoolean) {
            toggleRating.setValue(0);
        }
    };

    private Observer<Movie> getOldValues = movie -> {
        movieTitle.setValue(movie.getTitle());
        movieYear.setValue(movie.getYear());
        movieStatus.setValue(movie.getStatus() == Watched);
        movieCover.setValue(movie.getCover());
        toggleRating.setValue(movie.getRating());
    };
}
