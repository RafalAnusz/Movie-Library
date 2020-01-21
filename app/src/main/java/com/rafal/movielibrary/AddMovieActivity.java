package com.rafal.movielibrary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.snackbar.Snackbar;
import com.rafal.movielibrary.databinding.ActivityAddMovieBinding;
import com.rafal.movielibrary.viewmodel.util.AddMovieViewModelFactory;
import com.rafal.movielibrary.viewmodel.AddMovieViewModel;

import static com.rafal.movielibrary.MovieDetailFragment.MOVIE_ID;


public class AddMovieActivity extends AppCompatActivity {

    public static final int PICK_COVER_REQUEST = 2;
    public static final int RESULT_MOVIE_ADDED = 3;
    public static final int RESULT_MOVIE_MODIFIED = 4;

    private AddMovieViewModel addMovieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddMovieBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_movie);

        Intent intent = getIntent();
        long movieId = intent.getLongExtra(MOVIE_ID, -1);

        addMovieViewModel = ViewModelProviders.of(this, new AddMovieViewModelFactory(getApplicationContext(), movieId)).get(AddMovieViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewModel(addMovieViewModel);
        binding.setPickCover(pickCover);
        ((MaterialButtonToggleGroup)binding.toggleRating).addOnButtonCheckedListener(addMovieViewModel.toggleRatingListener);

        addMovieViewModel.movieAddedModified.observe(this, movieAddedModifiedObserver);
    }

    private Observer<Integer> movieAddedModifiedObserver = integer -> {
        switch (integer) {
            case RESULT_MOVIE_ADDED:
                setResult(RESULT_MOVIE_ADDED);
                finish();
                break;
            case RESULT_MOVIE_MODIFIED:
                setResult(RESULT_MOVIE_MODIFIED);
                finish();
                break;
            default:
                View view = findViewById(R.id.addMovieLayout);
                Snackbar.make(view, getString(R.string.cant_add_movie), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                break;
        }
    };

    private View.OnClickListener pickCover = v -> {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent.createChooser(intent, "Select Cover"), PICK_COVER_REQUEST);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_COVER_REQUEST) {
            if(resultCode == RESULT_OK) {
                try {
                    addMovieViewModel.movieCover.setValue(data.getData());
                } catch (NullPointerException e) {
                    View view = findViewById(R.id.addMovieLayout);
                    Snackbar.make(view, getString(R.string.cant_load_image), Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
        }
    }
}
