package com.rafal.movielibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.snackbar.Snackbar;
import com.rafal.movielibrary.databinding.FragmentMovieDetailBinding;
import com.rafal.movielibrary.viewmodel.MovieDetailViewModel;
import com.rafal.movielibrary.viewmodel.util.MovieDetailViewModelFactory;

import static com.rafal.movielibrary.AddMovieActivity.RESULT_MOVIE_ADDED;
import static com.rafal.movielibrary.AddMovieActivity.RESULT_MOVIE_MODIFIED;
import static com.rafal.movielibrary.ViewPagerFragment.ADD_MOVIE_REQUEST;

public class MovieDetailFragment extends Fragment {

    public static final String MOVIE_ID = "com.rafal.movielibrary.MOVIE_ID";

    private MovieDetailViewModel viewModel;
    private long movieId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentMovieDetailBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_movie_detail, container, false);

        movieId = getArguments().getLong("movieId");

        viewModel = ViewModelProviders.of(this,
                new MovieDetailViewModelFactory(getActivity(), movieId))
                .get(MovieDetailViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        binding.setDeleteListener(deleteListener);
        binding.setModifyListener(modifyListener);
        binding.setButtonListener(viewModel.buttonListener);
        ((MaterialButtonToggleGroup)binding.toggleRating).addOnButtonCheckedListener(viewModel.toggleRatingListener);

        return binding.getRoot();
    }

    private View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            viewModel.deleteMovie();
            Navigation.findNavController(v).navigateUp();
        }
    };

    private View.OnClickListener modifyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), AddMovieActivity.class);
            intent.putExtra(MOVIE_ID, movieId);
            startActivityForResult(intent, ADD_MOVIE_REQUEST);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_MOVIE_REQUEST) {
            if (resultCode == RESULT_MOVIE_MODIFIED) {
                Snackbar.make(this.getView(), getString(R.string.movie_modified), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        }
    }
}
